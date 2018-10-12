package com.cetc.datasynch.model;

import com.cetc.datasynch.middleware.SQLCreator;
import com.cetc.datasynch.service.ScheduleService;
import com.cetc.datasynch.service.DbOperateService;
import com.cetc.datasynch.service.SynchJobLogInfoService;
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

    @Autowired
    DbOperateService dbOperateService;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    SynchJobLogInfoService synchJobLogInfoService;

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
            int toDoPage = 9999999;
            if (null == model) {
                toDoPage = 1;
            } else {
                toDoPage = model.getCurrentPageNum() + 1;
            }

            String SQL = SQLCreator.createSQLByTbNameAndRowParam(scheduleModel.getTableName(), toDoPage, scheduleModel.getPageSize());
            //获取数据
            List<HashMap> queryResult = dbOperateService.oracleQuerySql(SQL);

            /**数据入库**/
            dbOperateService.insertToTargetTable(queryResult,scheduleModel);

            //记录在请求日志表中
            SynchJobLogInfoModel synchJobLogInfoModel = new SynchJobLogInfoModel();
            synchJobLogInfoModel.setPageSize();
            synchJobLogInfoService.addNewLogInfo();
        }
        return false;
    }


    /**
     * 发起Http在线轮询请求
     *
     * @return
     */
    private boolean doHttpPulling() {


        return false;
    }

}
