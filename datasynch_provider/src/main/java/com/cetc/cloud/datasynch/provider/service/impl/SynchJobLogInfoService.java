package com.cetc.cloud.datasynch.provider.service.impl;

import com.cetc.cloud.datasynch.provider.mapper.SyhchJobLogInfoMapper;
import com.cetc.cloud.datasynch.api.model.SynchJobLogInfoModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库方式同步状态记录管理
 * Created by llj on 2018/10/10.
 */
@Service("synchJobLogInfoService")
public class SynchJobLogInfoService implements com.cetc.cloud.datasynch.provider.service.SynchJobLogInfoService {

    @Mapper
    SyhchJobLogInfoMapper synchJobLogInfoMapper;

    @Override
    public SynchJobLogInfoModel queryLatestInfoByJobId(int jobId) {
        return synchJobLogInfoMapper.queryLatestLogInfoByJobId(jobId);
    }

    @Override
    public List<Integer> queryLatestPageParamsByJobID(int jobId) {
        SynchJobLogInfoModel synchJobLogInfoModel = synchJobLogInfoMapper.queryLatestPageParamsByJobID(jobId);
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(synchJobLogInfoModel.getCurrentPageNum());
        list.add(synchJobLogInfoModel.getCurrentPageSize());
        return list;
    }

    @Override
    public int add(SynchJobLogInfoModel synchJobLogInfoModel) {

        return synchJobLogInfoMapper.add(synchJobLogInfoModel);
    }
}
