package com.cetc.cloud.datasynch.provider.service.impl;

import com.cetc.cloud.datasynch.api.model.DddOuterURLsModel;
import com.cetc.cloud.datasynch.provider.mapper.OuterUrlsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.service.impl
 * projectName:   dataSyncher
 * Description:   提供一系列“工具性”功能套件，供SingleJob调用
 * Creator:     by luolinjie
 * Create_Date: 2018/12/14 17:32
 * Updater:     by luolinjie
 * Update_Date: 2018/12/14
 * Update_Description: luolinjie 补充
 **/
@Service
@Slf4j
public class OuterUrlsService {
    @Autowired
    OuterUrlsMapper outerUrlsMapper;
    @Autowired
    HttpOperateService httpOperateService;


    public DddOuterURLsModel getModelByTableName(String tableName) {
        return outerUrlsMapper.getModelByTableName(tableName);
    }

    public DddOuterURLsModel getModelByObjectId(int objectId) {
        return outerUrlsMapper.getModelByObjectId(objectId);
    }
}
