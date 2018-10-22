package com.cetc.cloud.datasynch.provider.service.impl;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import com.cetc.cloud.datasynch.provider.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description：数据库方式的Job服务管理(数据库修改)
 * Created by luolinjie on 2018/10/10.
 */
@Service("scheduleService")
public class ScheduleService implements com.cetc.cloud.datasynch.provider.service.ScheduleService {

    @Autowired
    ScheduleMapper scheduleMapper;

    @Override
    public int addScheduleInstance(ScheduleModel scheduleModel) {
        String source = scheduleModel.getSource();
        int pageSize = scheduleModel.getPageSize();
        String tableName = scheduleModel.getTargetTableName();
        String scheduleExpression = scheduleModel.getCronExpression();
        int connType = scheduleModel.getConnType();
        if (null==source || pageSize<1 || null==tableName ||null==scheduleExpression || connType<0||connType>1){
            return -1;
        }
        int jobId = scheduleMapper.addScheduleInstance(scheduleModel);
        return jobId;
    }
    @Override
    public int deleteScheduleByJobId(int jobId) {
        return scheduleMapper.deleteJobByJobId(jobId);
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
        return scheduleMapper.updateEnableStatusByJobId(jobId, CommonInstance.DISABLED);
    }
    @Override
    public int enableStatusByJobId(int jobId) {
        return scheduleMapper.updateEnableStatusByJobId(jobId, CommonInstance.ENABLED);
    }

    @Override
    public ScheduleModel queryModelByJobId(int jobId) {
        return scheduleMapper.queryModelByJobId(jobId);
    }

}
