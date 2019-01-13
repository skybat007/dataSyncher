package com.cetc.cloud.datasynch.provider.controller;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Administrator on 2018/12/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Slf4j
public class RePullTableControllerTest extends TestCase {
    @Autowired
    RePullTableController rePullTableController;

    @Test
    public void testClearAndPullAgainTableByTableName() throws Exception {
        log.info("start test");
        rePullTableController.clearAndPullAgainTableByTableName("BLK_SANXIAO_PLACE");
        log.info("end test");
    }
}