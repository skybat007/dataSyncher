package com.cetc.cloud.datasynch.provider.service.impl;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.service.impl
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/12/11 10:55
 * Updater:     by luolinjie
 * Update_Date: 2018/12/11
 * Update_Description: luolinjie 补充
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DbOperateServiceTest extends TestCase {
    @Autowired
    DbOperateService dbOperateService;

    @Test
    public void testGetMaxObjectId() throws Exception {
        int qhsj_enterprise_hour_data = dbOperateService.getMaxObjectId("COMMUNITY_CODE");
        System.out.println(qhsj_enterprise_hour_data);

    }

    @Test
    public void testGetNextSeqVal() throws Exception {
        int seq_urban_risk_detail = dbOperateService.getNextSeqVal("SEQ_URBAN_RISK_DETAIL");
        System.out.println("SEQ_URBAN_RISK_DETAIL.netxVal:" + seq_urban_risk_detail);
    }
}