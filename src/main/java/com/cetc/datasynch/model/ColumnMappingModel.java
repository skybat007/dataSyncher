package com.cetc.datasynch.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * 字段解析映射model -- DS_COLUMN_MAPPING_INFO表
 * Created by llj on 2018/10/14.
 */
public class ColumnMappingModel implements Serializable {
    int id;
    int targetTable;
    String source;
    String sourceColumnName;
    String targetColumnName;
    Date CreateTime;
    Date UpdateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(int targetTable) {
        this.targetTable = targetTable;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceColumnName() {
        return sourceColumnName;
    }

    public void setSourceColumnName(String sourceColumnName) {
        this.sourceColumnName = sourceColumnName;
    }

    public String getTargetColumnName() {
        return targetColumnName;
    }

    public void setTargetColumnName(String targetColumnName) {
        this.targetColumnName = targetColumnName;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    public Date getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(Date updateTime) {
        UpdateTime = updateTime;
    }
}
