package com.cetc.datasynch.model;

import java.io.Serializable;

/**
 * 前置机view方式更新状态日志表: DP_TABLE_LOG_INFO
 * Created by llj on 2018/10/8.
 */
public class TableLogInfoModel implements Serializable {
    String uuid;
    String tableName;
    String currentPage;
    String currentRowNum;
    String currentRowId;
    String createTime;
    String updateTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getCurrentRowNum() {
        return currentRowNum;
    }

    public void setCurrentRowNum(String currentRowNum) {
        this.currentRowNum = currentRowNum;
    }

    public String getCurrentRowId() {
        return currentRowId;
    }

    public void setCurrentRowId(String currentRowId) {
        this.currentRowId = currentRowId;
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
}
