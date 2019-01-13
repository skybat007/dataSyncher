package com.cetc.cloud.test;

/**
 * PackageName:   com.cetc.cloud.test
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/10/28 0:57
 * Updater:     by luolinjie
 * Update_Date: 2018/10/28
 * Update_Description: luolinjie 补充
 **/
public class TestRunnable implements Runnable{

    @Override
    public void run() {
        int count = 0;
        while(true) {
            if (count==10){
                return;
            }
            System.out.println("TestRunnable.run()--started!"+count);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
    }
}
