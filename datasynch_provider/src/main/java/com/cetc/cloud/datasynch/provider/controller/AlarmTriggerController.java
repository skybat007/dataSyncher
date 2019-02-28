package com.cetc.cloud.datasynch.provider.controller;

import com.cetc.cloud.datasynch.api.service.AlarmTriggerRemoteService;
import com.cetc.cloud.datasynch.provider.service.impl.IotAlarmTriggerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package: com.cetc.cloud.datasynch.provider.controller
 * @Project: dataSyncher
 * @Creator: huangzezhou
 * @Create_Date: 2018/12/11 15:44
 * @Updater: huangzezhou
 * @Update_Date: 2018/12/11 15:44
 * @Update_Description: huangzezhou 补充
 * @Description: //TODO
 **/
@RestController
public class AlarmTriggerController implements AlarmTriggerRemoteService{

    @Autowired
    IotAlarmTriggerServiceImpl alarmTriggerService;

    @Override
    public Boolean startIotTrigger() {
        return true;
    }
}
