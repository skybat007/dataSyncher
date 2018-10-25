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

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.provider.core.util.ListUtil;
import com.cetc.cloud.datasynch.provider.tools.DbTools;
import jdk.nashorn.internal.runtime.ECMAException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.*;

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
@Service("dbOperateService")
public class DbOperateService implements com.cetc.cloud.datasynch.provider.service.DbOperateService {

    @Autowired
    @Qualifier("primaryDataSource")
    DataSource dataSource;

    @Autowired
    com.cetc.cloud.datasynch.provider.service.impl.ColumnMappingService columnMappingService;

    private final Logger logger = LoggerFactory.getLogger(DbOperateService.class);
    private Connection conn = null;
    private Statement statement = null;

    private final String orclUsername = "ZHFT123";

    private final String IP = "10.192.19.108";
    private final String orclPassword = "123456";
    private final String orclServicename = "orcl";
    private final String urlOracle = "jdbc:oracle:thin:@" + IP + ":1521/" + orclServicename;

    /**
     * 获取 <表名,List<HashMap></><字段名,数据类型 > >组成的Map
     * 输出keyList：table_name,column_name,data_type
     *
     * @return
     * @throws SQLException
     */
    @Override
    public HashMap<String, HashMap> queryTableStructure() throws SQLException {
        String SQL = "SELECT table_name,column_name,data_type \n" +
                "FROM user_tab_columns \n" +
                "WHERE table_name in(\n" +
                "SELECT table_name from user_all_tables \n" +
                ")";
        List<HashMap> list = oracleQuerySql(SQL);
        // 结果集 HashMap的Key是不能重复的，因此，每次put相同的key，都会将其value覆盖
        HashMap<String, HashMap> resMap = new HashMap<String, HashMap>();
        // 筛选存储当前业务库表的集合
        HashSet<String> tableNameSet = new HashSet<String>();

        for (HashMap<String, String> map : list) {
            String tableName = map.get("TABLE_NAME");
            String column_name = map.get("COLUMN_NAME");
            String data_type = map.get("DATA_TYPE");
            if (tableNameSet.contains(tableName)){
                HashMap hashMap = resMap.get(tableName);
                hashMap.put(column_name,data_type);
            }else {
                tableNameSet.add(tableName);//添加到表名集合中，供下次检索
                HashMap<String,String> newMap = new HashMap();
                newMap.put(column_name, data_type);
                resMap.put(tableName,newMap);//第一次添加一条映射关系
            }
        }

        return resMap;
    }

    @Override
    public List<HashMap> oracleQueryTable(String tbName) throws SQLException {
        List<HashMap> list = new ArrayList<HashMap>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
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
                    String colType = resultSetMetaData.getColumnTypeName(i);
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

    @Override
    public List<HashMap> oracleQuerySql(String sql) throws SQLException {
        List<HashMap> data = new ArrayList<HashMap>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = dataSource.getConnection();
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

    @Override
    public List<HashMap> oracleQuerySql(String sql, String urlOracle, String orclUsername, String orclPassword) throws SQLException {
        List<HashMap> data = new ArrayList<HashMap>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = dataSource.getConnection();
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

    @Override
    public Boolean oracleSql(@RequestParam("sql") String sql) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
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

    @Override
    public List<Integer> oracleBatchSql(@RequestParam("sql") List<String> sql) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            for (int i = 0; i < sql.size(); ++i) {
                statement.addBatch(sql.get(i));
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

    @Override
    public void oracleBatchSqlFile(String filePath) throws SQLException, IOException {
        Connection connection = null;
        Statement statement = null;
        conn = dataSource.getConnection();
        ScriptRunner runner = new ScriptRunner(conn);

        runner.setDelimiter(";");//每条命令间的分隔符
        Resources.setCharset(Charset.forName("UTF-8")); //设置字符集,不然中文乱码插入错误
        runner.setLogWriter(null);//设置是否输出日志

        Reader resourceReader = Resources.getResourceAsReader(filePath);
        runner.runScript(resourceReader);
//        runner.runScript(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
        close(connection, statement);
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

    @Override
    public List<Integer> insertIntoTargetTable(List<HashMap> queryResult, ScheduleModel scheduleModel) throws SQLException {

        //获取字段类型映射map
        HashMap<String, HashMap> tbStructureMap = queryTableStructure();

        //根据targetTable获取对应的字段映射表
        HashMap mapping = columnMappingService.getColumnMappingByTargetTableName(scheduleModel.getTargetTableName());
        int successCounter = 0;
        int failCounter = 0;
        List keyList_SQL = new ArrayList<String>();
        List valueList_SQL = new ArrayList<String>();
        //遍历结果集，并根据结果集中的key，将值通过映射表映射到数据库中
        for (int i = 0; i < queryResult.size(); i++) {
            HashMap valueObj = queryResult.get(i);
            Set set = valueObj.keySet();
            Iterator iterator = set.iterator();

            while (iterator.hasNext()) {
                String k = (String) iterator.next();//value的keyName
                keyList_SQL.add(mapping.get(k));
                valueList_SQL.add(valueObj.get(k));
            }

            conn = dataSource.getConnection();
            statement = conn.createStatement();
            String tableName = scheduleModel.getTargetTableName();
            String tableValues = getTableValues(tableName, keyList_SQL, valueList_SQL, tbStructureMap);
            if (null == tableValues) {
                continue;
            }
            String sql = "INSERT INTO \"" + tableName + "\"(" +
                    //组装key列表
                    ListUtil.getSQLColumnsListWithQuotes(keyList_SQL) + ")" +
                    //组装value列表
                    " VALUES (" + tableValues + ")";

            logger.debug("sql: " + sql);
            int count = statement.executeUpdate(sql);
            if (count > 0) {
                logger.info("insert successful！");
                successCounter++;
            } else {
                logger.info("insert failed！");
                failCounter++;
            }
        }

        List<Integer> resList = new ArrayList<Integer>();
        resList.add(successCounter);
        resList.add(failCounter);
        return resList;
    }

    /**
     * 根据表名、字段名称、字段类型组装SQL插入值
     *
     * @param tableName      表名
     * @param keyList_SQL    目标表的字段列表
     * @param valueList_sql  值列表
     * @param tbStructureMap 字段名称-字段类型 映射表
     * @return 组装后的SQL值部分
     */
    private String getTableValues(String tableName, List keyList_SQL, List<String> valueList_sql, HashMap<String, HashMap> tbStructureMap) {

        List<String> valueList = new ArrayList<String>();
        for (int i = 0; i < keyList_SQL.size(); i++) {
            try {
                //通过当前key获取对应的字段类型
                String column_type = (String) tbStructureMap.get(tableName).get(keyList_SQL.get(i));
                //根据字段类型判断输出值的形式（加""或者to_date()）, 拼接至值列表中
                String decoratedColumn = DbTools.getDecoratedColumn(column_type, valueList.get(i));
                if (null == decoratedColumn) {
                    return null;
                } else {
                    valueList.add(decoratedColumn);
                }
            } catch (Exception e) {
                logger.error("DbOperateService.getTableValues()方法中的\"tbStructureMap\"没有找到表\"" + tableName + "\"对应字段\"" + keyList_SQL.get(i) + "\"对应的字段类型，\r\n请在对应的表中补全后重试！！！");
            }


        }
        return ListUtil.toStringWithoutBracket(valueList);
    }
}
