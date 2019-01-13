package com.cetc.cloud.datasynch.provider.template;

import com.cetc.cloud.datasynch.provider.service.impl.DbOperateService;
import com.cetc.cloud.datasynch.provider.service.impl.DbThirdOperateService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Administrator on 2018/12/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RepullSanxiaoListRunnableTest extends TestCase {
    @Autowired
    DbThirdOperateService dbOperateService_zhft;
    DbOperateService dbOperateService;

    @Test
    public void testRun() throws Exception {
        RefreshSanxiaoListRunnable repullSanxiaoListRunnable = new RefreshSanxiaoListRunnable(dbOperateService_zhft,dbOperateService);
        repullSanxiaoListRunnable.run();
        while (true) {

        }
    }
}