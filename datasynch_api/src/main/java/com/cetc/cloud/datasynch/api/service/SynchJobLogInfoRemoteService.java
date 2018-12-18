package com.cetc.cloud.datasynch.api.service;

import com.cetc.cloud.datasynch.api.model.SynchJobLogInfoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * PackageName:   com.cetc.cloud.datasynch.api.service
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/10/25 17:37
 * Updater:     by luolinjie
 * Update_Date: 2018/10/25
 * Update_Description: luolinjie 补充
 **/
@Api(description = "同步日志管理")
public interface SynchJobLogInfoRemoteService {
    @RequestMapping(value = "/jobLog/get", produces = "application/json", method = RequestMethod.GET)
    @ApiOperation(value = "通过jobId获取最近一次历史执行日志", notes = "通过jobId获取最近一次历史执行日志", produces = "application/json")
    SynchJobLogInfoModel queryLatestInfoByJobId(int jobId);

    @RequestMapping(value = "/jobLog/delete/byJobId", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "清空该jobId的所有同步log记录", notes = "清空该jobId的所有同步log记录", produces = "application/json")
    int deleteByJobId(int jobId);
}
