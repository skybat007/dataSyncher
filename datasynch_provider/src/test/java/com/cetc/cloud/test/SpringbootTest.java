package com.cetc.cloud.test;

import com.cetc.cloud.datasynch.provider.controller.SingleJobController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.SQLException;

/**
 * PackageName:   com.cetc.cloud.test
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/11/5 17:59
 * Updater:     by luolinjie
 * Update_Date: 2018/11/5
 * Update_Description: luolinjie 补充
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class SpringbootTest {


    @Autowired
    SingleJobController singleJobController;


    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @Test
    public void testApplicationMethod() throws SQLException {
        singleJobController.truncateTable();
        System.out.println("\nFinished! calculateHasTroubleSanXiao \n");
    }

    @Test
    public void testApplicationMethod2() throws SQLException {
        singleJobController.calculateHasTroubleSanXiao();
        System.out.println("\nFinished! calculateHasTroubleSanXiao \n");
    }
    @Test
    public void testApplicationMethod3() throws SQLException {
        singleJobController.calculateRealSanXiaoCount();
        System.out.println("\nFinished! calculateRealSanXiaoCount \n");
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }

}
