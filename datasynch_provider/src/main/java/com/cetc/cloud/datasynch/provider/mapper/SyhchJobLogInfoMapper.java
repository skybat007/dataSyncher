package com.cetc.cloud.datasynch.provider.mapper;

import com.cetc.cloud.datasynch.api.model.SynchJobLogInfoModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description：同步任务执行日志查询Dao层定义
 * Created by luolinjie on 2018/10/10.
 */
@Mapper
public interface SyhchJobLogInfoMapper {

    SynchJobLogInfoModel queryLatestLogInfoByJobId(int jobId);

    int add(SynchJobLogInfoModel synchJobLogInfoModel);

    SynchJobLogInfoModel queryLatestPageParamsByJobID(int jobId);

    int deleteByJobId(int jobId);
}
