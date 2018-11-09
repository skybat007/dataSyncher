package com.cetc.cloud.test;

import java.util.Arrays;
import java.util.List;

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
    public static void main(String[] args) {
        String s = "a.b.c.d.e.f.g";
//        String s = "Authorization:Bearer 23c29412-2f28-3248-9a95-82957ae69b9e";

//        String[] split = s.split("\\.");
        String[] split = null;
        split = s.split("\\.");

        List<String> list = Arrays.asList(split);

        System.out.println("split.length:" + split.length);

        List<String> list1 = list.subList(1, 7);

        for(int i=0;i<list1.size();i++){
            System.out.println("---");
            System.out.println(list1.get(i));
        }
//        System.out.println("split :" + split.toString());
//        System.out.println("split[1]:"+split[1]);
    }
}
