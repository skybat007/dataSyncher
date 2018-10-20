package com.cetc.cloud.datasynch.provider.mapper;

import com.cetc.cloud.datasynch.api.model.ColumnMappingModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 字段映射管理DAO层定义
 * Created by llj on 2018/10/14.
 */
@Mapper
public interface ColumnMappingMapper {

    List<ColumnMappingModel> getMappingByTableName(String targetTable);

    int addList(List<ColumnMappingModel> modelList);

    List<ColumnMappingModel> getListInfoByTargetColumnName(String targetColumnName);

    int updateById(int id, ColumnMappingModel model);

    int deleteByTargetTbName();

    int add(ColumnMappingModel model);

    int delteById(int id);
}
