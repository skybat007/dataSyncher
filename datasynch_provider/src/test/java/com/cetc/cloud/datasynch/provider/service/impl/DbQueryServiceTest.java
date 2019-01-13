package com.cetc.cloud.datasynch.provider.service.impl;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Administrator on 2018/12/28.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DbQueryServiceTest extends TestCase {
    @Autowired
    DbQueryService dbQueryService;
    @Test
    public void testCheckIfTableExists() {
        boolean t_kz_small_place = dbQueryService.checkIfTableExists_readOnly("T_KZ_SMALL_PLACE");
        System.out.println(">>>>>" + t_kz_small_place);
    }
}