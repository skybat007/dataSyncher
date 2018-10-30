package com.cetc.cloud.datasynch.provider.template;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.api.model.SynchJobLogInfoModel;
import com.cetc.cloud.datasynch.provider.middleware.SQLCreator;
import com.cetc.cloud.datasynch.provider.service.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Description：创建执行实例
 * Created by luolinjie on 2018/10/10.
 */
public class MyScheduleRunnable implements Runnable {
    private ScheduleModel scheduleModel;

    private Logger logger = LoggerFactory.getLogger(MyScheduleRunnable.class);
    private SynchJobLogInfoService synchJobLogInfoService;
    private DbQueryService dbQueryService;
    private DbOperateService dbOperateService;
    private HttpOperateService httpOperateService;

    public MyScheduleRunnable(ScheduleModel scheduleModel, SynchJobLogInfoService synchJobLogInfoService, DbQueryService dbQueryService, DbOperateService dbOperateService, HttpOperateService httpOperateService) {
        this.scheduleModel = scheduleModel;
        this.synchJobLogInfoService = synchJobLogInfoService;
        this.dbQueryService = dbQueryService;
        this.dbOperateService = dbOperateService;
        this.httpOperateService = httpOperateService;
    }

    @Override
    public void run() {
//        Thread.currentThread().setName(scheduleModel.getTargetTableName());
        logger.info("\n\n----->>>> executing run() method,\nThread.currentThread().Name:"
                +Thread.currentThread().getName()
                +"\nThread.currentThread().getId():"+Thread.currentThread().getId());
        try {
            //根据接入方式决定生成SQL query还是Http请求
            if (scheduleModel.getConnType() == 0) {
                //数据库接入方式
                boolean b = doSQLPulling(synchJobLogInfoService, dbQueryService, dbOperateService);
                if (b) {
                    logger.info("\n\n----->>>>MyScheduleRunnable.doSQLPulling：finished!!!");
                } else {
                    logger.info("\n\n----->>>>MyScheduleRunnable.doSQLPulling：finished with error!!!");
                }
            } else if (scheduleModel.getConnType() == 1) {
                //接口接入方式
                doHttpPulling(synchJobLogInfoService, httpOperateService, dbOperateService);
            } else return;
        } catch (SQLException e) {
            e.printStackTrace();
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
        int tableRowCounts_src = dbQueryService.getTableRowCounts(scheduleModel.getSource());
        int tableRowCounts_trgt = dbOperateService.getTableRowCounts(scheduleModel.getTargetTableName());
        boolean compareRes = tableRowCounts_src > tableRowCounts_trgt ? true : false;
        logger.info("\n\n----->>>> SourceTable.total > TargetTable.total? " + compareRes);
        boolean reachedLastRow = false;
        //判断是否到达最后一页(未到达最后一页的标志：rowNum==pageSize;若达到最后一页: rowNum==0||rownum<pageSize
        // null==model表示还未发起过请求)
        //增加第二次启动任务的逻辑：
        if (compareRes == true) {
            /**初次执行；未到达最后一页；两表大小不一致 --- 继续请求**/
            while (null == logModel || !reachedLastRow) {
                //1.根据tableName查询最近一条执行成功的数据同步日志对应的分页参数
                try {
                    logModel = synchJobLogInfoService.queryLatestInfoByJobId(scheduleModel.getId());
                } catch (Exception e) {
                    logger.info("获取最近一次日志的分页参数为NULL！");
                }
                logger.info("\n\n----->>>> current job is running! query doesn't reach the end ");

                //重新计算分页参数
                if (null == logModel) {
                    toDoPageNum = 1;
                    startRow = 1;
                    endRow = startRow + scheduleModel.getPageSize() - 1;
                } else if (compareRes == true) { //初次执行while循环
                    toDoPageNum = 1;
                    startRow = logModel.getEndRow() + 1;
                    //通过pagenum和pagesize计算StartRow和EndRow
                    endRow = startRow + scheduleModel.getPageSize() - 1;
                } else {
                    if (compareRes == true) {
                        toDoPageNum = 1;
                    } else {
                        toDoPageNum = logModel.getCurrentPageNum() + 1;
                    }

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
                logger.info("\n" +
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
                    logger.info("\n\n----->>>>successfuly done a SQL query: " +
                                    "\n" + SQL + "," +
                                    "\ncurrent tableName: " + scheduleModel.getTargetTableName() +
                                    "\ncurrent page: " + synchJobLogInfoModel.getCurrentPageNum() +
                                    "\nqueryResult.size(): " + queryResult.size() +
                                    "\ncurrent page success:" + synchJobLogInfoModel.getSuccessCount() +
                                    "\ncurrent page fail:" + synchJobLogInfoModel.getFailCount() +
                                    "\ntotal success:" + synchJobLogInfoModel.getTotalSuccessCount() +
                                    "\ntotal fail:" + synchJobLogInfoModel.getTotalFailCount()
                    );

                } else {
                    logger.error("failed done a SQL query: " + SQL + ",\ncurrent tableName: " + scheduleModel.getTargetTableName() + ",current page: " + toDoPageNum);
                    return false;
                }

                if (compareRes == true) {
                    compareRes = false;//该标志位只在启动时有效，之后不能以该标志位作为参考,故设为false
                }
                if (logModel != null) {
                    if (logModel.getQueryResultSize() == scheduleModel.getPageSize()) {
                        reachedLastRow = false;
                    } else {
                        return true;
                    }
                }
            }
        }

        return true;
    }


    /**
     * 发起Http在线轮询请求
     *
     * @return
     */
    private boolean doHttpPulling(SynchJobLogInfoService synchJobLogInfoService, HttpOperateService httpOperateService, DbOperateService dbOperateService) throws SQLException {
        //如果有认证鉴权，获取token认证信息

        /**变量创建*/
        int singleJobTotalSuccessCount = 0;
        int singleJobTotalFailCount = 0;
        //用来计算最新的分页参数
        int toDoPageNum = 1;
        SynchJobLogInfoModel logModel = null;

        //获取源接口中的数据总数，当总数大于目标表时，则需要启动任务执行该任务
        int tableRowCounts_src = httpOperateService.getTotalRows(scheduleModel);
        int tableRowCounts_trgt = dbOperateService.getTableRowCounts(scheduleModel.getTargetTableName());
        boolean compareRes = tableRowCounts_src > tableRowCounts_trgt ? true : false;
        logger.info("\n\n----->>>> SourceTable.total > TargetTable.total? " + compareRes);
        boolean reachedLastRow = false;
        //判断是否到达最后一页(未到达最后一页的标志：queryDataSize==pageSize; 若达到最后一页: rowNum==0||rownum<pageSize
        // null==model表示还未发起过请求)
        //增加第二次启动任务的逻辑：
        if (compareRes == true) {
            /**初次执行；未到达最后一页；两表大小不一致 --- 继续请求**/
            while (null == logModel || !reachedLastRow) {
                //1.根据tableName查询最近一条执行成功的数据同步日志对应的分页参数
                try {
                    logModel = synchJobLogInfoService.queryLatestInfoByJobId(scheduleModel.getId());
                } catch (Exception e) {
                    logger.info("获取最近一次日志的分页参数为NULL！");
                }
                logger.info("\n\n----->>>> current job is running! query doesn't reach the end ");

                //重新计算分页参数
                if (null == logModel) {
                    toDoPageNum = 1;
                } else if (compareRes == true) { //初次执行while循环
                    toDoPageNum = 1;
                } else {
                    if (compareRes == true) {
                        toDoPageNum = 1;
                    } else {
                        toDoPageNum = logModel.getCurrentPageNum() + 1;
                    }
                }

                //通过Http在线获取数据
                List<HashMap> queryResult = httpOperateService.doHttpQuery(scheduleModel, toDoPageNum);

                if (null == queryResult) {
                    break;
                }
                logger.info("\n" +
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
                    logger.info("\n\n----->>>>successfuly done a SQL query: " +
                                    "\n" + scheduleModel.getSource() + "," +
                                    "\ncurrent tableName: " + scheduleModel.getTargetTableName() +
                                    "\ncurrent page: " + synchJobLogInfoModel.getCurrentPageNum() +
                                    "\nqueryResult.size(): " + queryResult.size() +
                                    "\ncurrent page success:" + synchJobLogInfoModel.getSuccessCount() +
                                    "\ncurrent page fail:" + synchJobLogInfoModel.getFailCount() +
                                    "\ntotal success:" + synchJobLogInfoModel.getTotalSuccessCount() +
                                    "\ntotal fail:" + synchJobLogInfoModel.getTotalFailCount()
                    );

                } else {
                    logger.error("failed done a Http query: " + scheduleModel.getSource() + ",\ncurrent tableName: " + scheduleModel.getTargetTableName() + ",current page: " + toDoPageNum);
                    return false;
                }

                if (compareRes == true) {
                    compareRes = false;//该标志位只在启动时有效，之后不能以该标志位作为参考,故设为false
                }
                if (logModel != null) {
                    if (logModel.getQueryResultSize() == scheduleModel.getPageSize()) {
                        reachedLastRow = false;
                    } else {
                        return true;
                    }
                }
            }
        }

        return true;
    }
}


