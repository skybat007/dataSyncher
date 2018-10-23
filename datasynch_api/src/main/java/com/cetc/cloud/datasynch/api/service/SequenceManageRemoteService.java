package com.cetc.cloud.datasynch.api.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * PackageName:   com.cetc.cloud.datasynch.api.service
 * projectName:   dataSyncher
 * Description:   自增序列管理
 * Creator:     by luolinjie
 * Create_Date: 2018/10/23 17:13
 * Updater:     by luolinjie
 * Update_Date: 2018/10/23
 * Update_Description: luolinjie 补充
 **/
@Api(value = "自增序列管理")
public interface SequenceManageRemoteService {

    @ApiOperation(value = "querySequenceList", notes = "查询当前业务库所有序列名称", produces = "application/json")
    List<String> querySequenceList();

    @ApiOperation(value = "create", notes = "设置序列的下一个值", produces = "application/json")
    List<String> create(String sequenceName,String value);

    @ApiOperation(value = "setNextValueBySequenceName", notes = "设置序列的下一个值", produces = "application/json")
    List<String> setNextValueBySequenceName(String sequenceName,String value);

    @ApiOperation(value = "alterMaxValue", notes = "修改序列的最大允许值", produces = "application/json")
    List<String> alterMaxValue(String sequenceName,String maxvalue);


}
