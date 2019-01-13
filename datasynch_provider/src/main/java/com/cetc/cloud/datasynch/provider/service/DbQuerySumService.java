package com.cetc.cloud.datasynch.provider.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2019/1/4.
 */
public interface DbQuerySumService {

    HashMap<String, HashMap> queryTableStructure() throws SQLException;

    List<HashMap> oracleQueryTable(String tbName) throws SQLException;

    List<HashMap> oracleQuerySql_readOnly(String sql);

    boolean checkIfTableExists_readOnly(String tbName);

    int getTableRowCounts_readOnly(String tbName);
}
