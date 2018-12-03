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
public class XinFangPeopleModel {
    private int objectId;
    private String visitNo;
    private String name;
    private String sex;
    private String dateofBirth;
    private String address;
    private String certKind;
    private String certNo;
    private String issuing;
    private String censusRegister;
    private String nation;
    private String certificateValidity;
    private String contactNumber;
    private String certAddress;
    private int isMain;

    @Override
    public String toString() {
        return "XinFangPeopleModel{" +
                "visitNo='" + visitNo + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", dateofBirth='" + dateofBirth + '\'' +
                ", address='" + address + '\'' +
                ", certKind='" + certKind + '\'' +
                ", certNo='" + certNo + '\'' +
                ", issuing='" + issuing + '\'' +
                ", censusRegister='" + censusRegister + '\'' +
                ", nation='" + nation + '\'' +
                ", certificateValidity='" + certificateValidity + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", certAddress='" + certAddress + '\'' +
                ", isMain=" + isMain +
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCertKind() {
        return certKind;
    }

    public void setCertKind(String certKind) {
        this.certKind = certKind;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getIssuing() {
        return issuing;
    }

    public void setIssuing(String issuing) {
        this.issuing = issuing;
    }

    public String getCensusRegister() {
        return censusRegister;
    }

    public void setCensusRegister(String censusRegister) {
        this.censusRegister = censusRegister;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCertificateValidity() {
        return certificateValidity;
    }

    public void setCertificateValidity(String certificateValidity) {
        this.certificateValidity = certificateValidity;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCertAddress() {
        return certAddress;
    }

    public void setCertAddress(String certAddress) {
        this.certAddress = certAddress;
    }

    public int getIsMain() {
        return isMain;
    }

    public void setIsMain(int isMain) {
        this.isMain = isMain;
    }
}
