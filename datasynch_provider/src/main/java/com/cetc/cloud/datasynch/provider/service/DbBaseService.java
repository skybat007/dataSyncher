package com.cetc.cloud.datasynch.provider.service;


import java.sql.SQLException;

/**
 * Description：
 * Created by luolinjie on 2018/10/19.
 */
public interface DbBaseService {

    boolean checkIfTableExists(String tbName);

    int getTableRowCounts(String tbName) throws SQLException;
}
