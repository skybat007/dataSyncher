package com.cetc.cloud.datasynch.api.service;

import com.cetc.cloud.datasynch.api.model.ColumnMappingModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Description：字段映射表维护
 * Created by luolinjie on 2018/10/20.
 */
@Api(description = "字段映射表维护服务")
public interface ColumnMappingRemoteService {

    @RequestMapping(value = "/columnmapping/importExcel", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "importExcelIntoDB", notes = "将Excel人工维护的字段映射表，srcTable-targetTable映射表导入数据库进行维护", produces = "application/json")
    String importExcelIntoDB(MultipartFile file, String Data_StartRow_Num);

    @RequestMapping(value = "/columnmapping/getListInfo/byTargetTableName", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "getListInfoByTargetTableName", notes = "根据源表名称或源URL、目标表查询该表的映射关系列表", produces = "application/json")
    List<ColumnMappingModel> getListInfoByTargetTableName(String model);

    @RequestMapping(value = "/columnmapping/getListInfo/bySourceName", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "getListInfoByTargetTableName", notes = "根据源表名称或源URL、目标表查询该表的映射关系列表", produces = "application/json")
    List<ColumnMappingModel> getListInfoBySourceName(String sourceName);

    @RequestMapping(value = "/columnmapping/getListInfo/byTargetColumnName", produces = "application/json", method = RequestMethod.POST)
    List<ColumnMappingModel> getListInfoByTargetColumnName(String targetColumnName);

    @RequestMapping(value = "/columnmapping/getMap/byTargetTableName", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "getMapInfoByTargetTableName", notes = "根据源表名称或源URL、目标表查询该表的映射关系Map", produces = "application/json")
    Map<String,String> getMapInfoByTargetTableName(String targetTableName);

    @RequestMapping(value = "/columnmapping/delete/byId", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "deleteById", notes = "删除一条映射关系-通过id", produces = "application/json")
    int deleteById(int id);

    @RequestMapping(value = "/columnmapping/delete/byTargetTbName", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "deleteByTargetTbName", notes = "删除单张表的所有映射关系-通过目标表名", produces = "application/json")
    int deleteByTargetTbName(String targetTbName);

    @RequestMapping(value = "/columnmapping/add", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "deleteByTargetTbName", notes = "增加一条映射关系", produces = "application/json")
    int add(ColumnMappingModel model);

    @RequestMapping(value = "/columnmapping/update/byId", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "deleteByTargetTbName", notes = "修改一条映射关系-通过id", produces = "application/json")
    int updateById(int id, String targetTable, String source, String srcColumnName, String targetColumnName);
}
