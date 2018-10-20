package com.cetc.cloud.datasynch.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务表: DS_SCHEDULE_JOB_INFO
 * Created by llj on 2018/10/8.
 */
public class ScheduleModel implements Serializable {
    int id;                         //jobId
    int connType;                   //连接方式（0：前置机方式 1：接口方式）
    String source;                  //源(请求URL路径@token/前置机view视图的名称)
    Token token;                    //认证令牌
    String jsonExtractRule;         //json解析规则
    String tableName;               //入库表名
    int pageSize;                   //页大小
    String scheduleExpression;      //定时表达式
    int isEnabled;                  //是否开启（0：关闭 1：开启）
    Date createTime;                //创建时间
    Date updateTime;                //更新时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getJsonExtractRule() {
        return jsonExtractRule;
    }

    public void setJsonExtractRule(String jsonExtractRule) {
        this.jsonExtractRule = jsonExtractRule;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getScheduleExpression() {
        return scheduleExpression;
    }

    public void setScheduleExpression(String scheduleExpression) {
        this.scheduleExpression = scheduleExpression;
    }

    public int getConnType() {
        return connType;
    }

    public void setConnType(int connType) {
        this.connType = connType;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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
}
