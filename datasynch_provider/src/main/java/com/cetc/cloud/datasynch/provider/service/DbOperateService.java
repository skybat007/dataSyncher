package com.cetc.cloud.datasynch.provider.service;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Description：
 * Created by luolinjie on 2018/10/19.
 */
public interface DbOperateService {

    /**
     * 通过表名，查询该表所有数据
     *
     * @param tbName
     * @return
     */
    List<HashMap> oracleQueryTable(String tbName) throws SQLException;

    /**
     * @Author huangzezhou
     * @Description //执行oracle sql语句
     * @Param [sql]
     * @Return com.cetc.cloud.framework.api.model.CetcCloudHttpResponse<java.util.List<java.util.HashMap>>
     * @Throws
     */
    List<HashMap> oracleQuerySql(String sql) throws SQLException;

    /**
     * 传入指定连接参数，执行SQL
     * @param sql
     * @param urlOracle
     * @param orclUsername
     * @param orclPassword
     * @return
     * @throws SQLException
     */
    List<HashMap> oracleQuerySql(String sql, String urlOracle, String orclUsername, String orclPassword) throws SQLException;

    /**
     * @Author huangzezhou
     * @Description //执行sql
     * @Param [sql]
     * @Return com.cetc.cloud.framework.api.model.CetcCloudHttpResponse<java.lang.Boolean>
     * @Throws
     */
    Boolean oracleSql(@RequestParam("sql") String sql) throws SQLException;

    List<Integer> oracleBatchSql(@RequestParam("sql") List<String> sql) throws SQLException;

    void oracleBatchSqlFile(String filePath) throws SQLException, IOException;

    /**
     * 将请求到的数据放到数据库里
     *
     * @param queryResult   前期请求到准备存放的数据
     * @param scheduleModel 定时任务描述，用于获取关键信息
     */
    List<Integer> insertIntoTargetTable(List<HashMap> queryResult, ScheduleModel scheduleModel) throws SQLException;

    /**
     * 获取表结构信息
     *
     * @return <表名：<字段名：字段类型>>的HashMap
     * @throws SQLException
     */
    HashMap<String, HashMap> queryTableStructure() throws SQLException;

}
