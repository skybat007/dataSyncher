package com.cetc.datasynch.mapper;

import com.cetc.datasynch.model.ScheduleModel;
import com.cetc.datasynch.model.SynchJobLogInfoModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description：
 * Created by luolinjie on 2018/10/10.
 */
@Mapper
public interface ScheduleMapper {

    List<ScheduleModel> queryScheduleJobList();

    int deleteJobByJobId(String jobID);

    int updateCronByJobId(int jobID, String cron);

    int updateEnableStatusByJobId(int jobID, int i);

    String queryTableNameByJobId(int jobID);

    SynchJobLogInfoModel queryModelByJobId(int jobId);

    int addScheduleInstance(ScheduleModel scheduleModel);
}
