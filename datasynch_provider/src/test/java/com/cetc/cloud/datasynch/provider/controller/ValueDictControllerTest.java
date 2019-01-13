package com.cetc.cloud.datasynch.provider.controller;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.controller
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/12/5 17:46
 * Updater:     by luolinjie
 * Update_Date: 2018/12/5
 * Update_Description: luolinjie 补充
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ValueDictControllerTest extends TestCase {

    @Autowired
    ValueDictController controller;

    @Test
    public void testImportExcel() throws Exception {
    }
    @Test
    public void testGetDictValue() throws Exception {
        String tableName = "blk_population";
        String columnName = "mz";
        String code = "10";
        String dictValue = controller.getDictValue(tableName, columnName, code);
        System.out.println(dictValue);
        String tableName1 = "blk_population";
        String columnName1 = "mz";
        String code1 = "100";
        String dictValue1 = controller.getDictValue(tableName1, columnName1, code1);
        System.out.println(dictValue1);
    }
}