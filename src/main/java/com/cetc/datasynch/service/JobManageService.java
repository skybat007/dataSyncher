package com.cetc.datasynch.service;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Description：
 * Created by luolinjie on 2018/10/19.
 */
public interface JobManageService {
    @Bean
    ThreadPoolTaskScheduler threadPoolTaskScheduler();

    /**
     * 根据传入的jobId和run方法的执行体创建内容
     */
    int startJob(int jobID, String cron, Runnable runnableInstance);

    /**
     * 通过jobID取消定时任务
     * @param jobID
     */
    String stopJob(int jobID);

    /**
     * 通过jobID修改定时规则并重新启动（需要重新传入运行实体）
     * @param  jobID -- 任务标识符
     *  @param  cron -- cron表达式
     * @param  runnableInstance -- 带有run方法的执行实体
     */
//   cron表达式： "\*"/"10 * * * * *
    String changeJob(int jobID, String cron, Runnable runnableInstance);
}
