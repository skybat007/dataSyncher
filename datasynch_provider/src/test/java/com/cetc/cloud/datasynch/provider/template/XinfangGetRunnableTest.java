package com.cetc.cloud.datasynch.provider.template;

import com.cetc.cloud.datasynch.provider.mapper.XinfangEventMapper;
import com.cetc.cloud.datasynch.provider.service.impl.DbOperateService;
import com.cetc.cloud.datasynch.provider.service.impl.DbQueryService;
import com.cetc.cloud.datasynch.provider.service.impl.HttpOperateService;
import com.cetc.cloud.datasynch.provider.service.impl.OuterUrlsService;
import junit.framework.TestCase;
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
public class XinfangGetRunnableTest extends TestCase {
    @Autowired
    DbQueryService dbQueryService;
    @Autowired
    DbOperateService dbOperateService;
    @Autowired
    HttpOperateService httpOperateService;
    @Autowired
    OuterUrlsService outerUrlsService;
    @Autowired
    XinfangEventMapper xinfangEventMapper;

    public void testInsertXinfangDataToday() throws Exception {
        XinfangGetRunnable xinfangGetRunnable = new XinfangGetRunnable(outerUrlsService,xinfangEventMapper);
        xinfangGetRunnable.run();
    }
}