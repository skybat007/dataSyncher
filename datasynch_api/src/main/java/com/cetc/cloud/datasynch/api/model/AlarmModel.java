package com.cetc.cloud.datasynch.api.model;

import java.io.Serializable;

/**
 * PackageName:   com.cetc.cloud.datasynch.api.model
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/11/27 23:06
 * Updater:     by luolinjie
 * Update_Date: 2018/11/27
 * Update_Description: luolinjie 补充
 **/
public class AlarmModel implements Serializable {
    private String objectId;
    private String alarmObject;
    private String fRowId;
    private String originTableName;
    private String eventCode;
    private String releaseTime;
    private String cancelTime;
    private String releasePerson;
    private String alarmState;
    private String contents;
    private String alarmLevel;
    private String alarmTypeLv1;
    private String alarmTypeLv2;
    private String disposalState;
    private String distributionState;
    private String sendState;
    private String channel;
    private String yjjcCreateTime;
    private String yjjcUpdateTime;
    private String lddm;
    private String jd84;
    private String wd84;
    private String regionCode;
    private String streetCode;
    private String communityCode;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getAlarmObject() {
        return alarmObject;
    }

    public void setAlarmObject(String alarmObject) {
        this.alarmObject = alarmObject;
    }

    public String getfRowId() {
        return fRowId;
    }

    public void setfRowId(String fRowId) {
        this.fRowId = fRowId;
    }

    public String getOriginTableName() {
        return originTableName;
    }

    public void setOriginTableName(String originTableName) {
        this.originTableName = originTableName;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getReleasePerson() {
        return releasePerson;
    }

    public void setReleasePerson(String releasePerson) {
        this.releasePerson = releasePerson;
    }

    public String getAlarmState() {
        return alarmState;
    }

    public void setAlarmState(String alarmState) {
        this.alarmState = alarmState;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getAlarmTypeLv1() {
        return alarmTypeLv1;
    }

    public void setAlarmTypeLv1(String alarmTypeLv1) {
        this.alarmTypeLv1 = alarmTypeLv1;
    }

    public String getAlarmTypeLv2() {
        return alarmTypeLv2;
    }

    public void setAlarmTypeLv2(String alarmTypeLv2) {
        this.alarmTypeLv2 = alarmTypeLv2;
    }

    public String getDisposalState() {
        return disposalState;
    }

    public void setDisposalState(String disposalState) {
        this.disposalState = disposalState;
    }

    public String getDistributionState() {
        return distributionState;
    }

    public void setDistributionState(String distributionState) {
        this.distributionState = distributionState;
    }

    public String getSendState() {
        return sendState;
    }

    public void setSendState(String sendState) {
        this.sendState = sendState;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getYjjcCreateTime() {
        return yjjcCreateTime;
    }

    public void setYjjcCreateTime(String yjjcCreateTime) {
        this.yjjcCreateTime = yjjcCreateTime;
    }

    public String getYjjcUpdateTime() {
        return yjjcUpdateTime;
    }

    public void setYjjcUpdateTime(String yjjcUpdateTime) {
        this.yjjcUpdateTime = yjjcUpdateTime;
    }

    public String getLddm() {
        return lddm;
    }

    public void setLddm(String lddm) {
        this.lddm = lddm;
    }

    public String getJd84() {
        return jd84;
    }

    public void setJd84(String jd84) {
        this.jd84 = jd84;
    }

    public String getWd84() {
        return wd84;
    }

    public void setWd84(String wd84) {
        this.wd84 = wd84;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(String streetCode) {
        this.streetCode = streetCode;
    }

    public String getCommunityCode() {
        return communityCode;
    }

    public void setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
    }
}
