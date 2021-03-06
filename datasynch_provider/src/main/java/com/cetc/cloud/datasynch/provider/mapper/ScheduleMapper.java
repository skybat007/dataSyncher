package com.cetc.cloud.datasynch.provider.mapper;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.api.model.SynchJobLogInfoModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description：同步任务管理DAO层定义
 * Created by luolinjie on 2018/10/10.
 */
@Mapper
public interface ScheduleMapper {

    List<ScheduleModel> queryScheduleJobList();

    int deleteJobByJobId(@Param("jobId")int jobId);

    int updateCronByJobId(@Param("jobId")int jobId,@Param("cron")String cron);

    int updateEnableStatusByJobId(@Param("jobId")int jobId, @Param("isEnabled")int isEnabled);

    ScheduleModel queryModelByJobId(@Param("jobId")int jobId);

    int addScheduleInstance( ScheduleModel scheduleModel);

    int getStatusByJobId(@Param("jobId")int jobId);

    ScheduleModel queryModelByTableName(@Param("tableName")String tableName);
}
