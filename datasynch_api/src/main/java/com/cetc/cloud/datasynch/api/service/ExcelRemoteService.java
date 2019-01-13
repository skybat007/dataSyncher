package com.cetc.cloud.datasynch.api.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.controller
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/11/14 14:04
 * Updater:     by luolinjie
 * Update_Date: 2018/11/14
 * Update_Description: luolinjie 补充
 **/
@Api(description = "Excel导出工具")
public interface ExcelRemoteService {
    @RequestMapping(value = "/excel/export/byTableName", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "根据表名和列名导出Excel", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName", value = "表名", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "columns", value = "列/列名组合(col1,col2..)", required = true, dataType = "int", paramType = "query")
    })
    HashMap<String, String> exportExcelByTableName(String tableName, String columns);
}
