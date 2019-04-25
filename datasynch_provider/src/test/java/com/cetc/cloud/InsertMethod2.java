package com.cetc.cloud;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import com.cetc.cloud.datasynch.provider.controller.SequenceManagerController;
import com.cetc.cloud.datasynch.provider.tools.DbTools;
import com.cetc.cloud.datasynch.provider.util.ListUtil;
import com.cetc.cloud.datasynch.provider.service.impl.ColumnMappingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Administrator on 2019/1/8.
 */
@Slf4j
public class InsertMethod2 {
    //    @Qualifier("jdbcTemplate")
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ColumnMappingService columnMappingService;

    @Value("${spring.datasource.dynamic.datasource.master.username}")
    private String orclUsername;

    @Autowired
    SequenceManagerController sequenceManagerController;

    private Properties tbSeqMappingProp;

    /**
     * 获取 <表名,List<HashMap></><字段名,数据类型 > >组成的Map
     * 输出keyList：table_name,column_name,data_type
     *
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
        // 结果集 HashMap的Key是不能重复的，因此，每次put相同的key，都会将其value覆盖
        HashMap<String, HashMap> resMap = new HashMap<String, HashMap>();
        // 筛选存储当前业务库表的集合
        HashSet<String> tableNameSet = new HashSet<String>();

        for (HashMap<String, String> map : list) {
            String tableName = map.get("TABLE_NAME");
            String column_name = map.get("COLUMN_NAME");
            String data_type = map.get("DATA_TYPE");
            if (tableNameSet.contains(tableName)) {
                HashMap hashMap = resMap.get(tableName);
                hashMap.put(column_name, data_type);
            } else {
                tableNameSet.add(tableName);//添加到表名集合中，供下次检索
                HashMap<String, String> newMap = new HashMap();
                newMap.put(column_name, data_type);
                resMap.put(tableName, newMap);//第一次添加一条映射关系
            }
        }

        return resMap;
    }

    /**
     * 获取 <表名,List<HashMap></><字段名,数据类型 > >组成的Map
     * 输出keyList：table_name,column_name,data_type
     *
     * @return
     * @throws SQLException
     */
    public HashMap<String, HashMap> queryTableStructureByTableName(String tableName) {

        String SQL = "SELECT table_name,column_name,data_type \n" +
                "FROM user_tab_columns \n" +
                "WHERE table_name ='" + tableName + "'";
        List<HashMap> list = null;
        try {
            list = oracleQuerySql(SQL);
        } catch (Exception e) {
            log.error("SQL Error：" + SQL);
            e.printStackTrace();
        }
        // 结果集 HashMap的Key是不能重复的，因此，每次put相同的key，都会将其value覆盖
        HashMap<String, HashMap> resMap = new HashMap<String, HashMap>();
        // 筛选存储当前业务库表的集合
        HashSet<String> tableNameSet = new HashSet<String>();

        for (HashMap<String, String> map : list) {
            String tableName1 = map.get("TABLE_NAME");
            String column_name = map.get("COLUMN_NAME");
            String data_type = map.get("DATA_TYPE");
            if (tableNameSet.contains(tableName1)) {
                HashMap hashMap = resMap.get(tableName1);
                hashMap.put(column_name, data_type);
            } else {
                tableNameSet.add(tableName1);//添加到表名集合中，供下次检索
                HashMap<String, String> newMap = new HashMap();
                newMap.put(column_name, data_type);
                resMap.put(tableName1, newMap);//第一次添加一条映射关系
            }
        }

        return resMap;
    }

    public HashMap<String, String> queryTableStructureByTableName2(String tableName) throws SQLException {

        String SQL = "SELECT column_name,data_type \n" +
                "FROM user_tab_columns \n" +
                "WHERE table_name ='" + tableName + "'";
        List<HashMap> list = oracleQuerySql(SQL);
        // 结果集 HashMap的Key是不能重复的，因此，每次put相同的key，都会将其value覆盖
        HashMap<String, String> resMap = new HashMap<String, String>();

        for (HashMap<String, String> map : list) {
            String column_name = map.get("COLUMN_NAME");
            String data_type = map.get("DATA_TYPE");
            resMap.put(column_name, data_type);
        }

        return resMap;
    }

