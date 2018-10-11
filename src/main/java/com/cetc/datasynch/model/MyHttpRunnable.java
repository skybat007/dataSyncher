package com.cetc.datasynch.model;

import com.cetc.datasynch.service.impl.DbOperateService;
import com.cetc.datasynch.service.impl.HttpOperateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

/**
 * Description：创建在线Http执行实例
 * Created by luolinjie on 2018/10/10.
 */
public class MyHttpRunnable implements Runnable {
    private String SQL;

    @Autowired
    HttpOperateService httpOperateService;

    public MyHttpRunnable(String SQL){
        this.SQL = SQL;
    }
    @Override
    public void run() {
        try {
//            httpOperateService.doHttpQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
