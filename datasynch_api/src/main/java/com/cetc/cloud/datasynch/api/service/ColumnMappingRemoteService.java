package com.cetc.cloud.datasynch.api.service;

import com.cetc.cloud.datasynch.api.model.ColumnMappingModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    @ApiOperation(value = "importExcelIntoDB", notes = "将Excel人工维护的 srcTable-targetTable 字段映射表导入数据库进行维护," +
            "\r\n字段排序：\t源,源字段名,目标字段名,目标表\n", produces = "application/json")
    String importExcelIntoDB(MultipartFile file, String Data_StartRow_Num);

    @RequestMapping(value = "/columnmapping/getListInfo/byTargetTableName", produces = "application/json", method = RequestMethod.GET)
    @ApiOperation(value = "getListInfoByTargetTableName", notes = "根据源表名称或源URL、目标表查询该表的映射关系列表", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "targetTbName", value = "目标表名", required = true, dataType = "String", paramType = "query"),
    })
    List<ColumnMappingModel> getListInfoByTargetTableName(String targetTbName);

    @RequestMapping(value = "/columnmapping/getListInfo/bySourceName", produces = "application/json", method = RequestMethod.GET)
    @ApiOperation(value = "getListInfoBySourceName", notes = "根据源表名称或源URL、目标表查询该表的映射关系列表", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceName", value = "源（URL/表名）", required = true, dataType = "String", paramType = "query"),
    })
    List<ColumnMappingModel> getListInfoBySourceName(String sourceName);

    @RequestMapping(value = "/columnmapping/getListInfo/byTargetColumnName", produces = "application/json", method = RequestMethod.GET)
    @ApiOperation(value = "getListInfoByTargetColumnName", notes = "根据源表名称或源URL、目标表查询该表的映射关系列表", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceName", value = "源（URL/表名）", required = true, dataType = "String", paramType = "query"),
    })
    List<ColumnMappingModel> getListInfoByTargetColumnName(String targetColumnName);

    @RequestMapping(value = "/columnmapping/getMap/byTargetTableName", produces = "application/json", method = RequestMethod.GET)
    @ApiOperation(value = "getMapInfoByTargetTableName", notes = "根据源表名称或源URL、目标表查询该表的映射关系Map", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "targetTableName", value = "目标表名", required = true, dataType = "String", paramType = "query"),
    })
    Map<String, String> getMapInfoByTargetTableName(String targetTableName);

    @RequestMapping(value = "/columnmapping/delete/byId", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "deleteById", notes = "删除一条映射关系", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id值", required = true, dataType = "int", paramType = "query"),
    })
    int deleteById(int id);

    @RequestMapping(value = "/columnmapping/delete/byTargetTbName", produces = "application/json", method = RequestMethod.DELETE)
    @ApiOperation(value = "deleteByTargetTbName", notes = "删除单张表的所有映射关系-通过目标表名", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "targetTbName", value = "目标表名", required = true, dataType = "String", paramType = "query"),
    })
    int deleteByTargetTbName(String targetTbName);

    @RequestMapping(value = "/columnmapping/add", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "add", notes = "增加一条映射关系", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "targetTable", value = "目标表名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "source", value = "源(表名/URL)", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sourceColumnName", value = "源字段名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "targetColumnName", value = "目标字段名", required = true, dataType = "String", paramType = "query"),
    })
    int add(String targetTable, String source, String sourceColumnName, String targetColumnName);

    @RequestMapping(value = "/columnmapping/update/byId", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "updateById", notes = "修改一条映射关系-通过id", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "targetTable", value = "目标表名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "source", value = "源(表名/URL)", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sourceColumnName", value = "源字段名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "targetColumnName", value = "目标字段名", required = false, dataType = "String", paramType = "query"),
    })
    int updateById(int id, String targetTable, String source, String srcColumnName, String targetColumnName);
}