    public List<HashMap> oracleQueryTable(String tbName) throws SQLException {
        List<HashMap> list = new ArrayList<HashMap>();
        SqlRowSet resultSet = null;

        try {
            String sql = "select * from \"" + tbName + "\"";
            log.debug("sql: " + sql);
            resultSet = jdbcTemplate.queryForRowSet(sql);
            while (resultSet.next()) {
                ResultSetMetaData resultSetMetaData = (ResultSetMetaData) resultSet.getMetaData();
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
            log.error("database connection error!\n", e);
        }
        return list;
    }

    /**
     * 注意：只能指定返回2个字段
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    @DS("master")
    public List<List> oracleQueryList_2member(String sql) throws SQLException {
        List<List> data = new ArrayList<List>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        while (rs.next()) {
            ArrayList<String> values = new ArrayList<>();
            try {
                String value1 = rs.getString(1);
                values.add(value1);
                String value2 = rs.getString(2);
                values.add(value2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            data.add(values);
        }
        log.debug("sql: " + sql);

        return data;
    }

    public List<HashMap> oracleQuerySql(String sql) throws SQLException {
        List<HashMap> data = new ArrayList<HashMap>();
        SqlRowSet rs = null;
        rs = jdbcTemplate.queryForRowSet(sql);
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
        log.debug("sql: " + sql);

        return data;
    }

    /**
     * 注意：只能指定返回一个字段
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public List<String> oracleQueryList(String sql) throws SQLException {
        List<String> data = new ArrayList<String>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        while (rs.next()) {
            String value = rs.getString(1);
            data.add(value);
        }
        log.debug("sql: " + sql);

        return data;
    }

    public List<HashMap> oracleQuerySql(String sql, String urlOracle, String orclUsername, String orclPassword) throws SQLException {
        List<HashMap> data = new ArrayList<HashMap>();
        SqlRowSet rs = null;
        rs = jdbcTemplate.queryForRowSet(sql);
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
        log.debug("sql: " + sql);

        return data;
    }

    public boolean oracleExecuteSql(String sql) throws SQLException {
//     execute 方法返回一个 boolean 值，以指示第一个结果的形式。
        jdbcTemplate.execute(sql);
        log.debug("sql: " + sql);
        return true;
    }

    public int oracleUpdateSql(String sql) throws SQLException {
        int update = jdbcTemplate.update(sql);
        log.debug("sql: " + sql);
        return update;
    }

//    /**
//     * 批量执行Insert语句
//     * @param sql
//     * @return
//     * @throws SQLException
//     */
//    public int oracleBatchInsertSql(String sql) throws SQLException {
//        int update = jdbcTemplate.update(sql);
//        log.debug("sql: " + sql);
//        return update;
//    }

    /**
     * 批量执行SQL语句
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public int[] oracleBatchSql(String[] sql) throws SQLException {
        int[] ints = jdbcTemplate.batchUpdate(sql);
        int successCount = 0;
        int failCount = 0;
        for (int i : ints) {
            if (i == 1) {
                successCount++;
            } else {
                failCount++;
            }
        }
//        log.info("batch insert res: " + ints);
        log.debug("sql: " + sql);
        return new int[]{successCount, failCount};
    }

    /**
     * 批量执行SQL语句
     *
     * @param sqls
     * @return
     * @throws SQLException
     */
    public int[] oracleBatchSql(List<String> sqls) {
        if (sqls.size() == 0) {
            log.error("Empty sqls:oracleBatchSql");
            return new int[]{0, 0};
        }
        int[] ints = jdbcTemplate.batchUpdate(ListUtil.toStringArray(sqls));
        int successCount = 0;
        int failCount = 0;
        for (int i : ints) {
            if (i == 1) {
                successCount++;
            } else {
                failCount++;
            }
        }
        log.debug("sql: " + sqls);
        return new int[]{successCount, failCount};
    }


    public void oracleBatchSqlFile(String filePath) throws SQLException, IOException {
        ScriptRunner runner = new ScriptRunner(jdbcTemplate.getDataSource().getConnection());

        runner.setDelimiter(";");//每条命令间的分隔符
        Resources.setCharset(Charset.forName("UTF-8")); //设置字符集,不然中文乱码插入错误
        runner.setLogWriter(null);//设置是否输出日志

        Reader resourceReader = Resources.getResourceAsReader(filePath);
        runner.runScript(resourceReader);
    }


    public List<Integer> insertIntoTargetTable(List<HashMap> queryResult, ScheduleModel scheduleModel) throws SQLException {

        if (tbSeqMappingProp == null) {
            try {
                tbSeqMappingProp = sequenceManagerController.loadMappingExcel();
            } catch (Exception e) {
                log.error("");
            }
        }
        List<Integer> resList = new ArrayList<>();
        //获取 "字段-字段类型" 映射map
        HashMap<String, HashMap> tbStructureMap = queryTableStructureByTableName(scheduleModel.getTargetTableName());
//
//        //---对clob类型做单独处理
//        HashMap hashMap = tbStructureMap.get(scheduleModel.getTargetTableName());
//        Collection values = hashMap.values();
//        if (values.contains("CLOB")) {
//            final LobHandler lobHandler = new DefaultLobHandler(); //定义一个lob的操作类
//            Object update_result = jdbcTemplate.execute(sql, new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
//                @Override
//                protected void setValues(PreparedStatement pstmt, LobCreator lobCreator) throws SQLException, DataAccessException {
//                    pstmt.setLong(1, id);
//                    lobCreator.setClobAsString(pstmt, 2, clob);//大文本CLOB字段，result_log为字符串
//                }
//            });
//            jdbcTemplate.execute(sql, new Abs)
//
//        }

        //根据targetTable获取对应的字段映射表(需要过滤掉为null的字段)
        HashMap mapping = columnMappingService.getColumnMappingByTargetTableName(scheduleModel.getTargetTableName());
        int successCounter = 0;
        int failCounter = 0;
        List keyList_SQL = new ArrayList<String>();

        if (mapping.size() == 0) {
            resList.add(CommonInstance.FAIL);
            resList.add(successCounter);
            resList.add(failCounter);
            return resList;
        }

        /**要让映射过程可控，就需要以定义的mapping表为参考标准拼接SQL*/
        //遍历mapping，并根据mapping结果集中的key，将值通过映射表映射到数据库中
        Set set = mapping.keySet();//这里以mapping的keyset作为参考表，即使源表中多余的字段，也不会因mapping中没有对应的字段映射而报错
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            String k = (String) iterator.next();//mapping的keyName
            keyList_SQL.add(mapping.get(k));
        }

        for (int i = 0; i < queryResult.size(); i++) {
            List valueList_SQL = new ArrayList<String>();
            HashMap valueObj = queryResult.get(i);
            Set set1 = mapping.keySet();//这里以mapping的keyset作为参考表，即使源表中多余的字段，也不会因mapping中没有对应的字段映射而报错
            Iterator iterator1 = set1.iterator();
            while (iterator1.hasNext()) {
                String k = (String) iterator1.next();//mapping的keyName
                valueList_SQL.add(valueObj.get(k));
            }


            String targetTableName = scheduleModel.getTargetTableName();
            //根据表名、字段名称集合,与表结构 获取 组装后的SQL值String
            String tableValues = getTableValuesSQLString(targetTableName, keyList_SQL, valueList_SQL, tbStructureMap);
            //异常情况处理：如果不能在业务库中找到这张目标表对应的表结构,则放弃执行该任务
            if (null == tableValues || "".equals(tableValues)) {
                log.error("cannot find target table:\"" + targetTableName + "\" in targetDB");
                resList.add(CommonInstance.ERROR);
                resList.add(0);
                resList.add(0);
                return resList;
            }

            //获取序列nextval值
            SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT " + tbSeqMappingProp.getProperty(targetTableName) + ".NEXTVAL FROM DUAL");
            int nextValue = 0;
            while (sqlRowSet.next()) {
                nextValue = sqlRowSet.getInt(1);
            }

            String sql = "INSERT INTO \"" + targetTableName + "\"(" +
                    CommonInstance.GLOBAL_COLNAME_INCRE_ID + "," +
                    //组装key列表
                    ListUtil.getSQLColumnsListWithQuotes(keyList_SQL) + ")" +
                    //组装value列表
                    " VALUES (" + nextValue + "," + tableValues + ")";

            log.debug("sql: " + sql);
            int count = jdbcTemplate.update(sql);
            if (count > 0) {
                log.debug("insert successful！");
                successCounter++;
            } else {
                log.debug("insert failed！");
                failCounter++;
            }
        }


        resList.add(CommonInstance.SUCCESS);
        resList.add(successCounter);
        resList.add(failCounter);
        return resList;
    }

