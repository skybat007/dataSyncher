package com.cetc.datasynch.service;

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
public class SynchJobLogInfoService {

    @Mapper
    SyhchJobLogInfoMapper synchJobLogInfoMapper;

    /**
     * todo:根据jobID查询最近一次成功请求的分页参数
     */
    public SynchJobLogInfoModel queryLatestInfoByJobId(int jobId) {
        return synchJobLogInfoMapper.queryLatestInfoByJobId(jobId);
    }

    public List<Integer> queryLatestPageParamsByJobID(int jobId) {

        return null;
    }

    public int add(SynchJobLogInfoModel synchJobLogInfoModel) {

        return synchJobLogInfoMapper.add(synchJobLogInfoModel);
    }
}
