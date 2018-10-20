package com.cetc.datasynch.service.impl;

import com.cetc.datasynch.mapper.ColumnMappingMapper;
import com.cetc.datasynch.api.model.ColumnMappingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 字段映射关系维护
 * Created by llj on 2018/10/14.
 */
@Service
public class ColumnMappingService implements com.cetc.datasynch.service.ColumnMappingService {

    @Autowired
    ColumnMappingMapper columnMappingMapper;

    @Override
    public HashMap<String,String> getColumnMappingByTargetTableName(String targetTable) {
        List<ColumnMappingModel> list = columnMappingMapper.getMappingByTableName(targetTable);
        if (list == null)
            return null;

        HashMap<String,String> columnMap = new HashMap<String, String>();

        for (int i = 0; i < list.size(); i++) {
            ColumnMappingModel model = list.get(i);
            columnMap.put(model.getSourceColumnName(), model.getTargetColumnName());
        }

        return columnMap;
    }

    @Override
    public String addList(List<ColumnMappingModel> modelList) {
        return null;
    }

    @Override
    public List<ColumnMappingModel> getListInfoBySource(String source) {
        return null;
    }

    @Override
    public List<ColumnMappingModel> getListInfoByTargetTable(String targetTable) {
        return null;
    }

    @Override
    public List<ColumnMappingModel> getListInfoByTargetColumnName(String targetColumnName) {
        return null;
    }
}
