package com.cetc.cloud.test;

import com.cetc.cloud.datasynch.provider.core.util.JsonExtractor;
import org.junit.Test;

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
    @Test
    public void testSplitString() {
//        String s = "*.e.f.g";
//        String[] split =  s.split("\\.");
//
//        List<String> list = Arrays.asList(split);
////
//        System.out.println("split.length:" + split.length);
//
//        List<String> list1 = list.subList(1, list.size());
//        System.out.println(" list1.size():" +  list1.size());
////
//        for(int i=0;i<list1.size();i++){
//            System.out.println("---");
//            System.out.println(list1.get(i));
//        }
////        System.out.println("split :" + split.toString());
////        System.out.println("split[1]:"+split[1]);

//        List<String> subRule = JsonExtractor.getSubRule(".rule1.rule2");
//        List<String> subRule1 = JsonExtractor.getSubRule("rule1.rule2.");
//        System.out.println(subRule);
//        System.out.println(subRule1);
        String httpParamExpression = "customer_code=ftqw";
        String[] paramKeyValues = httpParamExpression.split("&");
        System.out.println(paramKeyValues.length);
        System.out.println(paramKeyValues[0]);
//        String[] split = paramKeyValues[0].split("=");
    }
}
