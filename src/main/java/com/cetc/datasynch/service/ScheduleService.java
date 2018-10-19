package com.cetc.datasynch.service;

import com.cetc.datasynch.model.ScheduleModel;
import com.cetc.datasynch.model.SynchJobLogInfoModel;

import java.util.List;

/**
 * Description：
 * Created by luolinjie on 2018/10/20.
 */
public interface ScheduleService {
    int addScheduleInstance(ScheduleModel scheduleModel);

    int deleteScheduleByJobId(int jobID);

    List<ScheduleModel> queryScheduleJobList();

    int updateCronByJobId(int jobId, String cron);

    int disableStatusByJobId(int jobId);

    int enableStatusByJobId(int jobId);

    String queryTableNameByJobId(int jobId);

    SynchJobLogInfoModel queryModelByJobId(int jobId);
}
