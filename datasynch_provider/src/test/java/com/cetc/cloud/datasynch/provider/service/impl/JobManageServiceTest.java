package com.cetc.cloud.datasynch.provider.service.impl;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Administrator on 2018/12/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class JobManageServiceTest extends TestCase {
    @Autowired
    JobManageService jobManageService;
    @Autowired
    ScheduleService scheduleService;
    @Test
    public void testStartOnceJob() throws Exception {
        ScheduleModel blk_sanxiao_place = scheduleService.queryModelByTableName("BLK_SANXIAO_PLACE");
        int i = jobManageService.startOnceJob(blk_sanxiao_place);
        while (true){}
    }
}