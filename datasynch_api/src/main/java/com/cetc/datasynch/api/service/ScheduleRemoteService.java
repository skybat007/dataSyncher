package com.cetc.datasynch.api.service;

import com.cetc.datasynch.api.model.ScheduleModel;
import com.cetc.datasynch.api.model.Token;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


public interface ScheduleRemoteService {

    @RequestMapping(value = "/schedule/job/querylist", produces = "application/json", method = RequestMethod.GET)
    List<ScheduleModel> queryScheduleJobList();

    @RequestMapping(value = "/schedule/job/create", produces = "application/json", method = RequestMethod.POST)
    HashMap createScheduleJob(int connType, String source, Token token, String jsonExtractRule, int pageSize, String tableName, String scheduleExpression) throws SQLException;

    @RequestMapping(value = "/schedule/job/start", produces = "application/json", method = RequestMethod.POST)
    HashMap<String, String> startScheduleJobByJobId(int jobID, String scheduleExpression, Runnable myScheduleRunnable);

    @RequestMapping(value = "/schedule/job/delete", produces = "application/json", method = RequestMethod.POST)
    HashMap<String, String> deleteScheduleJobByJobId(int jobID);

    @RequestMapping(value = "/schedule/job/alter", produces = "application/json", method = RequestMethod.POST)
    HashMap<String, String> alterScheduleJob(int jobID, String cron);

    @RequestMapping(value = "/schedule/job/restartJobByJobId", produces = "application/json", method = RequestMethod.POST)
    boolean restartJobByJobId(int jobId);
}
