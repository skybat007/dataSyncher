package com.cetc.cloud.test;

import org.junit.Test;

/**
 * PackageName:   com.cetc.cloud.test
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/11/11 11:31
 * Updater:     by luolinjie
 * Update_Date: 2018/11/11
 * Update_Description: luolinjie 补充
 **/
public class TestRegex {
    @Test
    public void testRegex(){
        String jsonExtractRule = "data.data.*.data.attributes.attributes";

        boolean matches = jsonExtractRule.matches("^\\w+[\\.\\w+]*\\.\\*[\\.\\w+]*\\.\\w+$");
        System.out.println(matches);
    }
}
