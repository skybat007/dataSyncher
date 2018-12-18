package com.cetc.cloud.test;

import com.alibaba.fastjson.JSONObject;
import com.cetc.cloud.datasynch.api.model.Token;
import com.cetc.cloud.datasynch.provider.core.util.HttpClientUtil2;
import lombok.extern.slf4j.Slf4j;

/**
 * PackageName:   com.cetc.cloud.test
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/11/21 11:27
 * Updater:     by luolinjie
 * Update_Date: 2018/11/21
 * Update_Description: luolinjie 补充
 **/
@Slf4j
public class HttpClientUtil2Test {
    public void testDoGet() throws Exception {
        for(int i=0;i<1000;i++) {
            String url = "http://10.190.62.57/geostar/440304_DPS0515/dps";
            String params = "SERVICE=DataPivotService&VERSION=1.0.0&REQUEST=Query&DATASETID=5d813d4a-4dd6-4254-bf2f-5bd1d72c111f&MAXCOUNT=5&STARTPOSITION="+i+"&ROWFILTER=order by CREATETIME asc";
            JSONObject httpParams = HttpClientUtil2.getHttpParams(params);
            JSONObject jsonObject = HttpClientUtil2.doGet(url, httpParams);
            System.out.println(">>"+i+"-"+jsonObject.toJSONString());
        }
    }
    public static void testDoGetWithAuthoration() throws Exception {
        for(int i=0;i<100;i++) {
            String url = "http://10.190.55.62:8080/zz-hqcsjclbxx/v1/gwapi";
            String params = "Method=apiGetCheckinfoList&AccessToken=emzwda78ac0d62d6a34d519cfc8f10d3d2cad9&pageSize=1000&page="+i;
            JSONObject httpParams = HttpClientUtil2.getHttpParams(params);
            Token token = new Token();
            token.setKey("Authorization");
            token.setValue("Bearer 2335e013-2473-3be3-818f-3ee647f1b006");
            JSONObject jsonObject = HttpClientUtil2.doGetWithAuthoration(url, httpParams, token);
            String data = jsonObject.getString("data");
            System.out.println("apiGetCheckinfoList"+i);
        }
    }
    public static void testDoGetWithAuthoration2() throws Exception {
        for(int i=0;i<50;i++) {
            String url = "http://10.190.55.62:8080/zz-hqcsjcjglbxx/v1/gwapi";
            String params = "Method=apiGetCheckResultList&AccessToken=emzwda78ac0d62d6a34d519cfc8f10d3d2cad9&pageSize=10000&page=" + i;
            JSONObject httpParams = HttpClientUtil2.getHttpParams(params);
            Token token = new Token();
            token.setKey("Authorization");
            token.setValue("Bearer 2335e013-2473-3be3-818f-3ee647f1b006");
            JSONObject jsonObject = HttpClientUtil2.doGetWithAuthoration(url, httpParams, token);
            String data = jsonObject.getString("data");
            System.out.println("apiGetCheckResultList"+i);
        }
    }


    public void testGet() throws Exception {

    }

    public void testPut() throws Exception {

    }

    public void testPost() throws Exception {

    }

    public void testGet1() throws Exception {

    }

    public void testDelete() throws Exception {

    }
    static int successCount= 0;
    static int failCount= 0;
    public static class myRunnable1 extends Thread{
        @Override
        public void run() {
            try {
                testDoGetWithAuthoration();
                System.out.println("myRunnable1");
//                int counter = 10000;
//                while (--counter>0){
//                    System.out.println("myRunnable1");
//                }
                successCount++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class myRunnable2 extends Thread{
        @Override
        public void run() {
            try {
                testDoGetWithAuthoration2();
                System.out.println("myRunnable2");
//                int counter = 10000;
//                while (--counter>0){
//                    System.out.println("myRunnable2");
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        myRunnable1 myRunnable1 = new myRunnable1();
        myRunnable2 myRunnable2 = new myRunnable2();
        Thread thread1 = new Thread(myRunnable1);
        thread1.setName("GetList");
        Thread thread2 = new Thread(myRunnable2);
        thread2.setName("GetResult");
        System.out.println("调用开始！！！");
        thread1.start();
        thread2.start();
        System.out.println("调用结束！！！");
    }
}