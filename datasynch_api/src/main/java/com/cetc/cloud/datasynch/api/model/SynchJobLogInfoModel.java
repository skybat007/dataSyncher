package com.cetc.cloud.datasynch.api.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * 更新状态日志表: DS_SYNCH_JOB_LOG_INFO
 * Created by llj on 2018/10/8.
 */
public class SynchJobLogInfoModel implements Serializable{

    private int id;
    private int jobId;
    private int currentPageNum;
    private int currentPageSize;
    private int currentRownum;
    private int connType;
    private int successCount;
    private int failCount;
    private int totalSuccessCount;
    private int totalFailCount;
    private Date createTime;
    private Date updateTime;

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

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

    public int getTotalSuccessCount() {
        return totalSuccessCount;
    }

    public void setTotalSuccessCount(int totalSuccessCount) {
        this.totalSuccessCount = totalSuccessCount;
    }

    public int getTotalFailCount() {
        return totalFailCount;
    }

    public void setTotalFailCount(int totalFailCount) {
        this.totalFailCount = totalFailCount;
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
