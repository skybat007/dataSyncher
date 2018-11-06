package com.cetc.cloud.datasynch.provider.controller;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.api.service.ScheduleRemoteService;
import com.cetc.cloud.datasynch.provider.core.util.Ping;
import com.cetc.cloud.datasynch.provider.service.impl.*;
import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import org.apache.el.lang.ELArithmetic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

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

    @Autowired
    DbQueryService dbQueryService;

    @Override
    public List<ScheduleModel> queryScheduleJobList() {
        List<ScheduleModel> list = scheduleService.queryScheduleJobList();
        return list;
    }


    @Override
    public HashMap createScheduleJob(int connType, String source, int isPagingQuery,
                                     String orderByColumnName,
                                     String httpParamExpression, String httpToken, String httpParamPageSize,
                                     String httpParamPageNum, String httpJsonExtractRule, String httpTotalExtractRule,
                                     String targetTableName, String pageSize, String cronExpression) throws SQLException {
        HashMap res = new HashMap();

        //共有参数完整性校验
        if (null == source || null == targetTableName || null == cronExpression || null == pageSize) {
            res.put("result", "fail");
            res.put("msg", "param error! source,targetTableName,cronExpression,pageSize cannot be null!");
            return res;
        }
        //独有参数完整性校验
        if (connType == CommonInstance.TYPE_DB) {
            if (isPagingQuery == CommonInstance.DO_PAGING) {
                if (null == orderByColumnName) {//做分页时一定要有排序字段
                    res.put("result", "fail");
                    res.put("msg", "param error! orderByColumnName cannot be null!");
                    return res;
                }
            } else if (isPagingQuery == CommonInstance.NO_PAGING) {//如果不做分页，则需要指定rownum为排序字段
                if (null == orderByColumnName || !"rownum".equalsIgnoreCase(orderByColumnName)) {
                    res.put("result", "fail");
                    res.put("msg", "param error! orderByColumnName cannot be null,you can figure it by input:\"rownum\"");
                    return res;
                }
            } else {
                res.put("result", "fail");
                res.put("msg", "param error! isPagingQuery can not be otherValue, available value is:0 or 1");
                return res;
            }
        } else if (connType == CommonInstance.TYPE_INTERFACE) {
            if (isPagingQuery == CommonInstance.DO_PAGING) {
                if (null == httpParamExpression || null == httpJsonExtractRule) {
                    res.put("result", "fail");
                    res.put("msg", "param error! httpParamExpression,httpParamPageSize,httpParamPageNum,httpJsonExtractRule cannot be null!");
                    return res;
                }
            } else if (isPagingQuery == CommonInstance.NO_PAGING) {
                if (!"[*]".equals(httpJsonExtractRule)) {
                    res.put("result", "fail");
                    res.put("msg", "param error! httpJsonExtractRule cannot be null!,default: \"[*]\"");
                    return res;
                }
            } else {
                res.put("result", "fail");
                res.put("msg", "param error! isPagingQuery cannot be otherValue, available value is:0 or 1");
                return res;
            }
        }
        // 验证参数合法性
        if (CommonInstance.TYPE_DB == connType) {
            if (false == dbQueryService.checkIfTableExists(source)) {
                res.put("result", "fail");
                res.put("msg", "failed,source Table:" + source + " doesn't exists!");
                return res;
            } else if (false == dbOperateService.checkIfTableExists(targetTableName)) {
                res.put("result", "fail");
                res.put("msg", "failed,Target table:" + targetTableName + " doesn't Exists!");
                return res;
            }
        } else if (CommonInstance.TYPE_INTERFACE == connType) {
            if (false == dbOperateService.checkIfTableExists(targetTableName)) {
                res.put("result", "fail");
                res.put("msg", "failed,Target table:" + targetTableName + " doesn't Exists!");
                return res;
            }
        }

        ScheduleModel scheduleModel = new ScheduleModel();
        scheduleModel.setConnType(connType);
        scheduleModel.setSource(source);
        scheduleModel.setIsPagingQuery(isPagingQuery);
        scheduleModel.setOrderByColumnName(orderByColumnName);
        scheduleModel.setHttpParamExpression(httpParamExpression);
        scheduleModel.setHttpToken(httpToken);
        scheduleModel.setHttpParamPageSize(httpParamPageSize);
        scheduleModel.setHttpParamPageNum(httpParamPageNum);
        scheduleModel.setHttpJsonExtractRule(httpJsonExtractRule);
        scheduleModel.setHttpTotalExtractRule(httpTotalExtractRule);
        scheduleModel.setTargetTableName(targetTableName);
        scheduleModel.setPageSize(Integer.parseInt(pageSize));
        scheduleModel.setCronExpression(cronExpression);

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
        if (-1 != jobid) {
            //修改状态
            int i = scheduleService.enableStatusByJobId(jobId);
            if (jobid == jobId && i > 0) {
                res.put("result", "success");
                res.put("msg", "start job:" + jobid + " success!");
            }
        } else {
            res.put("result", "fail");
            res.put("msg", "start job:" + jobid + " failed!");
        }
        return res;
    }

    @Override
    public HashMap<String, String> stopScheduleJobByJobId(int jobId) {
        HashMap res = new HashMap();
        //停止当前任务
        int status = jobManageService.removeJob(jobId);

        if (status == 1) {
            //更新当前任务状态
            int updateRes = scheduleService.disableStatusByJobId(jobId);
            if (updateRes > 0) {
                res.put("result", "success");
                res.put("msg", "stopping job:" + jobId + " success!");
            } else {
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
        int s = jobManageService.removeJob(jobId);
        if (s == 1) {
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
        int s = jobManageService.removeJob(jobId);
        if (s == 1) {
            //更新cron表达式至数据库
            int res = scheduleService.updateCronByJobId(jobId, cron);
        }
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

    @Override
    public Map<String, Future> getRunningFutures() {
        return jobManageService.getRunningFutures();
    }

}
