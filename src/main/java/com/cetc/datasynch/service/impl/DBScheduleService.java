package com.cetc.datasynch.service.impl;

import com.cetc.datasynch.mapper.DBScheduleMapper;
import com.cetc.datasynch.model.DBScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description：数据库方式的Job服务管理
 * Created by luolinjie on 2018/10/10.
 */
@Service
public class DBScheduleService {

    @Autowired
    DBScheduleMapper dbScheduleMapper;

    public int deleteJobByJobId(String jobID) {
        return dbScheduleMapper.deleteJobByJobId(jobID);
    }

    public List<DBScheduleModel> queryScheduleJobList() {
        return dbScheduleMapper.queryScheduleJobList();
    }

    public int updateCronByJobId(String jobID, String cron) {
        return dbScheduleMapper.updateCronByJobId(jobID, cron);
    }
    public int disableStatusByJobId(String jobID) {
        return dbScheduleMapper.updateEnableStatusByJobId(jobID, 0);
    }
    public int enableStatusByJobId(String jobID) {
        return dbScheduleMapper.updateEnableStatusByJobId(jobID,1);
    }

    public String queryTableNameByJobId(String jobID) {
        return dbScheduleMapper.queryTableNameByJobId(jobID);
    }
}
