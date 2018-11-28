package com.cetc.cloud.datasynch.provider.controller;

import com.alibaba.fastjson.JSONObject;
import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.api.service.RePullTableRemoteService;
import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import com.cetc.cloud.datasynch.provider.service.impl.DbOperateService;
import com.cetc.cloud.datasynch.provider.service.impl.JobManageService;
import com.cetc.cloud.datasynch.provider.service.impl.ScheduleService;
import com.cetc.cloud.datasynch.provider.service.impl.SynchJobLogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.controller
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/11/20 20:09
 * Updater:     by luolinjie
 * Update_Date: 2018/11/20
 * Update_Description: luolinjie 补充
 **/
@RestController
public class RePullTableController implements RePullTableRemoteService {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    SynchJobLogInfoService synchJobLogInfoService;
    @Autowired
    DbOperateService dbOperateService;
    @Autowired
    JobManageService jobManageService;

    @Override
    public String clearAndPullAgainTableByJobId(String tableName) {

        JSONObject res = new JSONObject();

        //1.根据表名查询JobId
        ScheduleModel scheduleModel = scheduleService.queryModelByTableName(tableName);
        //disable任务
        scheduleService.alterJobStatusByJobId(scheduleModel.getId(), CommonInstance.DISABLED);

        //2.清空表
        boolean clearRes = dbOperateService.truncateTableByTbName(scheduleModel.getTargetTableName());

        //3.通过jobId清空日志
        if (clearRes) {
            int i = synchJobLogInfoService.deleteByJobId(scheduleModel.getId());
            int i1 =0;
            try {
                jobManageService.startOnceJob(scheduleModel);
            }finally {
                i1 = scheduleService.enableStatusByJobId(scheduleModel.getId());
            }
            if (i > 0 && i1 >0) {
                res.put("res", "success");
                res.put("msg", "successfully executed clearAndPullAgainTableByJobId, targetTable:" + scheduleModel.getTargetTableName());
                return res.toJSONString();
            } else {
                res.put("res", "fail");
                res.put("msg", "failed executed clearAndPullAgainTableByJobId, targetTable:" + scheduleModel.getTargetTableName());
                return res.toJSONString();
            }
        } else {
            res.put("res", "fail");
            res.put("msg", "failed executed truncateTableByTbName, targetTable:" + scheduleModel.getTargetTableName());
            return res.toJSONString();
        }


    }
}
