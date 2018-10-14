package com.cetc.datasynch.service;
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

import com.cetc.datasynch.core.util.ArrayListUtil;
import com.cetc.datasynch.core.util.JdbcUtil;
import com.cetc.datasynch.model.ScheduleModel;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.*;

/**
 * @Package: com.cetc.cloud.db_operate.provider.service.impl
 * @Project: db_operate
 * @Description: //TODO
 * @Creator: huangzezhou
 * @Create_Date: 2018/7/11 9:36
 * @Updater: huangzezhou
 * @Update_Date：2018/7/11 9:36
 * @Update_Description: huangzezhou 补充
 **/
@Service("dbOperateService")
public class DbOperateService {

    @Autowired
    DataSource dataSource;
    @Autowired
    ColumnMappingService columnMappingService;

    private static final Logger logger = LoggerFactory.getLogger(DbOperateService.class);
    private static Connection conn = null;
    private static Statement statement = null;

    private static String IP = "10.192.19.104";
    private static String orcl_username = "ZHFTYJJCPT";
    private static String orcl_password = "ToKreDi*nJ";
    private static String orcl_servicename = "orclcetc";
    String url_oracle = "jdbc:oracle:thin:@" + IP + ":1521/" + orcl_servicename;

    /**
     * 通过表名，查询该表所有数据
     *
     * @param tbName
     * @return
     */
    public List<HashMap> oracleQueryTable(String tbName) throws SQLException {
        List<HashMap> list = new ArrayList<HashMap>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtil.getConnection(url_oracle, orcl_username, orcl_password);
            statement = connection.createStatement();
            String sql = "select * from \"" + tbName + "\"";
            logger.debug("sql: " + sql);
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int columnNumber = resultSetMetaData.getColumnCount();
                LinkedHashMap hashMap = new LinkedHashMap();
                for (int i = 1; i <= columnNumber; ++i) {
                    String colName = resultSetMetaData.getColumnName(i);
                    String typeName = resultSetMetaData.getColumnTypeName(i);
                    String value = resultSet.getString(i);

                    hashMap.put(colName, value);
                }
                list.add(hashMap);
            }
        } catch (SQLException e) {
            logger.error("database connection error!\n", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return list;
    }

    /**
     * @Author huangzezhou
     * @Description //执行oracle sql语句
     * @Date 2018/7/12 15:14
     * @Param [sql]
     * @Return com.cetc.cloud.framework.api.model.CetcCloudHttpResponse<java.util.List<java.util.HashMap>>
     * @Throws
     */
    public List<HashMap> oracleQuerySql(String sql) throws SQLException {
        List<HashMap> data = new ArrayList<HashMap>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = JdbcUtil.getConnection(url_oracle, orcl_username, orcl_password);
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
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

        } catch (SQLException e) {
            logger.error("query oracle error!\nsql=" + sql, e);
            throw e;
        } finally {
            close(connection, statement, rs);
        }
        return data;
    }

    /**
     * @Author huangzezhou
     * @Description //执行sql
     * @Date 2018/7/19 16:54
     * @Param [sql]
     * @Return com.cetc.cloud.framework.api.model.CetcCloudHttpResponse<java.lang.Boolean>
     * @Throws
     */
    public Boolean oracleSql(@RequestParam("sql") String sql) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = JdbcUtil.getConnection(url_oracle, orcl_username, orcl_password);
            statement = connection.createStatement();
//            execute 方法返回一个 boolean 值，以指示第一个结果的形式。必须调用 getResultSet 或 getUpdateCount 方法来检索结果，并且必须调用 getMoreResults 移动到任何后面的结果。
            statement.execute(sql);
            logger.debug("sql: " + sql);
            return true;
        } catch (SQLException e) {
            logger.error("query oracle error!\nsql=" + sql, e);
            throw e;
        } finally {
            close(connection, statement);
            return false;
        }
    }

    public List<Integer> oracleBatchSql(@RequestParam("sql") String sql) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = JdbcUtil.getConnection(url_oracle, orcl_username, orcl_password);
            statement = connection.createStatement();
            String[] temp = sql.split("\\);");
            for (int i = 0; i < temp.length; ++i) {
                statement.addBatch(temp[i] + ")");
            }
            int[] results = statement.executeBatch();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < results.length; ++i) {
                arrayList.add(results[i]);
            }
            return arrayList;
        } catch (SQLException e) {
            logger.error("", e);
            throw e;
        } finally {
            close(connection, statement);
        }
    }

    public void oracleBatchSqlFile(String filePath) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            conn = JdbcUtil.getConnection(url_oracle, orcl_username, orcl_password);
            ScriptRunner runner = new ScriptRunner(conn);
            Resources.setCharset(Charset.forName("UTF-8")); //设置字符集,不然中文乱码插入错误
            runner.setLogWriter(null);//设置是否输出日志
            runner.runScript(Resources.getResourceAsReader(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, statement);
        }
    }

    private boolean close(Connection connection, Statement statement) throws SQLException {
        boolean result = false;
        boolean connectionResult = false;
        boolean statementResult = false;
        if (connection != null) {
            try {
                connection.close();
                connectionResult = true;
            } catch (SQLException e) {
                logger.error("connection close error!\n", e);
                throw e;
            }
        }
        if (statement != null) {
            try {
                statement.close();
                statementResult = true;
            } catch (SQLException e) {
                logger.error("statement close error!\n", e);
                throw e;
            }
        }
        if (connectionResult && statementResult)
            result = true;
        return result;
    }

    private boolean close(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        boolean result = false;
        boolean connectionResult = false;
        boolean statementResult = false;
        boolean resultSetResult = false;
        if (connection != null) {
            try {
                connection.close();
                connectionResult = true;
            } catch (SQLException e) {
                logger.error("connection close error!\n", e);
                throw e;
            }
        }
        if (statement != null) {
            try {
                statement.close();
                statementResult = true;
            } catch (SQLException e) {
                logger.error("statement close error!\n", e);
                throw e;
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
                resultSetResult = true;
            } catch (SQLException e) {
                logger.error("resultSet close error!\n", e);
                throw e;
            }
        }
        if (connectionResult && statementResult && resultSetResult)
            result = true;
        return result;
    }

    /**
     * 将请求到的数据放到数据库里
     *
     * @param queryResult
     * @param scheduleModel
     */
    public void insertIntoTargetTable(List<HashMap> queryResult, ScheduleModel scheduleModel) throws SQLException {

        //todo:根据targetTable获取对应的字段映射表
        HashMap mapping = columnMappingService.getColumnMappingByTableName(scheduleModel.getTableName());

        List keyList_SQL = new ArrayList<String>();
        List valueList_SQL = new ArrayList<String>();
        //
        for (int i = 0; i < queryResult.size(); i++) {
            HashMap valueObj = queryResult.get(i);
            Set set = valueObj.keySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                String k = (String) iterator.next();//value的keyName
                keyList_SQL.add(mapping.get(k));
                valueList_SQL.add(valueObj.get(k));
            }

            conn = JdbcUtil.getConnection(url_oracle, orcl_username, orcl_password);
            statement = conn.createStatement();
            String sql = "INSERT INTO " + scheduleModel.getTableName()+
                    ArrayListUtil.toStringWithoutBracket(keyList_SQL)+
                    " VALUES ";


            logger.debug("sql: " + sql);
            int count = statement.executeUpdate(sql);
        }
    }


}
