package com.cetc.cloud.datasynch.provider.client.service;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.api.service.ScheduleRemoteService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

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
@FeignClient(value = "data_synch", fallback = ScheduleService.HystrixClientFallback.class)
public interface ScheduleService extends ScheduleRemoteService {
    @Component
    class HystrixClientFallback implements ScheduleService {


        @Override
        public HashMap createScheduleJob(int connType, String source, String srcDs, int isPagingQuery, String orderByColumnName, String httpParamExpression, String httpToken, String httpPagingType, String httpParamPageSize, String httpParamPageNum, String httpJsonExtractRule, String targetTableName, int needsTruncateTargetTb, String pageSize, String cronExpression) throws SQLException {
            return null;
        }

        @Override
        public List<ScheduleModel> queryScheduleJobList() {
            return null;
        }

        @Override
        public HashMap<String, String> startScheduleJobByJobId(int jobId) {
            return null;
        }

        @Override
        public HashMap<String, String> triggerOnceJobByTargetTableName(String tableName) {
            return null;
        }

        @Override
        public HashMap<String, String> startOuterScheduleJob(String jobName, CronTrigger trigger) {
            return null;
        }

        @Override
        public HashMap<String, String> triggerOnceOuterScheduleJobByJobName(String jobName) {
            return null;
        }

        @Override
        public HashMap<String, String> startScheduleJobArrayByJobId(String jobs) {
            return null;
        }

        @Override
        public HashMap<String, String> startAllDSJobs() {
            return null;
        }

        @Override
        public HashMap<String, String> startAllEnabledScheduleJobs() {
            return null;
        }

        @Override
        public HashMap<String, String> disableJobStatusByJobId(int jobId) {
            return null;
        }

        @Override
        public HashMap<String, String> enableJobStatusByJobId(int jobId) {
            return null;
        }


        @Override
        public HashMap<String, String> deleteScheduleJobByJobId(int jobId) {
            return null;
        }

        @Override
        public HashMap<String, String> alterScheduleJobCron(int jobId, String cron) {
            return null;
        }

        @Override
        public Map<String, Future> getRunningFutures() {
            return null;
        }

    }
}
