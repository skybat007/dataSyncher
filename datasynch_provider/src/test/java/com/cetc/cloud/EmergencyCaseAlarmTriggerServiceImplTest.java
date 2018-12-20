package com.cetc.cloud;

import com.cetc.cloud.datasynch.provider.service.impl.alarm.EmergencyCaseAlarmTriggerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Package: com.cetc.cloud.alarm.trigger.service.impl
 * @Project: alarm-trigger
 * @Creator: huangzezhou
 * @Create_Date: 2018/12/17 20:57
 * @Updater: huangzezhou
 * @Update_Date: 2018/12/17 20:57
 * @Update_Description: huangzezhou 补充
 * @Description: //TODO
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmergencyCaseAlarmTriggerServiceImplTest {

    @Autowired
    EmergencyCaseAlarmTriggerServiceImpl emergencyCaseAlarmTriggerService;

    @Test
    public void test(){
        emergencyCaseAlarmTriggerService.triggerAlarm();
    }

}