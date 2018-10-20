package com.cetc.datasynch.mapper;

import com.cetc.datasynch.api.model.ColumnMappingModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 字段映射管理DAO层定义
 * Created by llj on 2018/10/14.
 */
@Mapper
public interface ColumnMappingMapper {

    List<ColumnMappingModel> getMappingByTableName(String targetTable);
}
