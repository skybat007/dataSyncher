package com.cetc.cloud.datasynch.api.model;

import java.io.Serializable;

/**
 * 数据字典-值映射表:DDD_VALUE_ MAPPING
 * Created by llj on 2018/10/8.
 */
public class DddValueMappingModel implements Serializable {
    String tableName;
    String columnname;
    String columnCodeValue;
    String codeMeaning;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnname() {
        return columnname;
    }

    public void setColumnname(String columnname) {
        this.columnname = columnname;
    }

    public String getColumnCodeValue() {
        return columnCodeValue;
    }

    public void setColumnCodeValue(String columnCodeValue) {
        this.columnCodeValue = columnCodeValue;
    }

    public String getCodeMeaning() {
        return codeMeaning;
    }

    public void setCodeMeaning(String codeMeaning) {
        this.codeMeaning = codeMeaning;
    }
}
