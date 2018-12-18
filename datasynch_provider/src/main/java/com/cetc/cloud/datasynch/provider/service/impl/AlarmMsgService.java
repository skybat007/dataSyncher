package com.cetc.cloud.datasynch.provider.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.service.impl
 * projectName:   dataSyncher
 * Description:   预警事件推送服务-多线程模式
 * Creator:     by luolinjie
 * Create_Date: 2018/11/23 17:49
 * Updater:     by luolinjie
 * Update_Date: 2018/11/23
 * Update_Description: luolinjie 补充
 **/
@Service
@Slf4j
public class AlarmMsgService {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate primaryJdbcTemplate;

    public void pushAlaramInfo(final String targetTableName, final HashMap valueObj) {
    }

    private String getSQLByTableName(String targetTableName, HashMap valueObj) {
        String SQL = "";

        if ("BLK_SANXIAO_PLACE".equals(targetTableName)){
//            Object o = valueObj.get();
        }else if ("QAJJ_INSRECORD_V".equals(targetTableName)){

        }
        return SQL;
    }
}
