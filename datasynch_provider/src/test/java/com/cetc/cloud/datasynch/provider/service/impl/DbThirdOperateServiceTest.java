package com.cetc.cloud.datasynch.provider.service.impl;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by Administrator on 2018/12/28.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DbThirdOperateServiceTest extends TestCase {
    @Autowired
    DbThirdOperateService dbThirdOperateService;
    @Test
    public void testGetAllTableList() throws Exception {
        List allTableList = dbThirdOperateService.getAllTableList();
        System.out.println(allTableList);

    }
    @Test
    public void testCheckIfTableExists(){
        boolean t_kz_small_place = dbThirdOperateService.checkIfTableExists("T_KZ_SMALL_PLACE");
        System.out.println(">>>>>"+t_kz_small_place);
    }
}