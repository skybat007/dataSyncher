package com.cetc.datasynch.model;

import java.io.Serializable;

/**
 * 表注释 -- DDD_TABLE_COMMENT表
 * Created by llj on 2018/10/8.
 */
public class DddTableCommentModel implements Serializable {
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
