package com.cetc.datasynch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

/**
 * Description：定时任务管理器
 * Created by luolinjie on 2018/10/9.
 */
@Service
public class JobManageService {

    Logger logger = LoggerFactory.getLogger(JobManageService.class);

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> future;
    private Map<String, Future> futures = new HashMap<String, Future>();

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    /**
     * 根据传入的jobId和run方法的执行体创建内容
     */
    public int startJob(int jobID,String cron, Runnable runnableInstance) {
        if (null==cron){
            cron= "0 0 0 * * ?";//默认是每天凌晨0点更新
        }
        //创建定时任务并启动
        future = threadPoolTaskScheduler.schedule(runnableInstance, new CronTrigger(cron));
        //将定时任务记录在全局变量中，供其他功能查询
        futures.put(String.valueOf(jobID), future);
        logger.info("job:"+jobID+"--started!");
        return jobID;
    }

    /**
     * 通过jobID取消定时任务
     * @param jobID
     */
    public String stopJob(int jobID) {

        if (future != null) {
            Future future = futures.get(jobID);
            future.cancel(true);
        }
        logger.info("job:" + jobID + "--stopped!");
        return "stopCron";
    }

    /**
     * 通过jobID修改定时规则并重新启动（需要重新传入运行实体）
     *  @param  jobID -- 任务标识符
     *  @param  cron -- cron表达式
     *  @param  runnableInstance -- 带有run方法的执行实体
     */
//   cron表达式： "\*"/"10 * * * * *
    public String changeJob(String jobID, String cron,Runnable runnableInstance) {
        stopJob(jobID);// 先停止，再开启
        future = threadPoolTaskScheduler.schedule(runnableInstance, new CronTrigger(cron));
        System.out.println("DynamicTask.startCron10()");
        return "changeCron10";
    }


    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("DynamicTask.MyRunnable.run()，" + new Date());
        }
    }
}
