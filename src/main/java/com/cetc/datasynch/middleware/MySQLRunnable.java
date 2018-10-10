package com.cetc.datasynch.middleware;

import com.cetc.datasynch.service.impl.DbOperateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

/**
 * Description：创建执行实例
 * Created by luolinjie on 2018/10/10.
 */
public class MySQLRunnable implements Runnable {
    private String SQL;

    @Autowired
    DbOperateService dbOperateService;

    public MySQLRunnable(String SQL){
        this.SQL = SQL;
    }
    @Override
    public void run() {
        try {
            dbOperateService.oracleQuerySql(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
