package com.cetc.datasynch.model;

import com.cetc.datasynch.middleware.SQLCreator;
import com.cetc.datasynch.service.impl.DBScheduleService;
import com.cetc.datasynch.service.impl.DbOperateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Description：创建执行实例
 * Created by luolinjie on 2018/10/10.
 */
public class MySQLRunnable implements Runnable {
    private String jobId;

    @Autowired
    DbOperateService dbOperateService;
    @Autowired
    DBScheduleService dbScheduleService;


    public MySQLRunnable(String jobId){
        this.jobId = jobId;
    }
    @Override
    public void run() {
        try {
            //1.根据tableName查询最近一条执行成功的数据同步日志对应的分页参数
            SynchJobLogInfoModel model = dbScheduleService.queryModelByJobId(jobId);
            String SQL = SQLCreator.createSQLByTbNameAndRowParam(model.getTableName(), model.getCurrentPageNum() + 1, model.getCurrentPageSize());
            List<HashMap> hashMaps = dbOperateService.oracleQuerySql(SQL);
            //判断是否到达最后一页


            //若到达，则。。。
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
