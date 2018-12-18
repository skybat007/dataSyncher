package com.cetc.cloud.test;

/**
 * PackageName:   com.cetc.cloud.xiaoanETL.service
 * projectName:   xiaoanETL
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/12/16 10:36
 * Updater:     by luolinjie
 * Update_Date: 2018/12/16
 * Update_Description: luolinjie 补充
 **/
public class Son implements Father {
    @Override
    public void run(){
        System.out.println("Son run");
    }



    public static void main(String[] args){
        Father father = new Son();
        father.run();
    }
}
