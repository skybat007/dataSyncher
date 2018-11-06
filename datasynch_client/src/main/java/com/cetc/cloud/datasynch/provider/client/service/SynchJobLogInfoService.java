package com.cetc.cloud.datasynch.provider.client.service;

import com.cetc.cloud.datasynch.api.model.SynchJobLogInfoModel;
import com.cetc.cloud.datasynch.api.service.SynchJobLogInfoRemoteService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * 工程包名:   com.cetc.datasynch.client.service
 * 项目名称:   dataSyncher
 * 创建描述:   luolinjie 补充
 * Creator:     luolinjie
 * Create_Date: 2018/10/20
 * Updater:     luolinjie
 * Update_Date：2018/10/20
 * 更新描述:    luolinjie 补充
 **/
@FeignClient(value = "data_synch", fallback = SynchJobLogInfoService.HystrixClientFallback.class)
public interface SynchJobLogInfoService extends SynchJobLogInfoRemoteService {
    @Component
    class HystrixClientFallback implements SynchJobLogInfoService {


        @Override
        public SynchJobLogInfoModel queryLatestInfoByJobId(int jobId) {
            return null;
        }
    }
}
