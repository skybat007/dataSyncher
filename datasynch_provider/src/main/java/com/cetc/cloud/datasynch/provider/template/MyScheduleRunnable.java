package com.cetc.cloud.datasynch.provider.template;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.api.model.SynchJobLogInfoModel;
import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import com.cetc.cloud.datasynch.provider.middleware.SQLCreator;
import com.cetc.cloud.datasynch.provider.service.impl.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Description：创建自定义执行实例定时同步任务实例
 * Created by luolinjie on 2018/10/10.
 */
@Slf4j
public class MyScheduleRunnable implements Runnable {
    private ScheduleModel scheduleModel;

    private SynchJobLogInfoService synchJobLogInfoService;
    private ScheduleService scheduleService;
    private DbQueryService dbQueryService;
    private DbOperateService dbOperateService;
    private HttpOperateService httpOperateService;

    public MyScheduleRunnable(ScheduleModel scheduleModel, SynchJobLogInfoService synchJobLogInfoService,ScheduleService scheduleService, DbQueryService dbQueryService, DbOperateService dbOperateService, HttpOperateService httpOperateService) {
        this.scheduleModel = scheduleModel;
        this.synchJobLogInfoService = synchJobLogInfoService;
        this.scheduleService = scheduleService;
        this.dbQueryService = dbQueryService;
        this.dbOperateService = dbOperateService;
        this.httpOperateService = httpOperateService;
    }

