package com.cetc.cloud.datasynch.api.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Package: com.cetc.cloud.datasynch.api.service
 * @Project: dataSyncher
 * @Creator: huangzezhou
 * @Create_Date: 2018/12/11 11:06
 * @Updater: huangzezhou
 * @Update_Date: 2018/12/11 11:06
 * @Update_Description: huangzezhou 补充
 * @Description: //TODO
 **/
@Api(value = "触发器服务")
@RequestMapping("/trigger")
public interface AlarmTriggerRemoteService {

    @RequestMapping(value = "/iot/start", produces = "application/json", method = RequestMethod.GET)
    @ApiOperation(value = "启动物联网触发器", notes = "启动物联网触发器", produces = "application/json")
    Boolean startIotTrigger();

}
