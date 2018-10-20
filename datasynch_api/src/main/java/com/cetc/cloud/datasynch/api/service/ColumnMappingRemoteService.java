package com.cetc.cloud.datasynch.api.service;

import com.cetc.cloud.datasynch.api.model.ColumnMappingModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Description：字段映射表维护
 * Created by luolinjie on 2018/10/20.
 */
public interface ColumnMappingRemoteService {
    /**
     * 将Excel人工维护的字段映射表，srcTable-targetTable映射表导入数据库进行维护
     * @param file 固定格式的Excel文件
     * @return
     */
    @RequestMapping(value = "/columnmapping/importExcel", produces = "application/json", method = RequestMethod.POST)
    String importExcelIntoDB(MultipartFile file, String Data_StartRow_Num);

    /**
     * 根据源表名称或源URL、目标表查询该表的映射关系列表
     * @param model 包含各种查询条件，在实现中指定query的Key
     * @return 映射关系列表
     */
    @RequestMapping(value = "/columnmapping/getListInfo", produces = "application/json", method = RequestMethod.POST)
    List<ColumnMappingModel> getListInfo(ColumnMappingModel model);

    /**
     * 根据源表名称或源URL、目标表查询该表的映射关系Map
     * @param model 根据单张表
     * @return 映射关系Map
     */
    @RequestMapping(value = "/columnmapping/getMapInfo", produces = "application/json", method = RequestMethod.POST)
    Map<String,String> getMapInfo(ColumnMappingModel model);

    /**
     * 删除一条映射关系
     * @param id 该映射关系的唯一标识
     * @return 删除结果：success/fail
     */
    @RequestMapping(value = "/columnmapping/deleteByid", produces = "application/json", method = RequestMethod.POST)
    int deleteById(int id);

    /**
     * 删除单张表的所有映射关系
     * @param targetTbName 目标表的名称
     * @return 删除结果：success/fail
     */
    @RequestMapping(value = "/columnmapping/deleteByTargetTbName", produces = "application/json", method = RequestMethod.POST)
    int deleteByTargetTbName(String targetTbName);

    /**
     * 增加一条映射关系
     * @param model 包含该映射关系的所有重要信息
     * @return 插入结果：success/fail 并返回该条记录的id
     */
    @RequestMapping(value = "/columnmapping/add", produces = "application/json", method = RequestMethod.POST)
    int add(ColumnMappingModel model);

    /**
     * 修改一条映射关系
     * @param model 包含该映射关系的所有重要信息
     * @return 修改结果：success/fail
     */
    @RequestMapping(value = "/columnmapping/updateById", produces = "application/json", method = RequestMethod.POST)
    int updateById(int id, ColumnMappingModel model);
}
