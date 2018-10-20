package com.cetc.datasynch.client.service;

import com.cetc.datasynch.api.model.ScheduleModel;
import com.cetc.datasynch.api.model.Token;
import com.cetc.datasynch.api.service.ScheduleRemoteService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * 工程包名:   com.cetc.datasynch.client.service
 * 项目名称:   dataSyncher
 * 创建描述:   luolinjie 补充
 * Creator:     luolinjie
 * Create_Date: 2018/10/20
 * Updater:     luolinjie
 * Update_Date：2018/10/20
 * 更新描述:    luolinjie 补充
 **/
@FeignClient(value = "cetc-msg", fallback = ScheduleService.HystrixClientFallback.class)
public interface ScheduleService extends ScheduleRemoteService{
    @Component
    class HystrixClientFallback implements ScheduleService{

        @Override
        public List<ScheduleModel> queryScheduleJobList() {
            return null;
        }

        @Override
        public HashMap createScheduleJob(int connType, String source, Token token, String jsonExtractRule, int pageSize, String tableName, String scheduleExpression) throws SQLException {
            return null;
        }

        @Override
        public HashMap<String, String> startScheduleJobByJobId(int jobID, String scheduleExpression, Runnable myScheduleRunnable) {
            return null;
        }

        @Override
        public HashMap<String, String> deleteScheduleJobByJobId(int jobID) {
            return null;
        }

        @Override
        public HashMap<String, String> alterScheduleJob(int jobID, String cron) {
            return null;
        }

        @Override
        public boolean restartJobByJobId(int jobId) {
            return false;
        }
    }
}
