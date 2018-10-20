package com.cetc.cloud.datasynch.provider.service.impl;

import com.cetc.cloud.datasynch.api.model.ColumnMappingModel;
import com.cetc.cloud.datasynch.provider.mapper.ColumnMappingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 字段映射关系维护
 * Created by llj on 2018/10/14.
 */
@Service("columnMappingService")
public class ColumnMappingService implements com.cetc.cloud.datasynch.provider.service.ColumnMappingService {

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
    public int addList(List<ColumnMappingModel> modelList) {
        return columnMappingMapper.addList(modelList);
    }
    //todo:
    @Override
    public List<ColumnMappingModel> getListInfoBySource(String source) {
        return getListInfoBySource(source);
    }
    //todo:
    @Override
    public List<ColumnMappingModel> getListInfoByTargetTable(String targetTable) {
        return getListInfoByTargetTable(targetTable);
    }
    @Override
    public List<ColumnMappingModel> getListInfoByTargetColumnName(String targetColumnName) {
        return columnMappingMapper.getListInfoByTargetColumnName(targetColumnName);
    }
    @Override
    public int updateById(int id, ColumnMappingModel model) {
        return columnMappingMapper.updateById(id,model);
    }
    @Override
    public int deleteByTargetTbName(String targetTbName) {
        return columnMappingMapper.deleteByTargetTbName();
    }
    @Override
    public int add(ColumnMappingModel model) {
        return columnMappingMapper.add(model);
    }

    @Override
    public int deleteById(int id) {
        return columnMappingMapper.delteById(id);
    }
}
