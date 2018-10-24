package com.cetc.cloud.datasynch.provider.controller;

import com.alibaba.fastjson.JSONObject;
import com.cetc.cloud.datasynch.api.service.InitialRemoteService;
import com.cetc.cloud.datasynch.provider.service.impl.DbOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * Description：初始化控制器
 * Created by luolinjie on 2018/10/20.
 */
@RestController
public class InitialController implements InitialRemoteService {
    @Autowired
    DbOperateService dbOperateService;

    /**
     * 初始化建表SQL
     */
    @Override
    public String executeDBInit() throws SQLException {
        JSONObject res = new JSONObject();
        try {
            dbOperateService.oracleBatchSqlFile("/initDataSynch.sql");
        }catch (Exception e){
            e.printStackTrace();
            res.put("fail","An error occured when executing batch SQL File:dataSynch.sql");
            return res.toJSONString();
        }
        res.put("success","Successfully executed batch SQL File:dataSynch.sql");
        return res.toJSONString();
    }
}
