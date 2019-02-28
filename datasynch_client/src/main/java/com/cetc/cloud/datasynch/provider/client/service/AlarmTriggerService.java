package com.cetc.cloud.datasynch.provider.client.service;

import com.cetc.cloud.datasynch.api.service.AlarmTriggerRemoteService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @Package: com.cetc.cloud.datasynch.provider.client.service
 * @Project: dataSyncher
 * @Creator: huangzezhou
 * @Create_Date: 2018/12/11 15:41
 * @Updater: huangzezhou
 * @Update_Date: 2018/12/11 15:41
 * @Update_Description: huangzezhou 补充
 * @Description: //TODO
 **/
@FeignClient(value = "alarmTriggerRemoteService", fallback = AlarmTriggerService.HystrixClientFallback.class)
public interface AlarmTriggerService extends AlarmTriggerRemoteService{

    @Component
    class HystrixClientFallback implements AlarmTriggerService{

        @Override
        public Boolean startIotTrigger() {
            return null;
        }
    }

}
