package com.cetc.cloud.datasynch.provider.service.impl;
/**********************************************************************
 * Copyright (c) 2018 CETC Company
 * 中电科新型智慧城市研究院有限公司版权所有
 * <p>
 * PROPRIETARY RIGHTS of CETC Company are involved in the
 * subject matter of this material. All manufacturing, reproduction, use,
 * and sales rights pertaining to this subject matter are governed by the
 * license agreement. The recipient of this software implicitly accepts
 * the terms of the license.
 * 本软件文档资料是中电科新型智慧城市研究院有限公司的资产，任何人士阅读和
 * 使用本资料必须获得相应的书面授权，承担保密责任和接受相应的法律约束。
 *************************************************************************/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Package: com.cetc.cloud.db_operate.provider.service.impl
 * Project: db_operate
 * Description: 执行SQL语句服务简单封装
 * Creator: huangzezhou
 * Create_Date: 2018/7/11 9:36
 * Updater: huangzezhou
 * Update_Date: 2018/7/11 9:36
 * Update_Description: huangzezhou 补充
 **/
@Service("dbQueryService")
public class DbQueryService {

    @Autowired
    @Qualifier("readOnlyJdbcTemplate")
    JdbcTemplate readOnlyJdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(DbQueryService.class);

    /**
     * 获取 <表名,<字段名,数据类型 > >组成的Map
     * 输出keyList：table_name,column_name,data_type
     * @return
     * @throws SQLException
     */
    public HashMap<String, HashMap> queryTableStructure() throws SQLException {
        String SQL = "SELECT table_name,column_name,data_type \n" +
                    "FROM user_tab_columns \n" +
                    "WHERE table_name in(\n" +
                    "SELECT table_name from user_all_tables \n" +
                    ")";
        List<HashMap> list = oracleQuerySql(SQL);
        HashMap<String, HashMap> resMap = new HashMap<String, HashMap>();
        for (HashMap<String, String> map : list) {
            HashMap<String, String> colName_type = new HashMap<String, String>();
            colName_type.put(map.get("COLUMN_NAME"), map.get("DATA_TYPE"));
            resMap.put(map.get("TABLE_NAME"), colName_type);
        }
        return resMap;
    }

    public List<HashMap> oracleQueryTable(String tbName) throws SQLException {
        List<HashMap> list = new ArrayList<HashMap>();
        String sql = "select * from \"" + tbName + "\"";
        logger.debug("sql: " + sql);

        SqlRowSet resultSet = readOnlyJdbcTemplate.queryForRowSet(sql);

        while (resultSet.next()) {
            SqlRowSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnNumber = resultSetMetaData.getColumnCount();
            LinkedHashMap hashMap = new LinkedHashMap();
            for (int i = 1; i <= columnNumber; ++i) {
                String colName = resultSetMetaData.getColumnName(i);
                String colType = resultSetMetaData.getColumnTypeName(i);
                String value = resultSet.getString(i);

                hashMap.put(colName, value);
            }
            list.add(hashMap);

        }
        return list;
    }


    public List<HashMap> oracleQuerySql(String sql) {
        List<HashMap> data = new ArrayList<HashMap>();
        logger.debug("\r\n-------->------------------->--------------------------->\r\n"+
                readOnlyJdbcTemplate.getDataSource().toString());
        SqlRowSet rs = readOnlyJdbcTemplate.queryForRowSet(sql);
        while (rs.next()) {
            int len = rs.getMetaData().getColumnCount();
            LinkedHashMap row = new LinkedHashMap();
            for (int i = 1; i <= len; ++i) {
                String colName = rs.getMetaData().getColumnName(i);
                String value = rs.getString(colName);
                row.put(colName, value);
            }
            data.add(row);
        }
        logger.debug("sql: " + sql);

        return data;
    }

    public boolean checkIfTableExists(String tbName) {
        String sql = "SELECT COUNT(*) FROM " + tbName;
        logger.debug("sql: " + sql);
        SqlRowSet resultSet = null;
        try {
            resultSet = readOnlyJdbcTemplate.queryForRowSet(sql);
        }catch (Exception e){
            logger.error("SQLSyntaxErrorException: ORA-00942: 表或视图 "+tbName+" 不存在");
            return false;
        }

        String count = null;
        while (resultSet.next()) {
             count = resultSet.getString(1);
        }
        if (Integer.parseInt(count)>0){
            return true;
        }
        return false;
    }
    public int getTableRowCounts(String tbName) {
        String sql = "SELECT COUNT(*) FROM " + tbName;
        logger.debug("sql: " + sql);

        SqlRowSet resultSet = readOnlyJdbcTemplate.queryForRowSet(sql);
        String count = null;
        while (resultSet.next()) {
             count = resultSet.getString(1);
        }
        return Integer.parseInt(count);
    }
}
