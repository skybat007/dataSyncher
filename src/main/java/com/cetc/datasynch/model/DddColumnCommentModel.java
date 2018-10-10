package com.cetc.datasynch.model;

import java.io.Serializable;

/**
 * 数据字典-字段注释表:DDD_COLUMN_COMMENT
 * Created by llj on 2018/10/8.
 */
public class DddColumnCommentModel implements Serializable{
    String tableName;
    String columnName;
    String columnComment;

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

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }
}
