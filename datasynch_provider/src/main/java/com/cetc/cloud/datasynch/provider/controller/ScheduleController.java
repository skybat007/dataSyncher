package com.cetc.cloud.datasynch.provider.controller;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.api.service.ScheduleRemoteService;
import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import com.cetc.cloud.datasynch.provider.config.MyApplicationRunner;
import com.cetc.cloud.datasynch.provider.core.JobManageService;
import com.cetc.cloud.datasynch.provider.mapper.XinfangEventMapper;
import com.cetc.cloud.datasynch.provider.service.impl.*;
import com.cetc.cloud.datasynch.provider.template.*;
import com.cetc.cloud.datasynch.provider.util.Date2CronUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Future;

/**
 * Description：数据库（前置机）定时任务管理
 * Created by luolinjie on 2018/10/9.
 */
@RestController
@Slf4j
public class ScheduleController implements ScheduleRemoteService {
    @Autowired
    DbOperateService dbOperateService;
    @Autowired
    DbThirdOperateService dbOperateService_zhft;
    @Autowired
    JobManageService jobManageService;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    SynchJobLogInfoService synchJobLogInfoService;
    @Autowired
    DbQueryService dbQueryService;
    @Autowired
    HttpOperateService httpOperateService;
    @Autowired
    OuterUrlsService outerUrlsService;
    @Autowired
    RePullTableController rePullTableController;
    @Autowired
    XinfangEventMapper xinfangEventMapper;

    @Autowired
    MyApplicationRunner runner;

    @Override
    public List<ScheduleModel> queryScheduleJobList() {
        List<ScheduleModel> list = scheduleService.queryScheduleJobList();
        return list;
    }


