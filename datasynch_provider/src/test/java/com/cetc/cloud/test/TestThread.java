package com.cetc.cloud.test;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ScheduledFuture;

/**
 * PackageName:   com.cetc.cloud.test
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/10/30 15:10
 * Updater:     by luolinjie
 * Update_Date: 2018/10/30
 * Update_Description: luolinjie 补充
 **/
public class TestThread {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(200);
        threadPoolTaskScheduler.setThreadNamePrefix("DataSyncher--");
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        /**需要实例化线程*/
        threadPoolTaskScheduler.initialize();
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(new InnerRunnable(), new CronTrigger("0 0/1 * * * ?"));
//        Thread t = new Thread(new InnerRunnable());
//        t.start();
        ThreadGroup group = Thread.currentThread().getThreadGroup();

        Thread.sleep(1000*65);
        // 一：通过线程组遍历获得线程
        Thread s = findThreadByName("DataSyncher--TestThread_1");
        System.out.println("   find thread: " + s);
        System.out.println("current thread: " + Thread.currentThread());


//        // 二：通过 JMX 可以通过线程 ID 获得线程信息
//        ThreadMXBean tmx = ManagementFactory.getThreadMXBean();
//        ThreadInfo info = tmx.getThreadInfo(s);
//        System.out.println(info.getThreadState());


        s.stop();
    }


    public static Thread findThreadByName(String threadName) {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        while (group != null) {
            Thread[] threads = new Thread[(int) (group.activeCount() * 1.2)];
            int count = group.enumerate(threads, true);
            for (int i = 0; i < count; i++) {
                if (threadName.equals(threads[i].getName())) {
                    return threads[i];
                }
            }
            group = group.getParent();
        }
        return null;
    }

    private static class InnerRunnable implements Runnable {

        private int i = 0;

        public void run() {
            Thread.currentThread().setName("TestThread_1");
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println(i++);
                    Thread.sleep(1000 * 5);
                }
            } catch (InterruptedException e) {
                System.out.println("mythread is interrupted!");
            }
        }
    }
}
