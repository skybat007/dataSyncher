package com.cetc.cloud.datasynch.provider.service.impl;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import com.cetc.cloud.datasynch.provider.mapper.input.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description：数据库方式的Job服务管理(数据库修改)
 * Created by luolinjie on 2018/10/10.
 */
@Service("scheduleService")
public class ScheduleService{

    @Autowired
    ScheduleMapper scheduleMapper;

    
    public int addScheduleInstance(ScheduleModel scheduleModel) {
        String source = scheduleModel.getSource();
        int pageSize = scheduleModel.getPageSize();
        String tableName = scheduleModel.getTargetTableName();
        String scheduleExpression = scheduleModel.getCronExpression();
        int isPagingQuery = scheduleModel.getIsPagingQuery();

        int connType = scheduleModel.getConnType();
        if (null==source || pageSize<1 || null==tableName ||null==scheduleExpression || connType<0||connType>1 ||
                isPagingQuery<0 || isPagingQuery>1){
            return -1;
        }
        int jobId = scheduleMapper.addScheduleInstance(scheduleModel);
        if (jobId>0) {
            return scheduleModel.getId();
        }else {
            return -1;
        }
    }
    
    public int deleteScheduleByJobId(int jobId) {
        return scheduleMapper.deleteJobByJobId(jobId);
    }

    public List<ScheduleModel> queryScheduleJobList() {
        return scheduleMapper.queryScheduleJobList();
    }

    
    public int updateCronByJobId(int jobId, String cron) {
        return scheduleMapper.updateCronByJobId(jobId, cron);
    }
    
    public int disableStatusByJobId(int jobId) {
        return scheduleMapper.updateEnableStatusByJobId(jobId, CommonInstance.DISABLED);
    }
    
    public int enableStatusByJobId(int jobId) {
        //获取运行状态
        int status = scheduleMapper.getStatusByJobId(jobId);
        if (CommonInstance.ENABLED == status) {
            return 1;
        }else{
            return scheduleMapper.updateEnableStatusByJobId(jobId, CommonInstance.ENABLED);
        }
    }

    
    public ScheduleModel queryModelByJobId(int jobId) {
        return scheduleMapper.queryModelByJobId(jobId);
    }

}
