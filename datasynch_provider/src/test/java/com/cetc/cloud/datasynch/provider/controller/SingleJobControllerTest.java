package com.cetc.cloud.datasynch.provider.controller;

import com.cetc.cloud.datasynch.provider.mapper.input.XinfangEventMapper;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Set;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.controller
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/12/4 10:59
 * Updater:     by luolinjie
 * Update_Date: 2018/12/4
 * Update_Description: luolinjie 补充
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class SingleJobControllerTest extends TestCase {

    @Autowired
    SingleJobController singleJobController;
    @Autowired
    XinfangEventMapper xinfangEventMapper;

    public void testInsertXinfangDataToday() throws Exception {
        Set<String> visitCodeSet = xinfangEventMapper.getVisitCodeList();
        System.out.println(visitCodeSet);
    }

    @Test
    public void testInsertXinfangDataToday1() throws Exception {
        singleJobController.insertXinfangDataToday();
    }
    @Test
    public void testInsertXinfangHistoryData() throws Exception {
        singleJobController.insertXinfangHistoryData("2018-12-12", "2018-12-13");
    }
}