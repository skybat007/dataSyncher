package com.cetc.datasynch.service;

import com.cetc.datasynch.mapper.DBScheduleMapper;
import com.cetc.datasynch.model.DBScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description：
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
}
