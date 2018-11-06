package com.cetc.cloud.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

/**
 * PackageName:   com.cetc.cloud.test
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/10/28 13:49
 * Updater:     by luolinjie
 * Update_Date: 2018/10/28
 * Update_Description: luolinjie 补充
 **/
public class TestSchedule {

    public void testSchedule() throws InterruptedException {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(200);
        threadPoolTaskScheduler.setThreadNamePrefix("DataSyncher--");
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(false);
        /**需要实例化线程*/
        threadPoolTaskScheduler.initialize();
        String cron =null;
        if (cron == null) {
            cron = "0/5 * * * * *";
        }
        Runnable runnable = new Runnable() {
            private int count = 2;
            public void run() {
                while (count-->0) {
                    String time = new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date());
                    System.out.println("Test GETaskScheduler Success at " + time);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        /*只调用一次*/
        ScheduledFuture<?> schedule = threadPoolTaskScheduler.schedule(runnable, new Date());
        ScheduledFuture<?> schedule2 = threadPoolTaskScheduler.schedule(runnable, new Date());
        Thread.sleep(6000);
        threadPoolTaskScheduler.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        TestSchedule testSchedule = new TestSchedule();
        testSchedule.testSchedule();
    }
}
