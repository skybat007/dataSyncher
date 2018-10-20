package com.cetc.cloud.datasynch.api.service;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.api.model.Token;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


public interface ScheduleRemoteService {
    /**
     * 查询表同步任务List
     */
    @RequestMapping(value = "/schedule/job/querylist", produces = "application/json", method = RequestMethod.GET)
    List<ScheduleModel> queryScheduleJobList();
    /**
     * 新增一条同步任务
     */
    @RequestMapping(value = "/schedule/job/create", produces = "application/json", method = RequestMethod.POST)
    HashMap createScheduleJob(int connType, String source, Token token, String jsonExtractRule, int pageSize, String tableName, String scheduleExpression) throws SQLException;
    /**
     * 根据jobID启动任务
     */
    @RequestMapping(value = "/schedule/job/start", produces = "application/json", method = RequestMethod.POST)
    HashMap<String, String> startScheduleJobByJobId(int jobID, String scheduleExpression, Runnable myScheduleRunnable);
    /**
     * 删除一条同步任务
     */
    @RequestMapping(value = "/schedule/job/delete", produces = "application/json", method = RequestMethod.POST)
    HashMap<String, String> deleteScheduleJobByJobId(int jobID);
    /**
     * 修改一条表同步任务的更新频率--仅修改定时表达式
     */
    @RequestMapping(value = "/schedule/job/alter", produces = "application/json", method = RequestMethod.POST)
    HashMap<String, String> alterScheduleJob(int jobID, String cron);
    /**
     * 重新启动任务
     */
    @RequestMapping(value = "/schedule/job/restartJobByJobId", produces = "application/json", method = RequestMethod.POST)
    boolean restartJobByJobId(int jobId);
}
