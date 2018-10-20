package com.cetc.cloud.datasynch.provider.mapper;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.api.model.SynchJobLogInfoModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description：同步任务管理DAO层定义
 * Created by luolinjie on 2018/10/10.
 */
@Mapper
public interface ScheduleMapper {

    List<ScheduleModel> queryScheduleJobList();

    int deleteJobByJobId(int jobID);

    int updateCronByJobId(int jobID, String cron);

    int updateEnableStatusByJobId(int jobID, int i);

    String queryTableNameByJobId(int jobID);

    SynchJobLogInfoModel queryModelByJobId(int jobId);

    int addScheduleInstance(ScheduleModel scheduleModel);
}
