package com.cetc.cloud.datasynch.provider.mapper;

import com.cetc.cloud.datasynch.api.model.ColumnMappingModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 字段映射管理DAO层定义
 * Created by llj on 2018/10/14.
 */
@Mapper
public interface ColumnMappingMapper {

    int add(ColumnMappingModel model);

    int addList(List<ColumnMappingModel> modelList);

    List<ColumnMappingModel> getMappingByTargetTableName(String targetTbName);

    List<ColumnMappingModel> getListInfoByTargetTableName(String targetTbName);

    List<ColumnMappingModel> getListInfoByTargetColumnName(String targetColumnName);

    List<ColumnMappingModel> getListInfoBySource(String source);

    int updateById(@Param("id")int id,@Param("targetTable")String targetTable,@Param("source")String source,
                   @Param("srcColumnName")String srcColumnName,@Param("targetColumnName")String targetColumnName);

    int deleteByTargetTbName(String targetTbName);

    int deleteById(int id);//删除多少条，返回数字就是多少
}
