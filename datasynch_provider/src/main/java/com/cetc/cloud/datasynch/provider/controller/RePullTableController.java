package com.cetc.cloud.datasynch.provider.controller;

import com.alibaba.fastjson.JSONObject;
import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.api.service.RePullTableRemoteService;
import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import com.cetc.cloud.datasynch.provider.service.impl.DbOperateService;
import com.cetc.cloud.datasynch.provider.core.JobManageService;
import com.cetc.cloud.datasynch.provider.service.impl.ScheduleService;
import com.cetc.cloud.datasynch.provider.service.impl.SynchJobLogInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

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
@Slf4j
public class RePullTableController implements RePullTableRemoteService {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    SynchJobLogInfoService synchJobLogInfoService;
    @Autowired
    DbOperateService dbOperateService;
    @Autowired
    JobManageService jobManageService;
    @Autowired
    ScheduleController scheduleController;
    @Autowired
    SequenceManagerController sequenceManagerController;

    @Override
    public String clearAndPullAgainTableByTableName(String tableName) {

        JSONObject res = new JSONObject();

        //1.根据表名查询JobId
        ScheduleModel scheduleModel = scheduleService.queryModelByTableName(tableName);
        //disable任务
        scheduleService.alterJobStatusByJobId(scheduleModel.getId(), CommonInstance.JOB_DISABLED);

        //2.清空表
        String copyName = dbOperateService.backUpTable(scheduleModel.getTargetTableName());
        log.info("\n>> BackUpTable:"+scheduleModel.getTargetTableName()+"\nbackup tableName："+copyName);
        //DISABLE任务
        boolean clearRes = dbOperateService.truncateTableByTbName(scheduleModel.getTargetTableName());

        //3.序列归0
        try {
            sequenceManagerController.exactSequenceByTbName(scheduleModel.getTargetTableName());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //3.通过jobId清空日志
        if (clearRes) {
            int i = synchJobLogInfoService.deleteByJobId(scheduleModel.getId());
            if (i>0){
                log.info("delete schedule log success:"+i);
            }
            try {
                //ENABLE任务
                scheduleService.alterJobStatusByJobId(scheduleModel.getId(), CommonInstance.JOB_ENABLED);
                log.info("started once job:");
                scheduleController.triggerOnceJobByTargetTableName(scheduleModel.getTargetTableName());
            }catch (Exception e){
                log.error("Error starting once job:triggerOnceJobByTargetTableName()"+scheduleModel.getTargetTableName());
            }
            res.put("res", "success");
            res.put("msg", "success executed clearAndPullAgainTableByTableName, targetTable:" + scheduleModel.getTargetTableName());
            return res.toJSONString();
        } else {
            res.put("res", "fail");
            res.put("msg", "failed executed clearAndPullAgainTableByTableName, targetTable:" + scheduleModel.getTargetTableName());
            return res.toJSONString();
        }

    }
}
