package com.cetc.cloud.datasynch.api.model;

/**
 * PackageName:   com.cetc.cloud.datasynch.api.model
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/11/28 19:24
 * Updater:     by luolinjie
 * Update_Date: 2018/11/28
 * Update_Description: luolinjie 补充
 **/
public class XinFangEventModel {
    private int objectId;
    private String visitNo;
    private String visitTime;
    private int visitPersonnelNum;
    private String contradiction;
    private String mattersName;
    private String visitMattersRemark;
    private String eventAddress;
    private String performanceName;
    private String departmentName;
    private String visitPlace;
    private String outAttitude;
    private String isIncludedStatistics;
    private String visitAddressName;
    private String source;
    private String visitType;

    @Override
    public String toString() {
        return "XinFangEventModel{" +
                "visitNo='" + visitNo + '\'' +
                ", visitTime='" + visitTime + '\'' +
                ", visitPersonnelNum=" + visitPersonnelNum +
                ", contradiction='" + contradiction + '\'' +
                ", mattersName='" + mattersName + '\'' +
                ", visitMattersRemark='" + visitMattersRemark + '\'' +
                ", eventAddress='" + eventAddress + '\'' +
                ", performanceName='" + performanceName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", visitPlace='" + visitPlace + '\'' +
                ", outAttitude='" + outAttitude + '\'' +
                ", isIncludedStatistics=" + isIncludedStatistics +
                ", visitAddressName='" + visitAddressName + '\'' +
                ", source='" + source + '\'' +
                ", visitType='" + visitType + '\'' +
                '}';
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public int getVisitPersonnelNum() {
        return visitPersonnelNum;
    }

    public void setVisitPersonnelNum(int visitPersonnelNum) {
        this.visitPersonnelNum = visitPersonnelNum;
    }

    public String getContradiction() {
        return contradiction;
    }

    public void setContradiction(String contradiction) {
        this.contradiction = contradiction;
    }

    public String getMattersName() {
        return mattersName;
    }

    public void setMattersName(String mattersName) {
        this.mattersName = mattersName;
    }

    public String getVisitMattersRemark() {
        return visitMattersRemark;
    }

    public void setVisitMattersRemark(String visitMattersRemark) {
        this.visitMattersRemark = visitMattersRemark;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getPerformanceName() {
        return performanceName;
    }

    public void setPerformanceName(String performanceName) {
        this.performanceName = performanceName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getVisitPlace() {
        return visitPlace;
    }

    public void setVisitPlace(String visitPlace) {
        this.visitPlace = visitPlace;
    }

    public String getOutAttitude() {
        return outAttitude;
    }

    public void setOutAttitude(String outAttitude) {
        this.outAttitude = outAttitude;
    }

    public String getIsIncludedStatistics() {
        return isIncludedStatistics;
    }

    public void setIsIncludedStatistics(String isIncludedStatistics) {
        this.isIncludedStatistics = isIncludedStatistics;
    }

    public String getVisitAddressName() {
        return visitAddressName;
    }

    public void setVisitAddressName(String visitAddressName) {
        this.visitAddressName = visitAddressName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }
}
