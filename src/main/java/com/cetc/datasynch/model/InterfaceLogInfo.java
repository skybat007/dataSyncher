package com.cetc.datasynch.model;

import java.io.Serializable;

/**
 * 接口方式更新状态日志表: DP_INTERFACE_LOG_INFO
 * Created by llj on 2018/10/8.
 */
public class InterfaceLogInfo implements Serializable{
    String tableName;
    String currentPage;
    String currentRownum;
    String currentRowId;
    String createTime;
    String updateTime;

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

    public String getCurrentRownum() {
        return currentRownum;
    }

    public void setCurrentRownum(String currentRownum) {
        this.currentRownum = currentRownum;
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
