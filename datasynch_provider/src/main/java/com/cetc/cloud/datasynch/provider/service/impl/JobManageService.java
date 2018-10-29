package com.cetc.cloud.datasynch.provider.service.impl;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.provider.template.MyScheduleRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

/**
 * Description：定时任务管理器
 * Created by luolinjie on 2018/10/9.
 */
@Service("jobManageService")
public class JobManageService implements com.cetc.cloud.datasynch.provider.service.JobManageService {

    Logger logger = LoggerFactory.getLogger(JobManageService.class);
    @Autowired
    DbOperateService dbOperateService;
    @Autowired
    SynchJobLogInfoService synchJobLogInfoService;
    @Autowired
    DbQueryService dbQueryService;
    @Autowired
    HttpOperateService httpOperateService;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private Map<String, Future> futures = new ConcurrentHashMap<String, Future>();

    @Override
    public Map<String, Future> getRunningFutures() {
        return futures;
    }

    static boolean isinitialized = false;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(200);
        threadPoolTaskScheduler.setThreadNamePrefix("DataSyncher--");
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        /**需要实例化线程*/
        threadPoolTaskScheduler.initialize();
        isinitialized = true;
        return threadPoolTaskScheduler;
    }

    @Override
    public int startJob(int jobID, ScheduleModel scheduleModel) {
        //创建定时任务
        MyScheduleRunnable runnableInstance = new MyScheduleRunnable(scheduleModel, synchJobLogInfoService, dbQueryService, dbOperateService, httpOperateService);
        String cron = scheduleModel.getCronExpression();
        if (null == cron) {
            cron = "0 0/1 * * * ?";//默认是每天凌晨0点更新
        }
        try {
            //创建定时任务并启动
            ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(runnableInstance, new CronTrigger(cron));
//            ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(runnableInstance, new Date());
            /**将定时任务记录在内存中，供其他功能查询*/
            futures.put(String.valueOf(jobID), future);
            logger.info("job:" + jobID + "--started!");
            return jobID;
        } catch (Exception e) {
            logger.info("job:" + jobID + "--started error!");
            return -1;
        }
    }

    @Override
    /**
     * stop:1:正常停止
     *      0：还在运行
     *      -1：异常
     */
    public int removeJob(int jobID) {
        if (threadPoolTaskScheduler != null) {
            ScheduledFuture<?> future = (ScheduledFuture<?>) futures.get(jobID);
            if (!future.isDone()) {
                return 0;
            } else {
                boolean cancel = future.cancel(true);
                if (cancel) {
                    logger.info("job:" + jobID + "--stopped!");
                    futures.remove(jobID);
                    return 1;
                } else {
                    logger.info("job:" + jobID + "--stopping job occurs error!");
                    return -1;
                }
            }
        }
        return -1;
    }

    //   cron表达式： "\*"/"10 * * * * *
    @Override
    public String changeJob(int jobID, String cron, Runnable runnableInstance) {
        removeJob(jobID);// 先停止，再开启
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(runnableInstance, new CronTrigger(cron));
        System.out.println("DynamicTask.startCron10()");
        return "changeCron10";
    }

}
