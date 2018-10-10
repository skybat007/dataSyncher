package com.cetc.datasynch.mapper;

import com.cetc.datasynch.model.DBScheduleModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description：
 * Created by luolinjie on 2018/10/10.
 */
@Mapper
public interface DBScheduleMapper {

    List<DBScheduleModel> queryScheduleJobList();

    int deleteJobByJobId(String jobID);

    int updateCronByJobId(String jobID, String cron);

    int updateEnableStatusByJobId(String jobID, int i);

    String queryTableNameByJobId(String jobID);
}