    /**
     * 该函数主要用于OuterJob
     *
     * @param queryResult
     * @param tableName
     * @return
     * @throws SQLException
     */
    public List<Integer> insertIntoTargetTableByTableName(List<HashMap> queryResult, String tableName) throws SQLException {

        if (tbSeqMappingProp == null) {
            try {
                tbSeqMappingProp = sequenceManagerController.loadMappingExcel();
            } catch (Exception e) {
                log.error("");
            }
        }
        List<Integer> resList = new ArrayList<Integer>();
        //获取 "字段-字段类型" 映射map
        HashMap<String, HashMap> tbStructureMap = queryTableStructureByTableName(tableName);

        //根据targetTable获取对应的字段映射表(需要过滤掉为null的字段)
        HashMap mapping = columnMappingService.getColumnMappingByTargetTableName(tableName);
        int successCounter = 0;
        int failCounter = 0;
        List keyList_SQL = new ArrayList<String>();

        if (mapping.size() == 0) {
            log.error("Empty mapping!tableName:" + tableName);
            resList.add(CommonInstance.FAIL);
            resList.add(successCounter);
            resList.add(failCounter);
            return resList;
        }

        /**要让映射过程可控，就需要以定义的mapping表为参考标准拼接SQL*/
        //遍历mapping，并根据mapping结果集中的key，将值通过映射表映射到数据库中
        Set set = mapping.keySet();//这里以mapping的keyset作为参考表，即使源表中多余的字段，也不会因mapping中没有对应的字段映射而报错
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            String k = (String) iterator.next();//mapping的keyName
            keyList_SQL.add(mapping.get(k));
        }

