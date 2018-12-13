package com.cetc.cloud.test;

import org.junit.Test;

/**
 * PackageName:   com.cetc.cloud.test
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/10/29 16:59
 * Updater:     by luolinjie
 * Update_Date: 2018/10/29
 * Update_Description: luolinjie 补充
 **/
public class testSplitString {
    @Test
    public void testSplitString() {


        String s = "http://10.190.55.62/wwgl-sjsu/v1/eventList.action";
        String[] split = s.split("//");
        String s1 = split[1].split("/")[0];
        if (s1.contains(":")) {
            String ip = s1.split(":")[0];
            String port = s1.split(":")[1];
            System.out.println("IP:" + ip);
            System.out.println("PORT:" + port);
        } else {
            String ip = s1;
            String port = "80";
            System.out.println("IP:" + ip);
            System.out.println("PORT:" + port);
        }

    }

    @Test
    public void testReplaceString() {
        String value = "2018-09-28 13:45:25.0";

        if (value.contains(".")) {
            value = value.substring(0,value.indexOf("."));
//            value = value.split("\\.")[0];
            System.out.println(value);
        }
//        if (value.contains(".")) {
//            value = value.replaceAll("\\.0", "");
//            System.out.println(value);
//        }
    }

}
