package com.cetc.datasynch.client.service;

import com.cetc.datasynch.api.model.ColumnMappingModel;
import com.cetc.datasynch.api.service.ColumnMappingRemoteService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * PackageName:   com.cetc.datasynch.client.service
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/10/20 13:09
 * Updater:     by luolinjie
 * Update_Date: 2018/10/20
 * Update_Description: luolinjie 补充
 **/
@FeignClient(value = "data_synch", fallback = ColumnMappingService.HystrixClientFallback.class)
public interface ColumnMappingService extends ColumnMappingRemoteService {
    @Component
    class HystrixClientFallback implements ColumnMappingService{

        @Override
        public String importExcelIntoDB(MultipartFile file) {
            return null;
        }

        @Override
        public List<ColumnMappingModel> getListInfo(ColumnMappingModel model) {
            return null;
        }

        @Override
        public Map<String, String> getMapInfo(ColumnMappingModel model) {
            return null;
        }

        @Override
        public String deleteByid(int id) {
            return null;
        }

        @Override
        public String deleteByTargetTbName(String targetTbName) {
            return null;
        }

        @Override
        public String add(ColumnMappingModel model) {
            return null;
        }

        @Override
        public String update(ColumnMappingModel model) {
            return null;
        }
    }
}