    @Override
    public HashMap createScheduleJob(int connType, String source, String srcDs, int isPagingQuery,
                                     String orderByColumnName,
                                     String httpParamExpression, String httpToken, String httpPagingType, String httpParamPageSize,
                                     String httpParamPageNum, String httpJsonExtractRule,
                                     String targetTableName, int needsTruncateTargetTb, String pageSize, String cronExpression) throws SQLException {
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
                if (null == httpJsonExtractRule || "".equals(httpJsonExtractRule)) {
                    res.put("result", "fail");
                    res.put("msg", "param error! httpJsonExtractRule cannot be null!");
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
            if (false == dbQueryService.checkIfTableExists_readOnly(source)) {
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

        //检查shceduleModel是否存在，如果已经存在，则提示需要先删除后再创建
        ScheduleModel model = scheduleService.queryModelByTableName(targetTableName);
        if (null!=model){
            res.put("result","failed");
            res.put("msg","job for "+targetTableName+" already exists! please delete job first and create again");
            return res;
        }

        try {
            CronTrigger trigger = new CronTrigger(cronExpression);
        } catch (Exception e) {
            log.error("cron trigger syntax error! please check and try again!");
            res.put("result", "failed");
            res.put("msg", "cron trigger syntax error! please check and try again!");
            return res;
        }

        ScheduleModel scheduleModel = new ScheduleModel();
        scheduleModel.setConnType(connType);
        scheduleModel.setSource(source);
        try {
            scheduleModel.setSrcDs(Integer.valueOf(srcDs));
        }catch (Exception e){
            log.error("cannot parse srcDs from String to Integer:" + srcDs + ",will set a default value:0 on it");
            scheduleModel.setSrcDs(0);
        }
        scheduleModel.setIsPagingQuery(isPagingQuery);
        scheduleModel.setOrderByColumnName(orderByColumnName);
        scheduleModel.setHttpParamExpression(httpParamExpression);
        scheduleModel.setHttpToken(httpToken);
        scheduleModel.setHttpPagingType(httpPagingType);
        scheduleModel.setHttpParamPageSize(httpParamPageSize);
        scheduleModel.setHttpParamPageNum(httpParamPageNum);
        scheduleModel.setHttpJsonExtractRule(httpJsonExtractRule);
        scheduleModel.setTargetTableName(targetTableName);
        scheduleModel.setNeedsTruncateTargetTb(needsTruncateTargetTb);
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
        int jobid = jobManageService.startScheduledJob(scheduleModel);
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
    public HashMap<String, String> triggerOnceJobByTargetTableName(String tableName) {
        HashMap res = new HashMap();
        ScheduleModel scheduleModel = scheduleService.queryModelByTableName(tableName);
        if (null==scheduleModel){
            res.put("msg","error! job doesn't exists!");
            return res;
        }
        scheduleModel.setIsEnabled(CommonInstance.JOB_ENABLED);
        //启动任务
        int jobid = jobManageService.startOnceJob(scheduleModel);
        if (-1 != jobid) {
            //修改状态
            if (jobid == scheduleModel.getId()) {
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
    public HashMap<String, String> startOuterScheduleJob(String jobName, CronTrigger trigger) {
        HashMap res = new HashMap();
        String uuid = null;
        if (CommonInstance.JOB_calc_trouble_sanxiao.equals(jobName)) {
            SanxiaoCalcRunnable myCalculateRunnable = new SanxiaoCalcRunnable(dbQueryService, dbOperateService, httpOperateService, rePullTableController);
            if (trigger != null) {
                uuid = jobManageService.startOuterScheduledJob(jobName, myCalculateRunnable, trigger);
            }
        }
        if (CommonInstance.JOB_refresh_sanxiao_list.equals(jobName)) {

            RefreshSanxiaoListRunnable myCalculateRunnable = new RefreshSanxiaoListRunnable(dbOperateService_zhft, dbOperateService);

            if (trigger != null) {
                uuid = jobManageService.startOuterScheduledJob(jobName, myCalculateRunnable, trigger);
            }
        }
        if (CommonInstance.JOB_get_today_xinfang.equals(jobName)) {

            XinfangGetRunnable myCalculateRunnable = new XinfangGetRunnable(outerUrlsService, xinfangEventMapper);
            if (trigger != null) {
                uuid = jobManageService.startOuterScheduledJob(jobName, myCalculateRunnable, trigger);
            }
        }
        if (CommonInstance.JOB_add_chengguanevent_attach.equals(jobName)) {

            ChengguanEventAttachRunnable myCalculateRunnable = new ChengguanEventAttachRunnable(dbOperateService, httpOperateService, outerUrlsService);
            if (trigger != null) {
                uuid = jobManageService.startOuterScheduledJob(jobName, myCalculateRunnable, trigger);
            }
        }
        if (CommonInstance.JOB_get_weather_alarm_info.equals(jobName)) {
            WeatherAlarmRunnable myCalculateRunnable = new WeatherAlarmRunnable(dbOperateService, outerUrlsService);
            if (trigger != null) {
                uuid = jobManageService.startOuterScheduledJob(jobName, myCalculateRunnable, trigger);
            }
        }
        if (CommonInstance.JOB_generate_water_AQI_info.equals(jobName)) {
            WaterAQIRunnable myCalculateRunnable = new WaterAQIRunnable(dbOperateService);
            if (trigger != null) {
                uuid = jobManageService.startOuterScheduledJob(jobName, myCalculateRunnable, trigger);
            }
        }
        if (CommonInstance.JOB_generate_water_AQI_info.equals(jobName)) {
            WaterAQIRunnable myCalculateRunnable = new WaterAQIRunnable(dbOperateService);
            if (trigger != null) {
                uuid = jobManageService.startOuterScheduledJob(jobName, myCalculateRunnable, trigger);
            }
        }

        log.info("\n\n>>>>\n\n  >>>> scheduling job:" + jobName + " started! --- cronExpression：" + trigger.getExpression());
        if (uuid != null) {
            res.put("result", "success");
            res.put("msg", "start Outer job:" + jobName + " success! job ID:" + uuid);
        } else {
            res.put("result", "failed");
            res.put("msg", "start Outer job:" + jobName + " faild!");
        }

        return res;
    }

    @Override
    public HashMap<String, String> triggerOnceOuterScheduleJobByJobName(String jobName) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 5);
        String cron = Date2CronUtil.getCron(instance.getTime());
        CronTrigger trigger = new CronTrigger(cron);
        HashMap<String, String> map = startOuterScheduleJob(jobName, trigger);
        return map;
    }

    @Override
    public HashMap<String, String> startScheduleJobArrayByJobId(String jobs) {
        HashMap res = new HashMap();
        if (null == jobs && "".equals(jobs)) {
            res.put("res", "fail");
            res.put("msg", "param: jobs cannot be null!");

        }
        String[] split = jobs.split(",");
        List<String> jobList = Arrays.asList(split);
        if (jobList.size() >= 1) {
            for (int i = 0; i < jobList.size(); i++) {
                startScheduleJobByJobId(Integer.parseInt(jobList.get(i)));
            }
            res.put("res", "success");
            res.put("msg", "jobs " + jobs + " started!");
            return res;
        } else {
            res.put("res", "fail");
            res.put("msg", "param: jobs cannot be null!");
            return res;
        }

    }

    @Override
    public HashMap<String, String> startAllDSJobs() {
        HashMap res = new HashMap();
        runner.startAllJobs();
        res.put("res", "started");
        return res;
    }

    @Override
    public HashMap<String, String> startAllEnabledScheduleJobs() {
        HashMap res = new HashMap();
        List<Integer> list = scheduleService.queryEnabledJobIdList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                startScheduleJobByJobId(list.get(i));
            }
            res.put("res", "success");
            res.put("msg", "start all jobs successful!" + list.toString());
        } else {
            res.put("res", "fail");
            res.put("msg", "jobIds is null!");
        }
        return res;
    }

    @Override
    public HashMap<String, String> disableJobStatusByJobId(int jobId) {
        HashMap res = new HashMap();
        //更新当前任务状态
        int updateRes = scheduleService.alterJobStatusByJobId(jobId, CommonInstance.JOB_DISABLED);
        if (updateRes > 0) {
            res.put("result", "success");
            res.put("msg", "disable JobStatus By JobId:" + jobId + " success!");
        } else {
            res.put("result", "fail");
            res.put("msg", "disable JobStatus By JobId:" + jobId + " failed!");
        }
        return res;
    }

    @Override
    public HashMap<String, String> enableJobStatusByJobId(int jobId) {
        HashMap res = new HashMap();
        //更新当前任务状态
        int updateRes = scheduleService.alterJobStatusByJobId(jobId, CommonInstance.JOB_ENABLED);
        if (updateRes > 0) {
            res.put("result", "success");
            res.put("msg", "enable JobStatus By JobId:" + jobId + " success!");
        } else {
            res.put("result", "fail");
            res.put("msg", "enable JobStatus By JobId :" + jobId + " failed!");
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
            scheduleService.alterJobStatusByJobId(jobId, CommonInstance.JOB_DISABLED);
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
