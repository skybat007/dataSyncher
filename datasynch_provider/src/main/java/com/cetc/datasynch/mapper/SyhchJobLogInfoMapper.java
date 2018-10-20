package com.cetc.datasynch.mapper;

import com.cetc.datasynch.api.model.SynchJobLogInfoModel;
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

    List<Integer> queryLatestPageParamsByJobID(int jobId);
}
