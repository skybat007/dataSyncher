package com.cetc.cloud.datasynch.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务表: DS_SCHEDULE_JOB_INFO
 * Created by llj on 2018/10/8.
 */
public class ScheduleModel implements Serializable {
    int id;                         //job唯一主键
    int connType;                   //连接方式（0：前置机方式 1：接口方式）
    String source;                  //源（请求URL路径@token/前置机view视图的名称）
    String dbSrcConnParam;          //（数据库请求独有参数）数据源连接参数IP;USRNAME;PASSWORD;DBURL
    String httpParamExpression;     //（HTTP请求独有参数）入参表达式,pageSize、pageNum映射规则
    String jsonExtractRule;         //（HTTP请求独有参数）json解析规则
    String targetTableName;         //入库表名
    int pageSize;                   //页大小
    String cronExpression;          //定时表达式
    int isEnabled;                  //是否开启（0：关闭 1：开启）
    Date createTime;                //创建时间
    Date updateTime;                //更新时间


    public String getIp(){
        return dbSrcConnParam.split(";")[0];
    }
    public String getSrcUsername(){
        return dbSrcConnParam.split(";")[1];
    }
    public String getSrcPassword(){
        return dbSrcConnParam.split(";")[2];
    }
    public String getSrcDbUrl(){
        return dbSrcConnParam.split(";")[3];
    }
    public String getHttpParams(){
        return httpParamExpression.split(";")[0];
    }
    public String getHttpPageMappingRule(){
        return httpParamExpression.split(";")[1];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDbSrcConnParam() {
        return dbSrcConnParam;
    }

    public void setDbSrcConnParam(String dbSrcConnParam) {
        this.dbSrcConnParam = dbSrcConnParam;
    }

    public String getHttpParamExpression() {
        return httpParamExpression;
    }

    public void setHttpParamExpression(String httpParamExpression) {
        this.httpParamExpression = httpParamExpression;
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

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
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
