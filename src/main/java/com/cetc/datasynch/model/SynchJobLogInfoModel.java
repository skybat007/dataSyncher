package com.cetc.datasynch.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 更新状态日志表: DS_SYNCH_JOB_LOG_INFO
 * Created by llj on 2018/10/8.
 */
public class SynchJobLogInfoModel implements Serializable{
    String id;
    String jobId;
    String tableName;
    int currentPageNum;
    int currentPageSize;
    int currentRownum;
    int updateWay;
    Date createTime;
    Date updateTime;

    public int getCurrentPageSize() {
        return currentPageSize;
    }

    public void setCurrentPageSize(int currentPageSize) {
        this.currentPageSize = currentPageSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getCurrentRownum() {
        return currentRownum;
    }

    public void setCurrentRownum(int currentRownum) {
        this.currentRownum = currentRownum;
    }

    public int getUpdateWay() {
        return updateWay;
    }

    public void setUpdateWay(int updateWay) {
        this.updateWay = updateWay;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
