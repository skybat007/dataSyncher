package com.cetc.cloud.test;

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
    public static void main(String[] args){
//        String s = "a.b";
        String s = "Authorization:Bearer 23c29412-2f28-3248-9a95-82957ae69b9e";

//        String[] split = s.split("\\.");
        String[] split = null;
        if (s.contains(":")) {
            split = s.split(":");
        }

        System.out.println("split.length:"+split.length);
        System.out.println("split[0]:"+split[0]);
        System.out.println("split[1]:"+split[1]);
    }
}
