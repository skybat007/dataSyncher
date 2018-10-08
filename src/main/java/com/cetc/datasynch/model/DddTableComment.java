package com.cetc.datasynch.model;

import java.io.Serializable;

/**
 * Created by llj on 2018/10/8.
 */
public class DddTableComment implements Serializable {
    String tableName;
    String tableComment;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }
}
