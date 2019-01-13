package com.cetc.cloud.test;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * PackageName:   com.cetc.cloud.test
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/12/10 9:37
 * Updater:     by luolinjie
 * Update_Date: 2018/12/10
 * Update_Description: luolinjie 补充
 **/
public class ThreadTest {

    @Test
    public void TestThread() throws InterruptedException {
        //通过多线程实现数据插入，避免阻塞队列
        ThreadPoolExecutor executor = new ThreadPoolExecutor(8,8,100, TimeUnit.MILLISECONDS,new ArrayBlockingQueue(8));
        executor.execute(new Thread(new ThreadA()));
        executor.execute(new Thread(new ThreadB()));
        Thread.sleep(100);
    }

    public class ThreadA implements Runnable{

        @Override
        public void run() {
            for (int i=0;i<100;i++) {
                System.out.println("ThreadA"+i);
            }
        }
    }
    public class ThreadB implements Runnable{

        @Override
        public void run() {
            for (int i=0;i<100;i++) {
                System.out.println("ThreadB"+i);
            }
        }
    }


}
