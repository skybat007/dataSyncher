package com.cetc.cloud.datasynch.api.service;

import com.cetc.cloud.datasynch.api.model.DddOuterURLsModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * PackageName:   com.cetc.cloud.datasynch.api.service
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/12/14 17:29
 * Updater:     by luolinjie
 * Update_Date: 2018/12/14
 * Update_Description: luolinjie 补充
 **/
@Api(description = "外部接口管理")
public interface OuterUrlsRemoteService {
    @RequestMapping(value = "/outerUrl/get/byTableName", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "通过表名获取外部Job的model", notes = "", produces = "application/json")
    DddOuterURLsModel getModelByTableName(String tableName);
}
