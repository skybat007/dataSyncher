package com.cetc.datasynch.service.impl;

import com.cetc.datasynch.api.model.SynchJobLogInfoModel;
import com.cetc.datasynch.mapper.ScheduleMapper;
import com.cetc.datasynch.api.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description：数据库方式的Job服务管理(数据库修改)
 * Created by luolinjie on 2018/10/10.
 */
@Service
public class ScheduleService implements com.cetc.datasynch.service.ScheduleService {

    @Autowired
    ScheduleMapper scheduleMapper;

    @Override
    public int addScheduleInstance(ScheduleModel scheduleModel) {
        String source = scheduleModel.getSource();
        int pageSize = scheduleModel.getPageSize();
        String tableName = scheduleModel.getTableName();
        String scheduleExpression = scheduleModel.getScheduleExpression();
        int connType = scheduleModel.getConnType();
        if (null==source || pageSize<1 || null==tableName ||null==scheduleExpression || connType<0||connType>1){
            return -1;
        }
        int jobId = scheduleMapper.addScheduleInstance(scheduleModel);
        return jobId;
    }
    @Override
    public int deleteScheduleByJobId(int jobID) {
        return scheduleMapper.deleteJobByJobId(jobID);
    }

    @Override
    public List<ScheduleModel> queryScheduleJobList() {
        return scheduleMapper.queryScheduleJobList();
    }

    @Override
    public int updateCronByJobId(int jobId, String cron) {
        return scheduleMapper.updateCronByJobId(jobId, cron);
    }
    @Override
    public int disableStatusByJobId(int jobId) {
        return scheduleMapper.updateEnableStatusByJobId(jobId, 0);
    }
    @Override
    public int enableStatusByJobId(int jobId) {
        return scheduleMapper.updateEnableStatusByJobId(jobId, 1);
    }

    @Override
    public String queryTableNameByJobId(int jobId) {
        return scheduleMapper.queryTableNameByJobId(jobId);
    }

    @Override
    public SynchJobLogInfoModel queryModelByJobId(int jobId) {
        return scheduleMapper.queryModelByJobId(jobId);
    }

}
