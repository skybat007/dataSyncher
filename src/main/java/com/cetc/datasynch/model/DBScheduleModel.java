package com.cetc.datasynch.model;

import java.io.Serializable;

/**
 * 定时任务表: DS_SYNCH_JOB_LOG_INFO
 * Created by llj on 2018/10/8.
 */
public class DBScheduleModel implements Serializable {
    String id;
    String connType;//连接方式（0：前置机方式 1：接口方式）
    String source;  //源(请求URL路径/前置机view视图的名称)
    String tableName;//入库表名
    String scheduleExpression;//定时表达式
    String isEnabled;//是否开启（0：关闭 1：开启）
    String createTime;//创建时间
    String updateTime;//更新时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConnType() {
        return connType;
    }

    public void setConnType(String connType) {
        this.connType = connType;
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

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
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
