package com.cetc.datasynch.service;

import com.cetc.datasynch.mapper.ScheduleMapper;
import com.cetc.datasynch.model.ScheduleModel;
import com.cetc.datasynch.model.SynchJobLogInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description：数据库方式的Job服务管理(数据库修改)
 * Created by luolinjie on 2018/10/10.
 */
@Service
public class ScheduleService {

    @Autowired
    ScheduleMapper scheduleMapper;

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
    public int deleteScheduleByJobId(int jobID) {
        return scheduleMapper.deleteJobByJobId(jobID);
    }

    public List<ScheduleModel> queryScheduleJobList() {
        return scheduleMapper.queryScheduleJobList();
    }

    public int updateCronByJobId(int jobId, String cron) {
        return scheduleMapper.updateCronByJobId(jobId, cron);
    }
    public int disableStatusByJobId(int jobId) {
        return scheduleMapper.updateEnableStatusByJobId(jobId, 0);
    }
    public int enableStatusByJobId(int jobId) {
        return scheduleMapper.updateEnableStatusByJobId(jobId, 1);
    }

    public String queryTableNameByJobId(int jobId) {
        return scheduleMapper.queryTableNameByJobId(jobId);
    }

    public SynchJobLogInfoModel queryModelByJobId(int jobId) {
        return scheduleMapper.queryModelByJobId(jobId);
    }

}