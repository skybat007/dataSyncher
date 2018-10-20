package com.cetc.datasynch.controller;

import com.alibaba.fastjson.JSONObject;
import com.cetc.datasynch.api.service.InitialRemoteService;
import com.cetc.datasynch.service.DbOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.SQLException;

/**
 * Description：初始化控制器
 * Created by luolinjie on 2018/10/20.
 */
public class InitialController implements InitialRemoteService {
    @Autowired
    DbOperateService dbOperateService;

    /**
     * 初始化建表SQL
     */
    @Override
    @RequestMapping(value = "/schedule/init", produces = "application/json", method = RequestMethod.GET)
    public String initSQL() throws SQLException {
        JSONObject res = new JSONObject();
        try {
            dbOperateService.oracleBatchSqlFile("/dataSynch.sql");
        }catch (Exception e){
            e.printStackTrace();
            res.put("fail","An error occured when executing batch SQL File:dataSynch.sql");
            return res.toJSONString();
        }
        res.put("success","Successfully executed batch SQL File:dataSynch.sql");
        return res.toJSONString();
    }
}
