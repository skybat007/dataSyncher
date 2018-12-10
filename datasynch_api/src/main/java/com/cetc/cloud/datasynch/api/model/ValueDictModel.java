package com.cetc.cloud.datasynch.api.model;

import java.io.Serializable;

/**
 * PackageName:   com.cetc.cloud.datasynch.api.model
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/12/5 15:57
 * Updater:     by luolinjie
 * Update_Date: 2018/12/5
 * Update_Description: luolinjie 补充
 **/
public class ValueDictModel implements Serializable {
    private String objectId;
    private String tableName;
    private String columnName;
    private String code;
    private String codeInChinese;
    private String tbInChinese;
    private String colInChinese;
    private String createTime;
    private String updateTime;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeInChinese() {
        return codeInChinese;
    }

    public void setCodeInChinese(String codeInChinese) {
        this.codeInChinese = codeInChinese;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTbInChinese() {
        return tbInChinese;
    }

    public void setTbInChinese(String tbInChinese) {
        this.tbInChinese = tbInChinese;
    }

    public String getColInChinese() {
        return colInChinese;
    }

    public void setColInChinese(String colInChinese) {
        this.colInChinese = colInChinese;
    }
}
