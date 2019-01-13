package com.cetc.cloud.datasynch.provider.controller;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;

/**
 * Created by Administrator on 2019/1/8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Slf4j
public class ScheduleControllerTest extends TestCase {
    @Autowired
    ScheduleController schduleController;

    @Test
    public void testCreateScheduleJob() throws Exception {

        HashMap blk_sanxiao_place = schduleController.createScheduleJob(1, "hqcsjclbxx/v1/gwapi", 0, 0, null, "page=1&pageSize=50", "Auth:233545", null, "10000", null, "rows.*",
                "BLK_SANXIAO_PLACE", 0, "10000", "0 0 0 * * ?");
        blk_sanxiao_place.values().stream().forEach(object -> System.out.println(object));
    }
}