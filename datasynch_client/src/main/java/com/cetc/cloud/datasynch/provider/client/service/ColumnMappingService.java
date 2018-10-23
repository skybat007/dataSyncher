package com.cetc.cloud.datasynch.provider.client.service;

import com.alibaba.fastjson.JSONObject;
import com.cetc.cloud.datasynch.api.model.ColumnMappingModel;
import com.cetc.cloud.datasynch.api.service.ColumnMappingRemoteService;
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
    class HystrixClientFallback implements ColumnMappingService {

        @Override
        public String importExcelIntoDB(MultipartFile file, String Data_StartRow_Num) {
            JSONObject result = new JSONObject();
            result.put("result","failed");
            result.put("data","null");
            return result.toJSONString();
        }

        @Override
        public List<ColumnMappingModel> getListInfoByTargetTableName(String model) {
            return null;
        }

        @Override
        public List<ColumnMappingModel> getListInfoBySourceName(String sourceName) {
            return null;
        }

        @Override
        public List<ColumnMappingModel> getListInfoByTargetColumnName(String targetColumnName) {
            return null;
        }

        @Override
        public Map<String, String> getMapInfoByTargetTableName(String targetTableName) {
            return null;
        }

        @Override
        public int deleteById(int id) {
            return -1;
        }

        @Override
        public int deleteByTargetTbName(String targetTbName) {
            return -1;
        }

        @Override
        public int add(String targetTable, String source, String sourceColumnName, String targetColumnName) {
            return -1;
        }

        @Override
        public int updateById(int id, String targetTable, String source, String srcColumnName, String targetColumnName) {
            return 0;
        }
    }
}
