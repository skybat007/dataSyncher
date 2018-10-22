package com.cetc.cloud.datasynch.provider.controller;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.api.service.ScheduleRemoteService;
import com.cetc.cloud.datasynch.provider.service.DbOperateService;
import com.cetc.cloud.datasynch.provider.service.JobManageService;
import com.cetc.cloud.datasynch.provider.service.ScheduleService;
import com.cetc.cloud.datasynch.provider.service.SynchJobLogInfoService;
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
    SynchJobLogInfoService synchJobLogInfoService;


    @Override
    public List<ScheduleModel> queryScheduleJobList() {
        List<ScheduleModel> list = scheduleService.queryScheduleJobList();
        return list;
    }


    @Override
    public HashMap createScheduleJob(int connType, String source, String token, String jsonExtractRule, int pageSize, String tableName, String cronExpression) throws SQLException {

        HashMap res = new HashMap();

        ScheduleModel scheduleModel = new ScheduleModel();
        scheduleModel.setConnType(connType);
        scheduleModel.setSource(source);
        scheduleModel.setPageSize(pageSize);
        scheduleModel.setTargetTableName(tableName);
        scheduleModel.setCronExpression(cronExpression);

        //参数完整性判断
        if (null == source || null == tableName || null == cronExpression) {
            res.put("result", "fail");
            res.put("msg", "param error! please check your params!");
            return res;
        }
        //接口类型任务，需要额外添加token属性和json解析规则
        if (connType == CommonInstance.TYPE_INTERFACE && null != token && null != jsonExtractRule) {
            scheduleModel.setToken(token);
            scheduleModel.setJsonExtractRule(jsonExtractRule);
        }

        //将创建的任务记录在数据库schedule表中
        int jobId = scheduleService.addScheduleInstance(scheduleModel);

        if (jobId == -1) {
            res.put("result", "fail");
            res.put("msg", "failed,error when creating schedule job");
            return res;
        }
        res.put("result", "success");
        res.put("msg", "create job:" + jobId + " success!");

        return res;
    }


    @Override
    public HashMap<String, String> startScheduleJobByJobId(int jobId) {
        HashMap res = new HashMap();
        ScheduleModel scheduleModel = scheduleService.queryModelByJobId(jobId);
        //启动任务
        int jobid = jobManageService.startJob(jobId, scheduleModel);

        if (jobid == jobId) {
            res.put("result", "success");
            res.put("msg", "create job:" + jobid + " success!");
        }
        return res;
    }

    @Override
    public HashMap<String, String> stopScheduleJobByJobId(int jobId) {
        HashMap res = new HashMap();
        //停止当前任务
        boolean s = jobManageService.stopJob(jobId);

        if (s) {
            //更新当前任务状态
            int updateRes = scheduleService.disableStatusByJobId(jobId);
            if (updateRes>0) {
                res.put("result", "success");
                res.put("msg", "stopping job:" + jobId + " success!");
            }else {
                res.put("result", "fail");
                res.put("msg", "disableStatusByJobId job:" + jobId + " failed!");
            }
        } else {
            res.put("result", "fail");
            res.put("msg", "stopScheduleJobByJobId job:" + jobId + " failed!");
        }
        return res;
    }


    @Override
    public HashMap<String, String> deleteScheduleJobByJobId(int jobId) {

        HashMap result = new HashMap<String, String>();
        //停止当前任务
        boolean s = jobManageService.stopJob(jobId);
        if (s) {
            //从列表中删除当前定时任务
            int res = scheduleService.deleteScheduleByJobId(jobId);
            if (res == 1) {
                result.put("result", "success");
                result.put("msg", "deleteScheduleJobByJobId success,job:" + jobId);
                return result;
            } else {
                result.put("result", "fail");
                result.put("mag", "deleteScheduleJobByJobId fail job:" + jobId);
                return result;
            }
        } else {
            result.put("result", "fail");
            result.put("msg", "deleteScheduleJobByJobId job:" + jobId + ",job cannot be canceled!");
            return result;
        }
    }


    @Override
    public HashMap<String, String> alterScheduleJobCron(int jobId, String cron) {

        HashMap result = new HashMap<String, String>();
        //停止当前任务
        boolean s = jobManageService.stopJob(jobId);

        //更新cron表达式至数据库
        int res = scheduleService.updateCronByJobId(jobId, cron);

        //重新启动当前任务--通过JobID（前提是必须之前有这个Job）
        HashMap<String, String> restartRes = startScheduleJobByJobId(jobId);
        if ("success".equals(restartRes.get("result"))) {
            //更改当前任务状态为Disabled
            scheduleService.disableStatusByJobId(jobId);
            result.put("result" + jobId, "success");
            result.put("msg", "alterScheduleJobCron:" + jobId + ", success!");
            return result;
        } else {
            result.put("result", "fail");
            result.put("msg", "alterScheduleJobCron:" + jobId + ", failed!");
            return result;
        }
    }


}
