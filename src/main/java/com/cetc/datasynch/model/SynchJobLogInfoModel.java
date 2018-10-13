package com.cetc.datasynch.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 更新状态日志表: DS_SYNCH_JOB_LOG_INFO
 * Created by llj on 2018/10/8.
 */
public class SynchJobLogInfoModel implements Serializable{
    int id;
    int jobId;
    int currentPageNum;
    int currentPageSize;
    int currentRownum;
    int pageSize;
    int connType;
    Date createTime;
    Date updateTime;

    public int getCurrentPageSize() {
        return currentPageSize;
    }

    public void setCurrentPageSize(int currentPageSize) {
        this.currentPageSize = currentPageSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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

    public int getConnType() {
        return connType;
    }

    public void setConnType(int connType) {
        this.connType = connType;
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
