package com.cetc.datasynch.service;

import com.cetc.datasynch.api.model.ColumnMappingModel;

import java.util.HashMap;
import java.util.List;

/**
 * Description：字段映射关系维护功能 Service层抽象
 * Created by luolinjie on 2018/10/19.
 */
public interface ColumnMappingService {
    /**
     * 根据目标表名称获取映射关系Map
     * @param targetTable
     * @return
     */
    HashMap<String,String> getColumnMappingByTargetTableName(String targetTable);

    /**
     * 批量添加，用于辅助Excel导入功能
     * @param modelList
     * @return
     */
    String addList(List<ColumnMappingModel> modelList);
    /**
     * 获取列表查询信息--通过源表名称/URL名称
     * @param source
     * @return
     */
    List<ColumnMappingModel> getListInfoBySource(String source);
    /**
     * 获取列表查询信息--通过目标表名称
     * @param targetTable
     * @return
     */
    List<ColumnMappingModel> getListInfoByTargetTable(String targetTable);
    /**
     * 获取列表查询信息--通过目标字段-用于模糊检索
     * @param targetColumnName
     * @return
     */
    List<ColumnMappingModel> getListInfoByTargetColumnName(String targetColumnName);
}
