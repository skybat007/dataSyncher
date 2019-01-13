package com.cetc.cloud;

import com.cetc.cloud.datasynch.provider.service.impl.alarm.IotAlarmTriggerServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Package: com.cetc.cloud.alarm.trigger.service
 * @Project: alarm-trigger
 * @Creator: huangzezhou
 * @Create_Date: 2018/12/17 14:59
 * @Updater: huangzezhou
 * @Update_Date: 2018/12/17 14:59
 * @Update_Description: huangzezhou 补充
 * @Description: //TODO
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class AlarmTriggerTest {


    @Autowired
    IotAlarmTriggerServiceImpl iotAlarmTriggerService;

    @org.junit.Test
    public void testTriggerAlarm() throws Exception {
        iotAlarmTriggerService.triggerAlarm();
        System.out.println();
    }
}