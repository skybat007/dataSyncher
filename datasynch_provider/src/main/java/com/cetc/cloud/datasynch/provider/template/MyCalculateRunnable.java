package com.cetc.cloud.datasynch.provider.template;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.api.model.SynchJobLogInfoModel;
import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import com.cetc.cloud.datasynch.provider.middleware.SQLCreator;
import com.cetc.cloud.datasynch.provider.service.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Description：创建定时 执行计算 实例
 * Created by luolinjie on 2018/10/10.
 */
public class MyCalculateRunnable implements Runnable {
    private ScheduleModel scheduleModel;

    private Logger logger = LoggerFactory.getLogger(MyCalculateRunnable.class);
    private SynchJobLogInfoService synchJobLogInfoService;
    private ScheduleService scheduleService;
    private DbQueryService dbQueryService;
    private DbOperateService dbOperateService;
    private HttpOperateService httpOperateService;

    public MyCalculateRunnable(ScheduleModel scheduleModel, SynchJobLogInfoService synchJobLogInfoService, ScheduleService scheduleService, DbQueryService dbQueryService, DbOperateService dbOperateService, HttpOperateService httpOperateService) {
        this.scheduleModel = scheduleModel;
        this.synchJobLogInfoService = synchJobLogInfoService;
        this.scheduleService = scheduleService;
        this.dbQueryService = dbQueryService;
        this.dbOperateService = dbOperateService;
        this.httpOperateService = httpOperateService;
    }

    @Override
    public void run() {
        //todo 执行计算,输出匹配的三小场所id

        //todo 更新至三小场所表

        //todo 更新至三小场所count表



    }


}


