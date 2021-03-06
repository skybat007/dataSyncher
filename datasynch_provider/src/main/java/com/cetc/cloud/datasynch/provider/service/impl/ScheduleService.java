package com.cetc.cloud.datasynch.provider.service.impl;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import com.cetc.cloud.datasynch.provider.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：数据库方式的Job服务管理(数据库修改)
 * Created by luolinjie on 2018/10/10.
 */
@Service("scheduleService")
public class ScheduleService {

    @Autowired
    ScheduleMapper scheduleMapper;

    public int addScheduleInstance(ScheduleModel scheduleModel) {
        String source = scheduleModel.getSource();
        int pageSize = scheduleModel.getPageSize();
        String tableName = scheduleModel.getTargetTableName();
        String scheduleExpression = scheduleModel.getCronExpression();
        int isPagingQuery = scheduleModel.getIsPagingQuery();
        int srcDs = scheduleModel.getSrcDs();

        int connType = scheduleModel.getConnType();
        if (connType==CommonInstance.TYPE_DB) {
            if (null == source || pageSize < 1 || null == tableName || null == scheduleExpression ||
                    isPagingQuery < 0 || isPagingQuery > 1 || srcDs < 0) {
                return -1;
            }
        }else if (connType==CommonInstance.TYPE_INTERFACE){
            if (null == source || pageSize < 1 || null == tableName || null == scheduleExpression ||
                    isPagingQuery < 0 || isPagingQuery > 1 ) {
                return -1;
            }
        }
        int jobId = scheduleMapper.addScheduleInstance(scheduleModel);
        if (jobId > 0) {
            return scheduleModel.getId();
        } else {
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

    public int alterJobStatusByJobId(int jobId, int statusToChange) {
        //获取运行状态
        int status = scheduleMapper.getStatusByJobId(jobId);
        if (statusToChange == status) {
            return 1;
        }
        //修改运行状态
        if (statusToChange == CommonInstance.JOB_ENABLED || statusToChange == CommonInstance.JOB_DISABLED) {
            return scheduleMapper.updateEnableStatusByJobId(jobId, statusToChange);
        } else {
            return -1;
        }
    }

    public int enableStatusByJobId(int jobId) {
        //获取运行状态
        int status = scheduleMapper.getStatusByJobId(jobId);
        if (CommonInstance.JOB_ENABLED == status) {
            return 2;
        } else {
            return scheduleMapper.updateEnableStatusByJobId(jobId, CommonInstance.JOB_ENABLED);
        }
    }


    public ScheduleModel queryModelByJobId(int jobId) {
        return scheduleMapper.queryModelByJobId(jobId);
    }

    public ScheduleModel queryModelByTableName(String tableName) {
        return scheduleMapper.queryModelByTableName(tableName);
    }

    public List<Integer> queryEnabledJobIdList() {
        List<ScheduleModel> scheduleModels = queryScheduleJobList();
        ArrayList<Integer> jobList = new ArrayList<Integer>();
        for (ScheduleModel model : scheduleModels) {
            int isEnabled = model.getIsEnabled();
            if (isEnabled == 1) {
                jobList.add(model.getId());
            }
        }
        return jobList;
    }
}
