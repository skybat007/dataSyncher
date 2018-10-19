package com.cetc.datasynch.service.impl;

import com.cetc.datasynch.mapper.SyhchJobLogInfoMapper;
import com.cetc.datasynch.model.SynchJobLogInfoModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据库方式同步状态记录管理
 * Created by llj on 2018/10/10.
 */
@Service
public class SynchJobLogInfoService implements com.cetc.datasynch.service.SynchJobLogInfoService {

    @Mapper
    SyhchJobLogInfoMapper synchJobLogInfoMapper;

    @Override
    public SynchJobLogInfoModel queryLatestInfoByJobId(int jobId) {
        return synchJobLogInfoMapper.queryLatestLogInfoByJobId(jobId);
    }

    @Override
    public List<Integer> queryLatestPageParamsByJobID(int jobId) {

        return synchJobLogInfoMapper.queryLatestPageParamsByJobID(jobId);
    }

    @Override
    public int add(SynchJobLogInfoModel synchJobLogInfoModel) {

        return synchJobLogInfoMapper.add(synchJobLogInfoModel);
    }
}
