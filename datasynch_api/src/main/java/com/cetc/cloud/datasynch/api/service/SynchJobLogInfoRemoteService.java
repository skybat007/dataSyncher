package com.cetc.cloud.datasynch.api.service;

import com.cetc.cloud.datasynch.api.model.SynchJobLogInfoModel;
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
public interface SynchJobLogInfoRemoteService {
    @RequestMapping(value = "/jobLog/get", produces = "application/json", method = RequestMethod.GET)
    @ApiOperation(value = "queryLatestInfoByJobId", notes = "通过jobId获取最近一次历史执行日志", produces = "application/json")
    SynchJobLogInfoModel queryLatestInfoByJobId(int jobId);
}
