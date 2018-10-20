package com.cetc.datasynch.service;

import com.cetc.datasynch.api.model.ScheduleModel;

import java.util.HashMap;
import java.util.List;

/**
 * Description：HTTP发起服务
 * Created by luolinjie on 2018/10/19.
 */
public interface HttpOperateService {
    /**
     * 通过HTTP协议请求对应的URL获取数据
     * @param model
     * @param pageNum
     * @return
     */
    List<HashMap> doHttpQuery(ScheduleModel model, int pageNum);
}
