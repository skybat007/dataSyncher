package com.cetc.cloud.datasynch.provider.service.impl;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by Administrator on 2018/12/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ScheduleServiceTest extends TestCase {
    @Autowired
    ScheduleService scheduleService;
    @Test
    public void testQueryScheduleJobList() throws Exception {
        List<ScheduleModel> scheduleModels = scheduleService.queryScheduleJobList();
        System.out.println(scheduleModels);
    }
}