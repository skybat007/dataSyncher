package com.cetc.cloud.datasynch.provider.service.impl;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Administrator on 2019/1/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DbQueryThirdServiceTest extends TestCase {
    @Autowired
    DbQueryService dbQueryService;
    @Autowired
    DbQueryThirdService dbQueryThirdService;

    @Test
    public void testCheckIfTableExists_readOnly() throws Exception {
        boolean temp1 = dbQueryThirdService.checkIfTableExists_readOnly("TEMP1");
        System.out.println("datasource:Third--"+temp1);
        boolean temp11 = dbQueryService.checkIfTableExists_readOnly("TEMP1");
        System.out.println("datasource:ReadOnly--"+temp11);
    }
}