        for (int i = 0; i < queryResult.size(); i++) {
            List valueList_SQL = new ArrayList<String>();
            HashMap valueObj = queryResult.get(i);
            Set set1 = mapping.keySet();//这里以mapping的keyset作为参考表，即使源表中多余的字段，也不会因mapping中没有对应的字段映射而报错
            Iterator iterator1 = set1.iterator();
            while (iterator1.hasNext()) {
                String k = (String) iterator1.next();//mapping的keyName
                valueList_SQL.add(valueObj.get(k));
            }


            String targetTableName = tableName;
            //根据表名、字段名称集合,与表结构 获取 组装后的SQL值String
            String tableValues = getTableValuesSQLString(targetTableName, keyList_SQL, valueList_SQL, tbStructureMap);
            //异常情况处理：如果不能在业务库中找到这张目标表对应的表结构,则放弃执行该任务
            if (null == tableValues || "".equals(tableValues)) {
                log.error("cannot find target table:\"" + targetTableName + "\" in targetDB");
                resList.add(CommonInstance.ERROR);
                resList.add(0);
                resList.add(0);
                return resList;
            }

            //获取序列nextval值
            SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT " + tbSeqMappingProp.getProperty(targetTableName) + ".NEXTVAL FROM DUAL");
            int nextValue = 0;
            while (sqlRowSet.next()) {
                nextValue = sqlRowSet.getInt(1);
            }

            String sql = "INSERT INTO \"" + targetTableName + "\"(" +
                    CommonInstance.GLOBAL_COLNAME_INCRE_ID + "," +
                    //组装key列表
                    ListUtil.getSQLColumnsListWithQuotes(keyList_SQL) + ")" +
                    //组装value列表
                    " VALUES (" + nextValue + "," + tableValues + ")";

            log.debug("sql: " + sql);
            int count = jdbcTemplate.update(sql);
            if (count > 0) {
//                alarmMsgService.pushAlaramInfo(targetTableName, valueObj);
                log.debug("insert successful！");
                successCounter++;
            } else {
                log.debug("insert failed！");
                failCounter++;
            }
        }


