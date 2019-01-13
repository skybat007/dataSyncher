package com.cetc.cloud.datasynch.provider.template;

import com.cetc.cloud.datasynch.api.model.SanxiaoModel;
import com.cetc.cloud.datasynch.provider.service.impl.DbOperateService;
import com.cetc.cloud.datasynch.provider.service.impl.DbThirdOperateService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashSet;

/**
 * Created by Administrator on 2018/12/28.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RefreshSanxiaoListRunnableTest extends TestCase {
    @Autowired
    DbOperateService dbOperateService;
    @Autowired
    DbThirdOperateService dbOperateService_zhft;
    @Test
    public void testRun() {
        RefreshSanxiaoListRunnable refreshSanxiaoListRunnable = new RefreshSanxiaoListRunnable(dbOperateService_zhft, dbOperateService);
        refreshSanxiaoListRunnable.run();
    }


    @Test
    public void testSetContains() {
        HashSet<SanxiaoModel> sanxiaoModelset = new HashSet<>();
        sanxiaoModelset.add(new SanxiaoModel("123", "1"));
        sanxiaoModelset.add(new SanxiaoModel("1", "0"));
        sanxiaoModelset.add(new SanxiaoModel("25", "1"));
        sanxiaoModelset.add(new SanxiaoModel("36", "0"));
        sanxiaoModelset.add(new SanxiaoModel("85", "1"));
        sanxiaoModelset.add(new SanxiaoModel("95", "0"));
        sanxiaoModelset.add(new SanxiaoModel("02", "1"));
        SanxiaoModel newModel = new SanxiaoModel("123", "0");
        if (sanxiaoModelset.contains(newModel)) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }
    }
}