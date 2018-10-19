package com.cetc.datasynch.api;

import com.cetc.datasynch.model.ScheduleModel;
import com.cetc.datasynch.model.Token;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Description：
 * Created by luolinjie on 2018/10/19.
 */
public interface ScheduleRemoteService {
    @RequestMapping(value = "/schedule/init", produces = "application/json", method = RequestMethod.GET)
    void initSQL() throws SQLException;

    @RequestMapping(value = "/schedule/job/querylist", produces = "application/json", method = RequestMethod.GET)
    List<ScheduleModel> queryScheduleJobList();

    @RequestMapping(value = "/schedule/job/create", produces = "application/json", method = RequestMethod.GET)
    HashMap createScheduleJob(int connType, String source, Token token, String jsonExtractRule, int pageSize, String tableName, String scheduleExpression) throws SQLException;

    @RequestMapping(value = "/schedule/job/start", produces = "application/json", method = RequestMethod.GET)
    HashMap<String, String> startScheduleJobByJobId(int jobID, String scheduleExpression, Runnable myScheduleRunnable);

    @RequestMapping(value = "/schedule/job/delete", produces = "application/json", method = RequestMethod.GET)
    HashMap<String, String> deleteScheduleJobByJobId(int jobID);

    @RequestMapping(value = "/schedule/dbjob/alter", produces = "application/json", method = RequestMethod.GET)
    HashMap<String, String> alterScheduleJob(int jobID, String cron);

    boolean restartJobByJobId(int jobId);
}
