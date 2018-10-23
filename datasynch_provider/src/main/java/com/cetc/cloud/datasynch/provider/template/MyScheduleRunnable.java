package com.cetc.cloud.datasynch.provider.template;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.provider.middleware.SQLCreator;
import com.cetc.cloud.datasynch.provider.service.DbOperateService;
import com.cetc.cloud.datasynch.provider.service.HttpOperateService;
import com.cetc.cloud.datasynch.provider.service.ScheduleService;
import com.cetc.cloud.datasynch.provider.service.SynchJobLogInfoService;
import com.cetc.cloud.datasynch.api.model.SynchJobLogInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
    @Autowired
    DbOperateService dbOperateService;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    SynchJobLogInfoService synchJobLogInfoService;
    @Autowired
    HttpOperateService httpOperateService;

    public MyScheduleRunnable(ScheduleModel scheduleModel) {
        this.scheduleModel = scheduleModel;
    }

    @Override
    public void run() {
        try {
            //根据接入方式决定生成SQL query还是Http请求
            if (scheduleModel.getConnType() == 0) {
                //数据库接入方式
                doSQLPulling();
            } else if (scheduleModel.getConnType() == 1) {
                //接口接入方式
                doHttpPulling();
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
    private boolean doSQLPulling() throws SQLException {
        //1.根据tableName查询最近一条执行成功的数据同步日志对应的分页参数
        SynchJobLogInfoModel model =null;
        try {
            model = synchJobLogInfoService.queryLatestInfoByJobId(scheduleModel.getId());
        }catch (Exception e){
            logger.error("获取最近一次日志的分页参数为NULL！");
//            e.printStackTrace();
        }

        int singleJobTotalSuccessCount = 0;
        int singleJobTotalFailCount = 0;

        //判断是否到达最后一页(未到达最后一页的标志：rowNum==pageSize;若达到最后一页: rowNum==0||rownum<pageSize
        // null==model表示还未发起过请求)
        while (null == model || model.getCurrentRownum() == scheduleModel.getPageSize()) {
            /**未到达最后一页，继续请求**/
            //计算最新的分页参数
            int toDoPage = -1;
            if (null == model) {
                toDoPage = 1;
            } else {
                toDoPage = model.getCurrentPageNum() + 1;
            }
            //创建新的SQL请求语句
            String SQL = SQLCreator.createSQLByTbNameAndRowParam(scheduleModel.getTargetTableName(), toDoPage, scheduleModel.getPageSize());
            //todo: 初始化Oracle连接
            //获取数据
            List<HashMap> queryResult = dbOperateService.oracleQuerySql(SQL);
            logger.info("Received queryResult:Size:--" + queryResult.size());
            /**数据入库**/
            List<Integer> insertResList = dbOperateService.insertIntoTargetTable(queryResult, scheduleModel);

            //记录在请求日志表中
            SynchJobLogInfoModel synchJobLogInfoModel = new SynchJobLogInfoModel();
            synchJobLogInfoModel.setJobId(scheduleModel.getId());
            synchJobLogInfoModel.setCurrentPageSize(scheduleModel.getPageSize());
            synchJobLogInfoModel.setCurrentPageNum(toDoPage);
            synchJobLogInfoModel.setCurrentRownum(queryResult.size());
            synchJobLogInfoModel.setConnType(scheduleModel.getConnType());
            synchJobLogInfoModel.setSuccessCount(insertResList.get(0));
            synchJobLogInfoModel.setFailCount(insertResList.get(1));
            singleJobTotalSuccessCount += insertResList.get(0);
            singleJobTotalFailCount += insertResList.get(1);
            synchJobLogInfoModel.setTotalSuccessCount(singleJobTotalSuccessCount);
            synchJobLogInfoModel.setTotalFailCount(singleJobTotalFailCount);

            int count = synchJobLogInfoService.add(synchJobLogInfoModel);

            if (count >= 1) {
                logger.info("successfuly done a SQL query: " + SQL + "," +
                                "\ncurrent tableName: " + scheduleModel.getTargetTableName() +
                                "\ncurrent page: " + toDoPage +
                                "\ncurrent page success:"+synchJobLogInfoModel.getSuccessCount()+
                                "\ncurrent page fail:"+synchJobLogInfoModel.getFailCount()+
                                "\ntotal success:"+synchJobLogInfoModel.getTotalSuccessCount()+
                                "\ntotal fail:"+synchJobLogInfoModel.getTotalFailCount()
                );
                return true;
            } else {
                logger.error("failed done a SQL query: " + SQL + ",\ncurrent tableName: " + scheduleModel.getTargetTableName() + ",current page: " + toDoPage);
                return false;
            }
        }
        return false;
    }


    /**
     * 发起Http在线轮询请求
     *
     * @return
     */
    private boolean doHttpPulling() throws SQLException {
        //1.根据tableName查询最近一条执行成功的数据同步日志对应的分页参数
        SynchJobLogInfoModel model = synchJobLogInfoService.queryLatestInfoByJobId(scheduleModel.getId());

        //临时变量，请求完成后将日志记录在请求日志表中
        SynchJobLogInfoModel synchJobLogInfoModel = null;
        int singleJobTotalSuccessCount = 0;
        int singleJobTotalFailCount = 0;
        //计算最新的分页参数
        int toDoPage = -1;
        List<HashMap> queryResult = null;
        List<Integer> insertResList = null;
        if (null == scheduleModel.getHttpParamPageSize() && null == scheduleModel.getHttpParamPageNum()) {
            /**一次请求，完成即结束*/
            //获取数据
            queryResult = httpOperateService.doHttpQuery(scheduleModel, 0);

            /**数据入库**/
            insertResList = dbOperateService.insertIntoTargetTable(queryResult, scheduleModel);

        } else {
            //判断是否到达最后一页(未到达最后一页的标志：rowNum==pageSize;
            // null == model 表示还未通过job发起过请求)
            while (null == model || model.getCurrentRownum() == scheduleModel.getPageSize()) {
                /**未到达最后一页，继续请求**/

                if (null == model) {
                    toDoPage = 1;
                } else {
                    toDoPage = model.getCurrentPageNum() + 1;
                }
                //获取数据
                queryResult = httpOperateService.doHttpQuery(scheduleModel, toDoPage);


                /**数据入库**/
                insertResList = dbOperateService.insertIntoTargetTable(queryResult, scheduleModel);
            }
        }
        //记录日志
        synchJobLogInfoModel = new SynchJobLogInfoModel();
        synchJobLogInfoModel.setJobId(scheduleModel.getId());
        synchJobLogInfoModel.setCurrentPageSize(scheduleModel.getPageSize());
        synchJobLogInfoModel.setCurrentPageNum(toDoPage);
        synchJobLogInfoModel.setCurrentRownum(queryResult.size());
        synchJobLogInfoModel.setConnType(scheduleModel.getConnType());
        synchJobLogInfoModel.setSuccessCount(insertResList.get(0));
        synchJobLogInfoModel.setFailCount(insertResList.get(1));
        singleJobTotalSuccessCount += insertResList.get(0);
        singleJobTotalFailCount += insertResList.get(1);
        synchJobLogInfoModel.setTotalSuccessCount(singleJobTotalSuccessCount);
        synchJobLogInfoModel.setTotalFailCount(singleJobTotalFailCount);

        int count = synchJobLogInfoService.add(synchJobLogInfoModel);

        if (count >= 1) {
            logger.info("successfuly do a Http query: " + scheduleModel.getSource() + ",\nTarget tableName is: " + scheduleModel.getTargetTableName() + " \n,Current page is: " + toDoPage);
            return true;
        } else {
            logger.error("failed do a Http query: " + scheduleModel.getSource() + ",\nTarget tableName is: " + scheduleModel.getTargetTableName() + ",Current page is: " + toDoPage);
            return false;
        }
    }
}


