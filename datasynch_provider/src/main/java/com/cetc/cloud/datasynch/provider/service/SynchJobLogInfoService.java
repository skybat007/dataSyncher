package com.cetc.cloud.datasynch.provider.service;

import com.cetc.cloud.datasynch.api.model.SynchJobLogInfoModel;

import java.util.List;

/**
 * Description：数据同步日志记录服务
 * Created by luolinjie on 2018/10/20.
 */
public interface SynchJobLogInfoService {
    /**
     * 根据jobID查询最近一次成功请求的分页参数
     */
    SynchJobLogInfoModel queryLatestInfoByJobId(int jobId);

    /**
     * 查询最近一次分页参数，并以【pageNum,pageSize】返回
     * @param jobId
     * @return
     */
    List<Integer> queryLatestPageParamsByJobId(int jobId);

    /**
     * 新增一条同步日志
     * @param synchJobLogInfoModel
     * @return
     */
    int add(SynchJobLogInfoModel synchJobLogInfoModel);
}
