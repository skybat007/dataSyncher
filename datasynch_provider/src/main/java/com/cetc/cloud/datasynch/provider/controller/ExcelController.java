package com.cetc.cloud.datasynch.provider.controller;

import com.cetc.cloud.datasynch.api.service.ExcelRemoteService;
import com.cetc.cloud.datasynch.provider.core.tools.ExcelTool;
import com.cetc.cloud.datasynch.provider.core.util.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.controller
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/11/14 12:17
 * Updater:     by luolinjie
 * Update_Date: 2018/11/14
 * Update_Description: luolinjie 补充
 **/
@Slf4j
@RestController
public class ExcelController implements ExcelRemoteService {

    @Autowired
//    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate primaryJdbcTemplate;

    @Override
    public HashMap<String, String> exportExcelByTableName(String tableName, String columns) {
        HashMap<String, String> res = new HashMap<String, String>();
        if (null == tableName || null == columns || "".equals(tableName) || "".equals(columns)) {
            res.put("res", "fail");
            res.put("msg", "tableName, columns cannot be null!");
        }

        String SQL = "SELECT $cols$ FROM " + tableName;

        String[] split = columns.split(",");
        List<String> colNameList = Arrays.asList(split);
        String realCols = ListUtil.toStringWithoutBracket(colNameList);
        String SQL1 = SQL.replaceAll("\\$cols\\$", realCols);
        SqlRowSet sqlRowSet = primaryJdbcTemplate.queryForRowSet(SQL1);
        List<List<String>> colValueList = new ArrayList<List<String>>();

        while (sqlRowSet.next()) {
            int columnCount = sqlRowSet.getMetaData().getColumnCount();
            ArrayList<String> aRowOfValues = new ArrayList<String>();
            for (int i = 1; i <= columnCount; i++) {
                String col1 = sqlRowSet.getString(i);
                aRowOfValues.add(col1);
            }
            colValueList.add(aRowOfValues);
        }

        try {
            res = ExcelTool.exportExcel(colValueList, colNameList, tableName);
        } catch (Exception e) {
            log.error("error while export excel");
            e.printStackTrace();
            return res;
        }

        res.put("res", "success");
        res.put("msg", "generated file path is：" + res);
        return res;
    }
}
