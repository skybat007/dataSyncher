package com.cetc.datasynch.client.service;

import com.cetc.datasynch.api.service.InitialRemoteService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * PackageName:   com.cetc.datasynch.client.service
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/10/20 13:08
 * Updater:     by luolinjie
 * Update_Date: 2018/10/20
 * Update_Description: luolinjie 补充
 **/
@FeignClient(value = "cetc-msg", fallback = InitialService.HystrixClientFallback.class)
public interface InitialService extends InitialRemoteService {

    @Component
    class HystrixClientFallback implements InitialService{

        @Override
        public String initSQL() throws SQLException {
            return null;
        }
    }
}