        resList.add(CommonInstance.SUCCESS);
        resList.add(successCounter);
        resList.add(failCounter);
        return resList;
    }


    /**
     * 优化版：利用批处理命令，执行insert语句
     *
     * @param queryResult
     * @param tableName
     * @return
     */
    public int[] batchInsertIntoTargetTableByTableName(List<HashMap> queryResult, String tableName) {
        if (null == queryResult) {
            return new int[]{0, 0};
        }
        if (tbSeqMappingProp == null) {
            try {
                tbSeqMappingProp = sequenceManagerController.loadMappingExcel();
            } catch (Exception e) {
                log.error("Error: cannot Execute : DbOperateService：sequenceManagerController.loadMappingExcel(); ");
            }
        }
        List<Integer> resList = new ArrayList<Integer>();
        //获取 "字段-字段类型" 映射map
        HashMap<String, HashMap> tbStructureMap = queryTableStructureByTableName(tableName);

        //根据targetTable获取对应的字段映射表(需要过滤掉为null的字段)
        HashMap mapping = columnMappingService.getColumnMappingByTargetTableName(tableName);
        if (mapping.size() == 0) {
            log.error("Empty mapping!tableName:" + tableName);
            return new int[]{0, queryResult.size()};
        }

        int successCounter = 0;
        int failCounter = 0;
        List keyList_SQL = new ArrayList<String>();
        /**要让映射过程可控，就需要以定义的mapping表为参考标准拼接SQL*/
        //遍历mapping，并根据mapping结果集中的key，将值通过映射表映射到数据库中
        Set set = mapping.keySet();//这里以mapping的keyset作为参考表，即使源表中多余的字段，也不会因mapping中没有对应的字段映射而报错
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            String k = (String) iterator.next();//mapping的keyName
            keyList_SQL.add(mapping.get(k));
        }

        ArrayList<String> insertSQLList = new ArrayList<>();

        for (int i = 0; i < queryResult.size(); i++) {
            List valueList_SQL = new ArrayList<String>();
            HashMap valueObj = queryResult.get(i);
            Set set1 = mapping.keySet();//这里以mapping的keyset作为参考表，即使源表中多余的字段，也不会因mapping中没有对应的字段映射而报错
            Iterator iterator1 = set1.iterator();
            while (iterator1.hasNext()) {
                String k = (String) iterator1.next();//mapping的keyName
                valueList_SQL.add(valueObj.get(k));
            }
            String targetTableName = tableName;
            //根据表名、字段名称集合,与表结构 获取 组装后的SQL值String
            String tableValues = getTableValuesSQLString(targetTableName, keyList_SQL, valueList_SQL, tbStructureMap);
            //异常情况处理：如果不能在业务库中找到这张目标表对应的表结构,则放弃执行该任务
            if (null == tableValues || "".equals(tableValues)) {
                log.error("cannot find target table:\"" + targetTableName + "\" in targetDB");
                return new int[]{0, queryResult.size()};
            }
            String sequenceName = tbSeqMappingProp.getProperty(targetTableName);

            String sql = "INSERT INTO \"" + targetTableName + "\"(" +
                    CommonInstance.GLOBAL_COLNAME_INCRE_ID + "," +
                    //组装key列表
                    ListUtil.getSQLColumnsListWithQuotes(keyList_SQL) + ")" +
                    //组装value列表
                    " VALUES (" + sequenceName + ".NEXTVAL," + tableValues + ")";
            insertSQLList.add(sql);
        }

        int[] batchSqlRes = null;
        log.info("ListToInsert.size():" + insertSQLList.size());
        try {
            batchSqlRes = oracleBatchSql(ListUtil.toStringArray(insertSQLList));
            log.info("\nInsert:SuccessCount:" + batchSqlRes[0]);
            log.info("\nInsert:FailCount:" + batchSqlRes[1]);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return batchSqlRes;
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
    private String getTableValuesSQLString(String tableName, List keyList_SQL, List<String> valueList_sql, HashMap<String, HashMap> tbStructureMap) {

        List<String> valueList = new ArrayList<String>();
        for (int i = 0; i < keyList_SQL.size(); i++) {
            try {
                //通过当前key获取对应的字段类型
                String column_type = (String) tbStructureMap.get(tableName).get(keyList_SQL.get(i));
                //根据字段类型判断输出值的形式（加""或者to_date()）, 拼接至值列表中
                String decoratedColumn = DbTools.getDecoratedColumn(column_type, valueList_sql.get(i));
                if (null == decoratedColumn) {
                    log.error("DbOperateService.getTableValuesSQLString():\"tbStructureMap\" cannot find :\"" + tableName + "\"对应字段\"" + keyList_SQL.get(i) + "\"对应的字段类型，请在对应的表中补全后重试！！！");
                    return null;
                } else {
                    valueList.add(decoratedColumn);
                }
            } catch (Exception e) {
                log.error("DbOperateService.getTableValuesSQLString()方法中的\"tbStructureMap\"没有找到表\"" + tableName + "\"对应字段\"" + keyList_SQL.get(i) + "\"对应的字段类型，请在对应的表中补全后重试！！！");
                return null;
            }
        }
        return ListUtil.toStringWithoutBracket(valueList);
    }


    public boolean checkIfTableExists(String tbName) {
        String sql = "SELECT COUNT(*) COUNT FROM " + tbName;
        try {
            List<HashMap> hashMaps = oracleQuerySql(sql);
            if (hashMaps.size() > 0) {
                return true;
            }
        } catch (Exception e) {
            log.error("DbOperateService.checkIfTableExists_readOnly(),SQL:" + sql);
//            e.printStackTrace();
            return false;
        }
        return false;
    }

    public int getTableRowCounts(String tbName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM \"" + tbName + "\"";
        log.debug("sql: " + sql);

        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(sql);
        String count = null;
        while (resultSet.next()) {
            count = resultSet.getString(1);
        }
        return Integer.parseInt(count);
    }

    public boolean checkIfSequenceExists(String sequenceName) {
        String SQL = "select count(1) COUNT \n" +
                "from dba_sequences \n" +
                "where sequence_owner='" + orclUsername + "' and SEQUENCE_NAME='" + sequenceName + "'";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(SQL);
        int count = 0;
        log.info("\nsql:" + SQL);
        while (sqlRowSet.next()) {
            count = sqlRowSet.getInt("COUNT");
            log.info("\nCOUNT:" + count);
        }
        if (count > 0) {
            return true;
        }
        return false;
    }

    public boolean checkIfSequenceExists_pure(String targetTableName) {
        String SQL = "select count(1) COUNT \n" +
                "from dba_sequences \n" +
                "where sequence_owner='" + orclUsername + "' and SEQUENCE_NAME='" + targetTableName + "'";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(SQL);
        int count = 0;
        log.info("\nsql:" + SQL);
        while (sqlRowSet.next()) {
            count = sqlRowSet.getInt("COUNT");
            log.info("\nCOUNT:" + count);
        }
        if (count > 0) {
            return true;
        }
        return false;
    }

    public boolean checkIfSequenceExists_prefix_seq(String targetTableName) {
        String SQL = "select count(1) COUNT \n" +
                "from dba_sequences \n" +
                "where sequence_owner='" + orclUsername + "' and SEQUENCE_NAME='SEQ_" + targetTableName + "'";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(SQL);
        int count = 0;
        log.info("\nsql:" + SQL);
        while (sqlRowSet.next()) {
            count = sqlRowSet.getInt("COUNT");
            log.info("\nCOUNT:" + count);
        }
        if (count > 0) {
            return true;
        }
        return false;
    }

    public boolean createSequence(String sequenceName) {
        String sql = "create sequence " + sequenceName + "\n" +
                "minvalue 1\n" +
                "maxvalue 999999999999\n" +
                "start with 1\n" +
                "increment by 1\n" +
                "cache 50";
        jdbcTemplate.execute(sql);
        return true;
    }

    public boolean dropSequence(String sequenceName) {
        String sql = "drop sequence " + sequenceName;
        jdbcTemplate.execute(sql);
        return true;
    }

    public boolean checkIfColumnExists(String targetTableName, String globalIncreIdColname) {

        String sql = "SELECT count(*) COUNT " +
                "FROM user_tab_columns" +
                " WHERE TABLE_NAME ='" + targetTableName + "'" +
                " AND column_name= '" + globalIncreIdColname + "'";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        int count = 0;
        while (sqlRowSet.next()) {
            count = sqlRowSet.getInt(1);
        }

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int exeCuteClobSQL(String sql, AbstractLobCreatingPreparedStatementCallback ablcPsCbk) {
        final LobHandler lobHandler = new DefaultLobHandler(); //定义一个lob的操作类
        Object update_result = jdbcTemplate.execute(sql, ablcPsCbk);
        return 1;
    }

    public boolean addColumn(String targetTableName, String columnName, String columnType_len) {

        String sql = "alter table " + targetTableName + " add " + columnName + " " + columnType_len;
        jdbcTemplate.execute(sql);
        return true;
    }

    public boolean addColumnComment(String targetTableName, String columnName, String columnComment) {

        String sql = "COMMENT ON COLUMN \"" + orclUsername + "\".\"" + targetTableName + "\".\"" + columnName + "\" IS \'" + columnComment + "\'";
        jdbcTemplate.execute(sql);
        return true;
    }

    public boolean addTableComment(String targetTableName, String tableComment) {

        String sql = "COMMENT ON TABLE \"" + orclUsername + "\".\"" + targetTableName + "\" IS " + tableComment;
        jdbcTemplate.execute(sql);
        return true;
    }

    public boolean checkIfExistsTable(String targetTableName) {
        String sql = "SELECT count(1) from user_all_tables " +
                "WHERE table_name ='" + targetTableName + "'";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        int count = 0;
        while (sqlRowSet.next()) {
            count = sqlRowSet.getInt(1);
        }
        if (count == 1) {
            return true;
        } else {
            return false;
        }
    }

    public String backUpTable(String tableName) {
        //todo 执行备份
        int count = 0;
        String newCopyName = tableName + "_COPY" + count;
        boolean ifExistsTable = false;
        while (true) {
            ifExistsTable = checkIfExistsTable(newCopyName);
            if (ifExistsTable == true) {
                newCopyName = tableName + "_COPY" + count;
                count++;

            } else {
                log.info("success! Table copy doesn't exists, will do backup by TableName:" + tableName + "_COPY" + count);
                break;
            }
        }
        backUpTable(tableName, newCopyName);
        //删除多余的备份
        try {
            deleteRedundantTableCopies(tableName);
        } catch (Exception e) {
            log.error("" + e.getMessage());
        }
        return newCopyName;
    }

    public void deleteRedundantTableCopies(String tableName) throws SQLException {

        String SQL = "SELECT TABLE_NAME FROM USER_ALL_TABLES " +
                "WHERE TABLE_NAME LIKE '" + tableName + "_COPY%'";
        List<String> list = oracleQueryList(SQL);
        log.info(SQL);
        log.info("\nCopy counts:" + list.size());
        if (list.size() >= 3) { //只留2个备份
            log.info("\nPrepare Delete Redundant Copies");

            list.sort(new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    String obj1 = (String) o1;
                    String obj2 = (String) o2;
                    if (obj1.length() > 2 && obj2.length() > 2) {
                        if (Integer.valueOf(obj1.substring(obj1.length() - 1, obj1.length())) > Integer.valueOf(obj2.substring(obj2.length() - 1, obj2.length()))) {
                            return 1;
                        } else if (Integer.valueOf(obj1.substring(obj1.length() - 1, obj1.length())) < Integer.valueOf(obj2.substring(obj2.length() - 1, obj2.length()))) {
                            {
                                return -1;
                            }
                        }
                    }
                    return 0;
                }
            });
            for (int i = 0; i < list.size() - 2; i++) {
                boolean b = dropTable(list.get(i));
                if (b) {
                    log.info("\nSuccessfully Dropped Table:" + list.get(i));
                } else {
                    log.error("\nFailed Dropping Table:" + list.get(i));
                }
            }
        } else {
            return;
        }
    }

    public boolean truncateTableByTbName(String targetTbName) {
        boolean ifExistsTable = checkIfExistsTable(targetTbName);
        if (ifExistsTable) {
            log.info("prepare truncate table:" + targetTbName);
            String sql = "truncate TABLE \"" + orclUsername + "\".\"" + targetTbName + "\"";
            jdbcTemplate.execute(sql);
            log.info("done truncate table:" + targetTbName);
            return true;
        } else {
            log.error("\n >>> Table does not exists! tableName:" + targetTbName);
            return false;
        }
    }

    public boolean dropTable(String tableName) {
        boolean ifExistsTable = checkIfExistsTable(tableName);
        if (ifExistsTable) {
            String sql = "DROP TABLE \"" + orclUsername + "\".\"" + tableName + "\"";
            jdbcTemplate.execute(sql);
            return true;
        } else {
            log.error("\n >>> Table does not exists! tableName:" + tableName);
            return false;
        }
    }

    /**
     * 校准序列值
     */
    public boolean correctSequence(String tableName) {
        //todo 1.获取最大ObjectId
        int maxObjectId = getMaxObjectId(tableName);
        //todo 2.获取nextVal
        int nextSeqVal = getNextSeqVal(tableName);
        //todo 3.获取差值 objectID：215  nextVal：315 目标值：215   215-315= -100
        int differNum = maxObjectId - nextSeqVal;
        String SQL1 = "alter sequence \"" + orclUsername + "\".\"SEQ_" + tableName + "\" increment by " + differNum;
        jdbcTemplate.execute(SQL1);
        int nextSeqVal2 = getNextSeqVal(tableName);
        if (maxObjectId == nextSeqVal2) {
            String SQL2 = "alter sequence SEQ_" + tableName + " increment by 1";
            jdbcTemplate.execute(SQL2);
            return true;
        }

        return false;

    }

    public int getMaxObjectId(String tableName) {
        int maxObjId = -1;
        String sql = "select max(OBJECT_ID) FROM \"" + orclUsername + "\".\"" + tableName + "\"";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        if (!rowSet.wasNull()) {
            rowSet.next();
            maxObjId = rowSet.getInt(1);
        }
        return maxObjId;
    }

    public int getNextSeqVal(String sequenceName) {
        int nextVal = -1;
        String sql = "select " + sequenceName + ".nextval from dual";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        try {
            rowSet.next();
            return rowSet.getInt(1);
        } catch (Exception e) {
            return nextVal;
        }
    }

    /**
     * 执行copy至新表
     *
     * @param srcTableName
     * @param bkTableName
     */
    public void backUpTable(String srcTableName, String bkTableName) {
        String SQL = "create TABLE \"" + orclUsername + "\".\"" + bkTableName + "\" as  SELECT * FROM " + srcTableName;
        jdbcTemplate.execute(SQL);
//        增加约束
        String SQL2 = "select COLUMN_NAME,DATA_DEFAULT\n" +
                "from user_tab_columns\n" +
                "where table_name='" + srcTableName + "'\n" +
                "and data_default is not null";
        List<HashMap> hashMaps = null;
        try {
            hashMaps = oracleQuerySql(SQL2);
            if (null == hashMaps) {
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (HashMap map : hashMaps) {
            String column_name = (String) map.get("COLUMN_NAME");
            String data_default = (String) map.get("DATA_DEFAULT");
            int i = executeAlterTableSQL(bkTableName, column_name, data_default);
            if (i > 0) {
                log.info("Add Table Constraint:executeAlterTableSQL(bkTableName, column_name, data_default)" + bkTableName + column_name + data_default);
            }
        }
    }

    private int executeAlterTableSQL(String tableName, String column_name, String data_default) {
        String sql = "alter table " + tableName + " modify " + column_name + " default " + data_default;
        int update = jdbcTemplate.update(sql);
        return update;
    }


    public List getAllSequenceNameList() {
        List<String> list = new ArrayList<String>();
        String SQL = "SELECT sequence_name from user_sequences";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(SQL);
        while (rowSet.next()) {
            String seq_name = rowSet.getString(1);
            list.add(seq_name);
        }
        return list;
    }

    public List getAllTableList() {
        List list = new ArrayList();
        String sql = "SELECT TABLE_NAME FROM USER_TABLES";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        while (rowSet.next()) {
            String table_name = rowSet.getString(1);
            list.add(table_name);
        }

        return list;
    }


}