    @Override
    public void run() {
        //更新任务
        updateScheduleModel(scheduleModel);
        if (scheduleModel.getIsEnabled()==0){
            return;
        }
        Thread.currentThread().setName(scheduleModel.getTargetTableName());
        log.info("\n\n----->>>> executing run() method,\nThread.currentThread().Name:"
                + Thread.currentThread().getName()
                + "\nThread.currentThread().getId():" + Thread.currentThread().getId());
        try {
            boolean ifExistsTable = checkIfTableExists(scheduleModel.getTargetTableName());
            if (ifExistsTable==false){
                log.error("table doesn't exists:"+scheduleModel.getTargetTableName()+" ,please create table first!");
                return;
            }
            //检查是否存在TargetTable对应的序列，如果不存在，则提前创建
            checkAndCreateSequence(scheduleModel.getTargetTableName());
            //所有的Target表都必须有自增id,create_time,update_time，如果不存在，则添加
            checkAndCreateColumn(scheduleModel.getTargetTableName(), CommonInstance.GLOBAL_COLNAME_INCRE_ID, "NUMBER", "id");
            checkAndCreateColumn(scheduleModel.getTargetTableName(), CommonInstance.GLOBAL_COLNAME_CREATE_TIME, "DATE DEFAULT SYSDATE", "创建时间");
            checkAndCreateColumn(scheduleModel.getTargetTableName(), CommonInstance.GLOBAL_COLNAME_UPDATE_TIME, "DATE DEFAULT SYSDATE", "更新时间");

            //根据接入方式决定生成SQL query还是Http请求
            if (scheduleModel.getConnType() == CommonInstance.TYPE_DB) {
                //数据库接入方式
                boolean b = doSQLPulling(synchJobLogInfoService, dbQueryService, dbOperateService);
                if (b) {
                    log.info("\n\n----->>>>MyScheduleRunnable.doSQLPulling：finished!!!");
                } else {
                    log.error("\n\n----->>>>MyScheduleRunnable.doSQLPulling：finished with error!!!");
                    throw new RuntimeException();
                }
            } else if (scheduleModel.getConnType() == CommonInstance.TYPE_INTERFACE) {
                //接口接入方式
                boolean b = doHttpPulling(synchJobLogInfoService, httpOperateService, dbOperateService);
                if (b) {
                    log.info("\n\n----->>>>MyScheduleRunnable.doHttpPulling：finished!!!");
                } else {
                    log.error("\n\n----->>>>MyScheduleRunnable.doHttpPulling：finished with error!!!");
                    return;
                }
            } else return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateScheduleModel(ScheduleModel scheduleModel) {
        this.scheduleModel = scheduleService.queryModelByJobId(scheduleModel.getId());
    }

    private boolean checkIfTableExists(String targetTableName) {
        boolean exists = dbOperateService.checkIfExistsTable(targetTableName);
        return exists;
    }

    private void checkAndCreateColumn(String targetTableName, String collumnName, String columnType_len, String comment) {
        boolean exists = dbOperateService.checkIfColumnExists(targetTableName, collumnName);
        if (!exists) {
            dbOperateService.addColumn(scheduleModel.getTargetTableName(), collumnName, columnType_len);
            dbOperateService.addColumnComment(scheduleModel.getTargetTableName(), collumnName, comment);
        }
    }

    /**
     * 检查是否存在该序列，如果不存在，则创建该序列
     *
     * @param targetTableName
     */
    private void checkAndCreateSequence(String targetTableName) {
        boolean exists = dbOperateService.checkIfSequenceExists_prefix_seq(targetTableName);
        if (exists) {
            log.info("\nSequence has already exists!");
        } else {
            log.info("\nSequence doesn't exists!");
            boolean sequence = dbOperateService.createSequence("SEQ_" + targetTableName);
            if (sequence) {
                log.info("\ncreate sequence success! \nSequence Name:SEQ_" + targetTableName);
            }
        }
    }


    /**
     * 发起SQL轮询请求
     *
     * @return
     */
    private boolean doSQLPulling(SynchJobLogInfoService synchJobLogInfoService, DbQueryService dbQueryService, DbOperateService dbOperateService) throws SQLException {

        /**变量创建*/
        int singleJobTotalSuccessCount = 0;
        int singleJobTotalFailCount = 0;
        //用来计算最新的分页参数
        int toDoPageNum;
        int startRow;
        int endRow;
        SynchJobLogInfoModel logModel = null;

        //获取源表中的数据总数，当总数大于目标表时，则需要启动任务执行该任务
        int tableRowCounts_src = 0;
        int tableRowCounts_trgt = 0;


        //判断是否到达最后一页(未到达最后一页的标志：rowNum==pageSize;若达到最后一页: rowNum==0||rownum<pageSize
        // null==model表示还未发起过请求)
        //增加第二次启动任务的逻辑：
        if (scheduleModel.getIsPagingQuery() == CommonInstance.DO_PAGING) {
            tableRowCounts_src = dbQueryService.getTableRowCounts(scheduleModel.getSource());
            tableRowCounts_trgt = dbOperateService.getTableRowCounts(scheduleModel.getTargetTableName());
            boolean compareRes = tableRowCounts_src > tableRowCounts_trgt ? true : false;
            boolean reachedLastRow = false;
            log.info("\n\n----->>>> SourceTable.total > TargetTable.total? " + compareRes);
            /**初次执行；未到达最后一页；两表大小不一致 --- 继续请求**/
            while (null == logModel || !reachedLastRow) {
                //1.根据tableName查询最近一条执行成功的数据同步日志对应的分页参数
                try {
                    logModel = synchJobLogInfoService.queryLatestInfoByJobId(scheduleModel.getId());
                } catch (Exception e) {
                    log.info("获取最近一次日志的分页参数为NULL！");
                }
                log.info("\n\n----->>>> current job is running! query doesn't reach the end ");

                //重新计算分页参数
                if (null == logModel) {
                    startRow = 1;
                    endRow = startRow + scheduleModel.getPageSize() - 1;
                } else if (compareRes == true) { //初次执行while循环
                    startRow = logModel.getEndRow() + 1;
                    //通过pagenum和pagesize计算StartRow和EndRow
                    endRow = startRow + scheduleModel.getPageSize() - 1;
                } else {
                    startRow = logModel.getEndRow() + 1;
                    endRow = startRow + scheduleModel.getPageSize() - 1;
                }

                //创建新的SQL请求语句
                String SQL = SQLCreator.createSQLByTbNameAndRowParam(scheduleModel.getSource(), startRow, endRow, scheduleModel.getOrderByColumnName());
                //获取数据
                List<HashMap> queryResult = dbQueryService.oracleQuerySql(SQL);
                //通过pagenum和pagesize计算StartRow和EndRow
                if (queryResult.size() != scheduleModel.getPageSize()) {
                    endRow = startRow + queryResult.size() - 1;
                }
                log.info("\n\n----->>>>Received queryResult:Size:--" + queryResult.size());
                /**数据入库**/
                List<Integer> insertResList = dbOperateService.insertIntoTargetTable(queryResult, scheduleModel);

                //记录在请求日志表中
                int count = doJobLogging(SQL, scheduleModel, queryResult, insertResList, startRow, endRow, 1, singleJobTotalSuccessCount, singleJobTotalFailCount);

                if (compareRes == true) {
                    compareRes = false;//该标志位只在启动时有效，之后不能以该标志位作为参考,故设为false
                }
                if (logModel != null) {
                    if (logModel.getQueryResultSize() == scheduleModel.getPageSize()) {
                        reachedLastRow = false; //未翻到最后一页，继续循环
                    } else {
                        return true;        //已翻到最后一页，结束循环
                    }
                }
            }
        } else if (CommonInstance.NO_PAGING == scheduleModel.getIsPagingQuery()) {
            //设定分页参数
            startRow = 1;
            endRow = 9999999;
            //创建新的SQL请求语句
            String SQL = SQLCreator.createSQLByTbNameAndRowParam(scheduleModel.getSource(), startRow, endRow, scheduleModel.getOrderByColumnName());
            //获取结果数据
            List<HashMap> queryResult = dbQueryService.oracleQuerySql(SQL);
            log.info("\n\n----->>>>Received queryResult:Size:--" + queryResult.size());
            /**数据入库**/
            List<Integer> insertResList = dbOperateService.insertIntoTargetTable(queryResult, scheduleModel);
            //记录在请求日志表中
            int count = doJobLogging(SQL, scheduleModel, queryResult, insertResList, startRow, endRow, 1, singleJobTotalSuccessCount, singleJobTotalFailCount);
            if (count >= 1) {
                return true;
            }
        }
        return true;
    }

    private int doJobLogging(String SQL, ScheduleModel scheduleModel, List<HashMap> queryResult, List<Integer> insertResList
            , int startRow, int endRow, int toDoPageNum, int singleJobTotalSuccessCount, int singleJobTotalFailCount) {
        //记录在请求日志表中
        SynchJobLogInfoModel synchJobLogInfoModel = new SynchJobLogInfoModel();
        synchJobLogInfoModel.setJobId(scheduleModel.getId());
        synchJobLogInfoModel.setIsSuccess(insertResList.get(0));
        synchJobLogInfoModel.setCurrentPageSize(scheduleModel.getPageSize());
        synchJobLogInfoModel.setCurrentPageNum(toDoPageNum);
        synchJobLogInfoModel.setStartRow(startRow);
        synchJobLogInfoModel.setEndRow(endRow);
        synchJobLogInfoModel.setQueryResultSize(queryResult.size());
        synchJobLogInfoModel.setConnType(scheduleModel.getConnType());
        synchJobLogInfoModel.setSuccessCount(insertResList.get(1));
        synchJobLogInfoModel.setFailCount(insertResList.get(2));
        singleJobTotalSuccessCount += insertResList.get(1);
        singleJobTotalFailCount += insertResList.get(2);
        synchJobLogInfoModel.setTotalSuccessCount(singleJobTotalSuccessCount);
        synchJobLogInfoModel.setTotalFailCount(singleJobTotalFailCount);

        int count = synchJobLogInfoService.add(synchJobLogInfoModel);

        if (count >= 1) {
            log.info("\n\n----->>>>successfuly done a SQL query: " +
                            "\n" + SQL + "," +
                            "\ncurrent tableName: " + scheduleModel.getTargetTableName() +
                            "\ncurrent page: " + synchJobLogInfoModel.getLastQueryPageNum() +
                            "\nqueryResult.size(): " + queryResult.size() +
                            "\ncurrent page success:" + synchJobLogInfoModel.getSuccessCount() +
                            "\ncurrent page fail:" + synchJobLogInfoModel.getFailCount() +
                            "\ntotal success:" + synchJobLogInfoModel.getTotalSuccessCount() +
                            "\ntotal fail:" + synchJobLogInfoModel.getTotalFailCount()
            );
        }
        return count;
    }

    /**
     * 发起Http在线轮询请求
     *
     * @return
     */
    private boolean doHttpPulling(SynchJobLogInfoService synchJobLogInfoService, HttpOperateService httpOperateService, DbOperateService dbOperateService) throws SQLException {

        /**变量创建*/
        int singleJobTotalSuccessCount = 0;
        int singleJobTotalFailCount = 0;

        //用来计算最新的分页参数
        int toDoPageNum = CommonInstance.DEFAULT_START_PAGE_NUM;
        SynchJobLogInfoModel logModel = null;


        if (CommonInstance.DO_PAGING == scheduleModel.getIsPagingQuery()) {

            try {
                logModel = synchJobLogInfoService.queryLatestInfoByJobId(scheduleModel.getId());
                singleJobTotalSuccessCount = logModel.getTotalSuccessCount();
                singleJobTotalFailCount = logModel.getTotalFailCount();
            } catch (Exception e) {
                log.info("recent paging param is null!");
            }

            // 如果当前请求结果集大小大于上次结果集大小，则进行入库
            boolean doInsertQueryResult = queryDetect(scheduleModel, logModel);

            log.info("\n\n----->>>> current page has new data? -- " + doInsertQueryResult);

            if (doInsertQueryResult == true) {

                /**初次执行or未到达最后一页or有新的数据 --- 继续请求**/
                // 判断是否到达最后一页(未到达最后一页的标志：queryDataSize==pageSize; 若达到最后一页: rowNum==0||rownum<pageSize
                // null==model表示还未发起过请求)
                boolean reachedLastRow = false;
                while (null == logModel || !reachedLastRow) {
                    log.info("\n\n----->>>> current job is running! query doesn't reach the end ");
                    //重新计算分页参数toDoPageNum
                    if (null == logModel) {
                        toDoPageNum = CommonInstance.DEFAULT_START_PAGE_NUM;
                        //比较结果为false(说明已经在while循环中且已经完成了第一次循环)&&上次访问到的数据体大小==pageSize： todoPage+1
                    } else if (reachedLastRow == false && logModel.getQueryResultSize() == scheduleModel.getPageSize()&&logModel.getIsSuccess()==CommonInstance.SUCCESS) {
                        toDoPageNum = logModel.getLastQueryPageNum() + 1;
                        //比较结果为true&上次访问到的数据体大小<pageSize：todoPage不变，做非完整页面续接
                    } else if (logModel.getQueryResultSize() < scheduleModel.getPageSize()) {
                        reachedLastRow = true;
                        toDoPageNum = logModel.getLastQueryPageNum();
                    }

                    /** 通过Http在线获取数据**/
                    List<HashMap> queryResult = httpOperateService.doHttpQueryList(scheduleModel, toDoPageNum);
                    //当前请求为null,则中断当前请求，并输出在log日志中
                    if (null == queryResult) {
                        log.error("\n\nqueryResult is null:" +
                                "\n\nscheduleModel:" + scheduleModel.toString() +
                                "\n\n toDoPageNum:" + toDoPageNum);
                        SynchJobLogInfoModel synchJobLogInfoModel = new SynchJobLogInfoModel();
                        synchJobLogInfoModel.setJobId(scheduleModel.getId());
                        synchJobLogInfoModel.setIsSuccess(0);
                        synchJobLogInfoModel.setCurrentPageSize(scheduleModel.getPageSize());
                        synchJobLogInfoModel.setCurrentPageNum(toDoPageNum);
                        synchJobLogInfoModel.setQueryResultSize(0);
                        synchJobLogInfoModel.setConnType(scheduleModel.getConnType());
                        synchJobLogInfoModel.setTotalSuccessCount(singleJobTotalSuccessCount);
                        synchJobLogInfoModel.setTotalFailCount(singleJobTotalFailCount);
                        int count = synchJobLogInfoService.add(synchJobLogInfoModel);
                        return false;
                    }

                    // 根据上次log日志，提取未入库的内容进行入库，同时记录该位置，以供下次查询比对，pageNum不变
                    int lastQueryResultSize = 0;
                    List<Integer> insertResList = null;
                    List<HashMap> subQueryResult = null;
                    if (logModel != null) {
                        lastQueryResultSize = logModel.getQueryResultSize();
                    }
                    /**数据入库**/
                    if (logModel == null || queryResult.size() == lastQueryResultSize && logModel.getLastQueryPageNum() < toDoPageNum
                            || queryResult.size() < scheduleModel.getPageSize() && logModel.getLastQueryPageNum() < toDoPageNum) {
                        //未到达最后一页的入库方式：全量入库 // 已到达最后一页：全量入库
                        insertResList = dbOperateService.insertIntoTargetTable(queryResult, scheduleModel);
                    } else if (queryResult.size() > lastQueryResultSize && logModel.getLastQueryPageNum() == toDoPageNum) {
                        subQueryResult = queryResult.subList(lastQueryResultSize, queryResult.size());
                        //再次启动后：已到达最后一页的续接方式：新增部分入库
                        insertResList = dbOperateService.insertIntoTargetTable(subQueryResult, scheduleModel);
                    }

                    //如果本次请求大小等于一页，则需要继续进行分页查询
                    if (queryResult.size() == scheduleModel.getPageSize()) {
                        reachedLastRow = false;
                    } else if (queryResult.size() <= scheduleModel.getPageSize()) {
                        reachedLastRow = true;
                    }


                    log.info("\n\n----->>>>Received queryResult:Size:--" + queryResult.size());

                    //记录在请求日志表中
                    SynchJobLogInfoModel synchJobLogInfoModel = new SynchJobLogInfoModel();
                    synchJobLogInfoModel.setJobId(scheduleModel.getId());
                    if (insertResList != null) {
                        synchJobLogInfoModel.setIsSuccess(insertResList.get(0));
                        synchJobLogInfoModel.setSuccessCount(insertResList.get(1));
                        synchJobLogInfoModel.setFailCount(insertResList.get(2));
                        singleJobTotalSuccessCount += synchJobLogInfoModel.getSuccessCount();
                        singleJobTotalFailCount += synchJobLogInfoModel.getFailCount();
                    }else {
                        synchJobLogInfoModel.setJobId(scheduleModel.getId());
                        synchJobLogInfoModel.setIsSuccess(0);
                        synchJobLogInfoModel.setCurrentPageSize(scheduleModel.getPageSize());
                        synchJobLogInfoModel.setCurrentPageNum(toDoPageNum);
                        synchJobLogInfoModel.setQueryResultSize(0);
                        synchJobLogInfoModel.setConnType(scheduleModel.getConnType());
                        synchJobLogInfoModel.setTotalSuccessCount(singleJobTotalSuccessCount);
                        synchJobLogInfoModel.setTotalFailCount(singleJobTotalFailCount);
                        int count = synchJobLogInfoService.add(synchJobLogInfoModel);
                        return false;
                    }
                    synchJobLogInfoModel.setCurrentPageSize(scheduleModel.getPageSize());
                    synchJobLogInfoModel.setCurrentPageNum(toDoPageNum);
                    synchJobLogInfoModel.setQueryResultSize(queryResult.size());
                    synchJobLogInfoModel.setConnType(scheduleModel.getConnType());

                    synchJobLogInfoModel.setTotalSuccessCount(singleJobTotalSuccessCount);
                    synchJobLogInfoModel.setTotalFailCount(singleJobTotalFailCount);

                    int count = synchJobLogInfoService.add(synchJobLogInfoModel);

                    if (count >= 1) {
                        log.info("\n\n----->>>>successfuly done a HTTP query: " +
                                        "\n" + scheduleModel.getSource() + "," +
                                        "\ncurrent tableName: " + scheduleModel.getTargetTableName() +
                                        "\ncurrent page: " + synchJobLogInfoModel.getLastQueryPageNum() +
                                        "\nqueryResult.size(): " + queryResult.size() +
                                        "\ncurrent page success:" + synchJobLogInfoModel.getSuccessCount() +
                                        "\ncurrent page fail:" + synchJobLogInfoModel.getFailCount() +
                                        "\ntotal success:" + synchJobLogInfoModel.getTotalSuccessCount() +
                                        "\ntotal fail:" + synchJobLogInfoModel.getTotalFailCount()
                        );
                    } else {
                        log.error("failed done a Http query: " + scheduleModel.getSource() + ",\ncurrent tableName: " + scheduleModel.getTargetTableName() + ",current page: " + toDoPageNum);
                        return false;
                    }

                    //更新log信息
                    try {
                        logModel = synchJobLogInfoService.queryLatestInfoByJobId(scheduleModel.getId());
                    } catch (Exception e) {
                        log.info("获取最近一次日志的分页参数为NULL！");
                    }
                }
                return true;
            }
            //不做分页,定时全量入库
        } else if (CommonInstance.NO_PAGING == scheduleModel.getIsPagingQuery()) {
            //通过Http在线获取数据
            List<HashMap> queryResult = httpOperateService.doHttpQueryList(scheduleModel, toDoPageNum);

            if (null == queryResult) {
                log.info("queryResult is: null");
                SynchJobLogInfoModel synchJobLogInfoModel = new SynchJobLogInfoModel();
                synchJobLogInfoModel.setJobId(scheduleModel.getId());
                synchJobLogInfoModel.setIsSuccess(0);
                synchJobLogInfoModel.setCurrentPageSize(scheduleModel.getPageSize());
                synchJobLogInfoModel.setCurrentPageNum(toDoPageNum);
                synchJobLogInfoModel.setQueryResultSize(0);
                synchJobLogInfoModel.setConnType(scheduleModel.getConnType());
                synchJobLogInfoModel.setTotalSuccessCount(singleJobTotalSuccessCount);
                synchJobLogInfoModel.setTotalFailCount(singleJobTotalFailCount);
                int count = synchJobLogInfoService.add(synchJobLogInfoModel);
                return false;
            }
            log.info("\n" +
                    "\n" +
                    "----->>>>Received queryResult:Size:--" + queryResult.size());
            /**数据入库**/
            List<Integer> insertResList = dbOperateService.insertIntoTargetTable(queryResult, scheduleModel);

            //记录在请求日志表中
            SynchJobLogInfoModel synchJobLogInfoModel = new SynchJobLogInfoModel();
            synchJobLogInfoModel.setJobId(scheduleModel.getId());
            synchJobLogInfoModel.setIsSuccess(insertResList.get(0));
            synchJobLogInfoModel.setCurrentPageSize(scheduleModel.getPageSize());
            synchJobLogInfoModel.setCurrentPageNum(toDoPageNum);
            synchJobLogInfoModel.setQueryResultSize(queryResult.size());
            synchJobLogInfoModel.setConnType(scheduleModel.getConnType());
            synchJobLogInfoModel.setSuccessCount(insertResList.get(1));
            synchJobLogInfoModel.setFailCount(insertResList.get(2));
            singleJobTotalSuccessCount += insertResList.get(1);
            singleJobTotalFailCount += insertResList.get(2);
            synchJobLogInfoModel.setTotalSuccessCount(singleJobTotalSuccessCount);
            synchJobLogInfoModel.setTotalFailCount(singleJobTotalFailCount);

            int count = synchJobLogInfoService.add(synchJobLogInfoModel);

            if (count >= 1) {
                log.info("\n\n----->>>>successfuly done a SQL query: " +
                                "\n" + scheduleModel.getSource() + "," +
                                "\ncurrent tableName: " + scheduleModel.getTargetTableName() +
                                "\ncurrent page: " + synchJobLogInfoModel.getLastQueryPageNum() +
                                "\nqueryResult.size(): " + queryResult.size() +
                                "\ncurrent page success:" + synchJobLogInfoModel.getSuccessCount() +
                                "\ncurrent page fail:" + synchJobLogInfoModel.getFailCount() +
                                "\ntotal success:" + synchJobLogInfoModel.getTotalSuccessCount() +
                                "\ntotal fail:" + synchJobLogInfoModel.getTotalFailCount()
                );

            } else {
                log.error("failed done a Http query: " + scheduleModel.getSource() + ",\ncurrent tableName: " + scheduleModel.getTargetTableName() + ",current page: " + toDoPageNum);
                return false;
            }
            return true;
        }

        return true;
    }

    private boolean queryDetect(ScheduleModel scheduleModel, SynchJobLogInfoModel logModel) {
        if (null == logModel) {
            return true;
        }
        int currentPageTotalRows = httpOperateService.getHttpCurrentPageTotalRows(scheduleModel, logModel);
        int lastPageTotalRows = logModel.getQueryResultSize();
        if (currentPageTotalRows == scheduleModel.getPageSize()) {
            return true;
        } else {
            return currentPageTotalRows > lastPageTotalRows ? true : false;
        }

    }
}


