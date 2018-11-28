package com.cetc.cloud.datasynch.provider.service.impl;

import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
public class AlarmMsgService {

    Logger logger = LoggerFactory.getLogger(AlarmMsgService.class);

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate primaryJdbcTemplate;

    public void pushAlaramInfo(final String targetTableName, final HashMap valueObj) {
        class AlarmThread implements Runnable{
            @Override
            public void run() {
                //1.判断属于哪张表,获取预警信息组成规则，并组成SQL语句
                String sql = getSQLByTableName(targetTableName,valueObj);
                //2.执行SQL语句
                if (!"".equals(sql)) {
                    int insertRes = primaryJdbcTemplate.update(sql);
                    if (insertRes > 0) {
                        logger.info("AlramInfo:" + sql);
                    }
                }
            }
        }

        //通过多线程实现数据插入，避免阻塞队列
        ThreadPoolExecutor executor = new ThreadPoolExecutor(8,8,100, TimeUnit.MILLISECONDS,new ArrayBlockingQueue(8));
        executor.execute(new Thread(new AlarmThread()));
    }

    private String getSQLByTableName(String targetTableName, HashMap valueObj) {
        String SQL = "";

        if ("BLK_SANXIAO_PLACE".equals(targetTableName)){
//            Object o = valueObj.get();
        }else if ("QAJJ_INSRECORD_V".equals(targetTableName)){

        }
        return null;
    }
}
