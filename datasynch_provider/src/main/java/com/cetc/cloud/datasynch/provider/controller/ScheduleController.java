package com.cetc.cloud.datasynch.provider.controller;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.api.model.Token;
import com.cetc.cloud.datasynch.api.service.ScheduleRemoteService;
import com.cetc.cloud.datasynch.provider.middleware.SQLCreator;
import com.cetc.cloud.datasynch.provider.service.DbOperateService;
import com.cetc.cloud.datasynch.provider.service.JobManageService;
import com.cetc.cloud.datasynch.provider.service.ScheduleService;
import com.cetc.cloud.datasynch.provider.service.SynchJobLogInfoService;
import com.cetc.cloud.datasynch.provider.template.MyScheduleRunnable;
import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Description：数据库（前置机）定时任务管理
 * Created by luolinjie on 2018/10/9.
 */
@RestController
public class ScheduleController implements ScheduleRemoteService {

    @Autowired
    DbOperateService dbOperateService;

    @Autowired
    JobManageService jobManageService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    SynchJobLogInfoService dbLogInfoService;


    @Override
    public List<ScheduleModel> queryScheduleJobList() {
        List<ScheduleModel> list = scheduleService.queryScheduleJobList();
        return list;
    }


    @Override
    public HashMap createScheduleJob(int connType, String source, Token token, String jsonExtractRule, int pageSize, String tableName, String scheduleExpression) throws SQLException {

        HashMap res = new HashMap();

        ScheduleModel scheduleModel = new ScheduleModel();
        scheduleModel.setConnType(connType);
        scheduleModel.setSource(source);
        scheduleModel.setPageSize(pageSize);
        scheduleModel.setTableName(tableName);
        scheduleModel.setScheduleExpression(scheduleExpression);

        //参数完整性判断
        if (null == source || null == tableName || null == scheduleExpression) {
            res.put("failed", "param error! please check your params!");
            return res;
        }
        //
        if (connType == CommonInstance.TYPE_INTERFACE && null != token && null != jsonExtractRule) {
            scheduleModel.setToken(token);
            scheduleModel.setJsonExtractRule(jsonExtractRule);
        }

        //将创建的任务记录在schedule表中
        int jobId = scheduleService.addScheduleInstance(scheduleModel);

        if (jobId == -1){
            res.put("failed", "error when creating schedule job");
            return res;
        }

        //创建定时任务
        MyScheduleRunnable myScheduleRunnable = new MyScheduleRunnable(scheduleModel);

        //启动任务
        int jobid = jobManageService.startJob(jobId, scheduleExpression, myScheduleRunnable);

        if (jobid == jobId){
            res.put("success", "create job:" + jobid + " success!");
        }


        return res;
    }


    @Override
    public HashMap<String, String> startScheduleJobByJobId(int jobID, String scheduleExpression, Runnable myScheduleRunnable) {
        HashMap res = new HashMap();
        //启动任务
        int jobid = jobManageService.startJob(jobID, scheduleExpression, myScheduleRunnable);

        if (jobid == jobID){
            res.put("success", "create job:" + jobid + " success!");
        }
        return res;
    }


    @Override
    public HashMap<String, String> deleteScheduleJobByJobId(int jobID) {

        HashMap result = new HashMap<String, String>();
        //停止当前任务
        String s = jobManageService.stopJob(jobID);
        //从列表中删除当前定时任务
        int res = scheduleService.deleteScheduleByJobId(jobID);

        if (res == 1) {
            result.put("delete success", "job:" + jobID);
            return result;
        } else {
            result.put("delete fail", "job:" + jobID);
            return result;
        }
    }


    @Override
    public HashMap<String, String> alterScheduleJob(int jobID, String cron) {

        HashMap result = new HashMap<String, String>();
        //停止当前任务
        String s = jobManageService.stopJob(jobID);


        //
        int res = scheduleService.updateCronByJobId(jobID, cron);

        //重新启动当前任务--通过JobID（前提是必须之前有这个Job）
        boolean restartRes = restartJobByJobId(jobID);
        if (restartRes == false) {
            //更改当前任务状态为Disabled
            scheduleService.disableStatusByJobId(jobID);
            result.put("job:" + jobID, "disableStatusByJobId failed!");
            return result;
        }

        boolean s1 = restartJobByJobId(jobID);
        if (s1) {
            result.put("job:" + jobID, "update cron success!");
            return result;
        } else {
            result.put("job:" + jobID, "update cron failed!");
            return result;
        }
    }


    @Override
    public boolean restartJobByJobId(int jobId) {
        //查询tableName，通过jobID查询当前操作的tableName
        String tableName = scheduleService.queryTableNameByJobId(jobId);
        //通过JobId查询当前job最近一次成功请求到的分页状态记录值
        List<Integer> params = dbLogInfoService.queryLatestPageParamsByJobID(jobId);
        int pageNum = params.get(0);
        int pageSize = params.get(1);
        String SQL = SQLCreator.createSQLByTbNameAndRowParam(tableName, pageNum, pageSize);

        //创建runnable任务

        //启动该任务


        return false;
    }


}
