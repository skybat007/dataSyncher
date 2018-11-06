package com.cetc.cloud.datasynch.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务表: DS_SCHEDULE_JOB_INFO
 * Created by llj on 2018/10/8.
 */
public class ScheduleModel implements Serializable {
    private int id;                         //job唯一主键
    private int connType;                   //连接方式（0：前置机方式 1：接口方式）
    private String source;                  //源（请求URL路径@token/前置机view视图的名称）
    private int isPagingQuery;              //是否是分页查询
    private String orderByColumnName;       //（数据库请求独有参数）源-排序字段名称
    private String httpParamExpression;     //（HTTP请求独有参数）入参表达式
    private String httpToken;               //（HTTP请求独有参数）token表达式
    private String httpParamPageSize;       //（HTTP请求独有参数）pageSize映射参数名
    private String httpParamPageNum;        //（HTTP请求独有参数）pageNum映射参数名
    private String httpJsonExtractRule;     //（HTTP请求独有参数）json解析规则
    private String httpTotalExtractRule;    //（HTTP请求独有参数）total参数解析规则
    private String targetTableName;         //入库表名
    private int pageSize;                   //页大小
    private String cronExpression;          //定时表达式
    private int isEnabled;                  //是否开启（0：关闭 1：开启）
    private Date createTime;                //创建时间
    private Date updateTime;                //更新时间

    public int getIsPagingQuery() {
        return isPagingQuery;
    }

    public void setIsPagingQuery(int isPagingQuery) {
        this.isPagingQuery = isPagingQuery;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConnType() {
        return connType;
    }

    public void setConnType(int connType) {
        this.connType = connType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOrderByColumnName() {
        return orderByColumnName;
    }

    public void setOrderByColumnName(String orderByColumnName) {
        this.orderByColumnName = orderByColumnName;
    }

    public String getHttpParamExpression() {
        return httpParamExpression;
    }

    public void setHttpParamExpression(String httpParamExpression) {
        this.httpParamExpression = httpParamExpression;
    }

    public String getHttpToken() {
        return httpToken;
    }

    public void setHttpToken(String httpToken) {
        this.httpToken = httpToken;
    }

    public String getHttpParamPageSize() {
        return httpParamPageSize;
    }

    public void setHttpParamPageSize(String httpParamPageSize) {
        this.httpParamPageSize = httpParamPageSize;
    }

    public String getHttpParamPageNum() {
        return httpParamPageNum;
    }

    public void setHttpParamPageNum(String httpParamPageNum) {
        this.httpParamPageNum = httpParamPageNum;
    }

    public String getHttpJsonExtractRule() {
        return httpJsonExtractRule;
    }

    public void setHttpJsonExtractRule(String httpJsonExtractRule) {
        this.httpJsonExtractRule = httpJsonExtractRule;
    }

    public String getHttpTotalExtractRule() {
        return httpTotalExtractRule;
    }

    public void setHttpTotalExtractRule(String httpTotalExtractRule) {
        this.httpTotalExtractRule = httpTotalExtractRule;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public int getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(int isEnabled) {
        this.isEnabled = isEnabled;
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

    @Override
    public String toString() {
        return "ScheduleModel{" +
                "id=" + id +
                ", connType=" + connType +
                ", source='" + source + '\'' +
                ", isPagingQuery=" + isPagingQuery +
                ", orderByColumnName='" + orderByColumnName + '\'' +
                ", httpParamExpression='" + httpParamExpression + '\'' +
                ", httpToken='" + httpToken + '\'' +
                ", httpParamPageSize='" + httpParamPageSize + '\'' +
                ", httpParamPageNum='" + httpParamPageNum + '\'' +
                ", httpJsonExtractRule='" + httpJsonExtractRule + '\'' +
                ", httpTotalExtractRule='" + httpTotalExtractRule + '\'' +
                ", targetTableName='" + targetTableName + '\'' +
                ", pageSize=" + pageSize +
                ", cronExpression='" + cronExpression + '\'' +
                ", isEnabled=" + isEnabled +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
