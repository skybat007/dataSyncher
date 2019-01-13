package com.cetc.cloud.datasynch.provider.template;

import com.cetc.cloud.datasynch.provider.service.impl.DbOperateService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

/**
 * Created by Administrator on 2018/12/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class WaterAQIRunnableTest extends TestCase {

    @Autowired
    DbOperateService dbOperateService;
    @Test
    public void testGenerateHourlyAQI() throws Exception {
        WaterAQIRunnable waterAQIRunnable = new WaterAQIRunnable(dbOperateService);
        waterAQIRunnable.generateHourlyAQI();
    }

    public void testGetMaxCreateDate() throws Exception {
        WaterAQIRunnable waterAQIRunnable = new WaterAQIRunnable(dbOperateService);
        Date maxCreateDate = waterAQIRunnable.getMaxCreateDate("3702810000200004");
        System.out.println(maxCreateDate);
    }
}