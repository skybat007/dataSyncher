package com.cetc.cloud.datasynch.api.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * PackageName:   com.cetc.cloud.datasynch.api.service
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/11/20 20:43
 * Updater:     by luolinjie
 * Update_Date: 2018/11/20
 * Update_Description: luolinjie 补充
 **/
@Api(description = "reset")
public interface RePullTableRemoteService {

    @RequestMapping(value = "/table/clearAndPullAgain", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "清空并自动重新全量拉取表", notes = "step0:关闭任务; step1:清空表 step2:清空日志 step3:开启任务", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName", value = "目标表名", required = true, dataType = "String", paramType = "query"),
    })
    String clearAndPullAgainTableByTableName(String tableName);
}
