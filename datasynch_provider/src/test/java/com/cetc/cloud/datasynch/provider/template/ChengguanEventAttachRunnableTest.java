package com.cetc.cloud.datasynch.provider.template;

import com.cetc.cloud.datasynch.provider.service.impl.DbOperateService;
import com.cetc.cloud.datasynch.provider.service.impl.HttpOperateService;
import com.cetc.cloud.datasynch.provider.service.impl.OuterUrlsService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Administrator on 2018/12/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ChengguanEventAttachRunnableTest extends TestCase {

    @Autowired
    DbOperateService dbOperateService;
    @Autowired
    HttpOperateService httpOperateService;
    @Autowired
    OuterUrlsService outerUrlsService;

    @Test
    public void testRun() throws Exception {
        ChengguanEventAttachRunnable chengguanEventAttachRunnable = new ChengguanEventAttachRunnable( dbOperateService, httpOperateService, outerUrlsService);
        chengguanEventAttachRunnable.run();
    }
}