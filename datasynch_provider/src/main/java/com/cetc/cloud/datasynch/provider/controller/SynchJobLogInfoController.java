package com.cetc.cloud.datasynch.provider.controller;

import com.cetc.cloud.datasynch.api.model.SynchJobLogInfoModel;
import com.cetc.cloud.datasynch.api.service.SynchJobLogInfoRemoteService;
import com.cetc.cloud.datasynch.provider.service.impl.SynchJobLogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.controller
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/10/25 17:39
 * Updater:     by luolinjie
 * Update_Date: 2018/10/25
 * Update_Description: luolinjie 补充
 **/
@RestController
public class SynchJobLogInfoController implements SynchJobLogInfoRemoteService {

    @Autowired
    SynchJobLogInfoService synchJobLogInfoService;

    @Override
    public SynchJobLogInfoModel queryLatestInfoByJobId(int jobId){return synchJobLogInfoService.queryLatestInfoByJobId(jobId);}

    @Override
    public int deleteByJobId(int jobId){return synchJobLogInfoService.deleteByJobId(jobId);}

}
