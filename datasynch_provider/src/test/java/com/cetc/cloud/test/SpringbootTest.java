package com.cetc.cloud.test;

import com.cetc.cloud.datasynch.provider.service.impl.DbOperateService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
    DbOperateService dbOperateService;

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @Test
    public void testApplicationMethod(){
//        boolean exists = dbOperateService.checkIfSequenceExists("123");
//        System.out.println(exists);
//
//        boolean sequence = dbOperateService.createSequence("SEQ_1234");
//        System.out.println("create sequence SEQ_1234:"+sequence);
        boolean exists = dbOperateService.checkIfColumnExists("WEEKLY_WEIWEN", "ID");
        System.out.println(exists);
    }
    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }

}
