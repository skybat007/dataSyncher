package com.cetc.cloud.datasynch.provider.util;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Calendar;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.util
 * projectName:   dataSyncher
 * Description:   LuoLinjie 补充
 * Creator:     by LuoLinjie
 * Create_Date: 2019/2/28 11:18
 * Updater:     by Administrator
 * Update_Date: 2019/2/28 11:18
 * Update_Description: Administrator 补充
 */

public class DateCronUtilTest extends TestCase {
    @Test
    public void testGetCron() throws Exception {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND,3);
        String cron = Date2CronUtil.getCron(instance.getTime());
        CronTrigger trigger = new CronTrigger(cron);
        System.out.println(trigger.getExpression());
    }
}