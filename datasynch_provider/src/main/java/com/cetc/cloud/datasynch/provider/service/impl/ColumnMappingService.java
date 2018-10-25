package com.cetc.cloud.datasynch.provider.service.impl;

import com.cetc.cloud.datasynch.api.model.ColumnMappingModel;
import com.cetc.cloud.datasynch.provider.mapper.ColumnMappingMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * 字段映射关系维护
 * Created by llj on 2018/10/14.
 */
@Service("columnMappingService")
public class ColumnMappingService implements com.cetc.cloud.datasynch.provider.service.ColumnMappingService {

//    @Autowired
//    DataSource dataSource;
    @Autowired
    ColumnMappingMapper columnMappingMapper;


    @Override
    public HashMap<String, String> getColumnMappingByTargetTableName(String targetTable) {
        List<ColumnMappingModel> list = columnMappingMapper.getMappingByTargetTableName(targetTable);
        if (list == null)
            return null;
        HashMap<String, String> columnMap = new HashMap<String, String>();

        for (int i = 0; i < list.size(); i++) {
            ColumnMappingModel model = list.get(i);
            columnMap.put(model.getSourceColumnName(), model.getTargetColumnName());
        }

        return columnMap;
    }

    @Override
    public int add(ColumnMappingModel model) {
        int count = columnMappingMapper.add(model);
        if (count>0) {
            return model.getId();
        }else {
            return -1;
        }
    }

    @Override
    public int addList(List<ColumnMappingModel> modelList) {
        return columnMappingMapper.addList(modelList);
    }

    @Override
    public List<ColumnMappingModel> getListInfoBySource(String source) {
        return columnMappingMapper.getListInfoBySource(source);
    }

    @Override
    public List<ColumnMappingModel> getListInfoByTargetTableName(String targetTbName) {
        return columnMappingMapper.getListInfoByTargetTableName(targetTbName);
    }

    @Override
    public List<ColumnMappingModel> getListInfoByTargetColumnName(String targetColumnName) {
        return columnMappingMapper.getListInfoByTargetColumnName(targetColumnName);
    }

    @Override
    public int updateById(int id, String targetTable, String source, String srcColumnName, String targetColumnName) {
        return columnMappingMapper.updateById(id, targetTable, source, srcColumnName, targetColumnName);
    }

    @Override
    public int deleteById(int id) {
        return columnMappingMapper.deleteById(id);
    }

    @Override
    public int deleteByTargetTbName(String targetTbName) {
        return columnMappingMapper.deleteByTargetTbName(targetTbName);
    }

}
