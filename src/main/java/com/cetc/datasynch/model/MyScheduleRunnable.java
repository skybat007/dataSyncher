package com.cetc.datasynch.model;

import com.cetc.datasynch.middleware.SQLCreator;
import com.cetc.datasynch.service.HttpOperateService;
import com.cetc.datasynch.service.ScheduleService;
import com.cetc.datasynch.service.DbOperateService;
import com.cetc.datasynch.service.SynchJobLogInfoService;
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
        SynchJobLogInfoModel model = synchJobLogInfoService.queryLatestInfoByJobId(scheduleModel.getId());

        //判断是否到达最后一页(未到达最后一页的标志：rowNum==pageSize;null == model表示还未发起过请求)
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
            String SQL = SQLCreator.createSQLByTbNameAndRowParam(scheduleModel.getTableName(), toDoPage, scheduleModel.getPageSize());
            //获取数据
            List<HashMap> queryResult = dbOperateService.oracleQuerySql(SQL);

            /**数据入库**/
            dbOperateService.insertIntoTargetTable(queryResult, scheduleModel);

            //记录在请求日志表中
            SynchJobLogInfoModel synchJobLogInfoModel = new SynchJobLogInfoModel();
            synchJobLogInfoModel.setJobId(scheduleModel.getId());
            synchJobLogInfoModel.setPageSize(scheduleModel.getPageSize());
            synchJobLogInfoModel.setCurrentPageNum(toDoPage);
            synchJobLogInfoModel.setCurrentRownum(queryResult.size());
            synchJobLogInfoModel.setConnType(scheduleModel.getConnType());
            int count = synchJobLogInfoService.add(synchJobLogInfoModel);
            if (count>=1) {
                logger.info("successfuly do a SQL query: "+SQL+",\ncurrent tableName is: "+scheduleModel.getTableName()+" \n,current page is: "+toDoPage);
            }else {
                logger.error("failed do a SQL query: " + SQL + ",\ncurrent tableName is: " +scheduleModel.getTableName()+ ",current page is: "+toDoPage);
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

        //判断是否到达最后一页(未到达最后一页的标志：rowNum==pageSize;null == model表示还未发起过请求)
        while (null == model || model.getCurrentRownum() == scheduleModel.getPageSize()) {
            /**未到达最后一页，继续请求**/
            //计算最新的分页参数
            int toDoPage = -1;
            if (null == model) {
                toDoPage = 1;
            } else {
                toDoPage = model.getCurrentPageNum() + 1;
            }
            //获取数据
            List<HashMap> queryResult = httpOperateService.doHttpQuery(scheduleModel,toDoPage);

            /**数据入库**/
            dbOperateService.insertIntoTargetTable(queryResult, scheduleModel);

            //记录在请求日志表中
            SynchJobLogInfoModel synchJobLogInfoModel = new SynchJobLogInfoModel();
            synchJobLogInfoModel.setJobId(scheduleModel.getId());
            synchJobLogInfoModel.setPageSize(scheduleModel.getPageSize());
            synchJobLogInfoModel.setCurrentPageNum(toDoPage);
            synchJobLogInfoModel.setCurrentRownum(queryResult.size());
            synchJobLogInfoModel.setConnType(scheduleModel.getConnType());
            int count = synchJobLogInfoService.add(synchJobLogInfoModel);
            if (count>=1) {
                logger.info("successfuly do a Http query: "+scheduleModel.getSource()+",\nTarget tableName is: "+scheduleModel.getTableName()+" \n,Current page is: "+toDoPage);
            }else {
                logger.error("failed do a Http query: " + scheduleModel.getSource() + ",\nTarget tableName is: " +scheduleModel.getTableName()+ ",Current page is: "+toDoPage);
            }
        }
        return false;
    }

}
