package com.cetc.cloud.datasynch.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * PackageName:   com.cetc.cloud.datasynch.api.model
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/11/27 23:06
 * Updater:     by luolinjie
 * Update_Date: 2018/12/11 11:00
 * Update_Description: huangzezhou 补充
 **/

/**
 *
 value–字段说明
 name–重写属性名字
 dataType–重写属性类型
 required–是否必填
 example–举例说明
 hidden–隐藏
 */
@ApiModel("预警模型")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmModel implements Serializable {

    @ApiModelProperty(value = "全表自增id,mybatis自动生成",hidden = true)
    private int objectId;

    @ApiModelProperty(value = "预警对象", required = true)
    private String alarmObject;

    @ApiModelProperty(value = "外键关联主体的object_id", required = true)
    private int fRowId;

    @ApiModelProperty("来源表名")
    private String originTableName;

    @ApiModelProperty(value = "事件编号，预警平台生成，推送给综治", required = true)
    private String systemId;

    @ApiModelProperty(value = "发布时间", required = true)
    private Date releaseTime;

    @ApiModelProperty(value = "取消发布的时间")
    private Date cancelTime;

    @ApiModelProperty(value = "发布人", example = "预警监测平台", required = true)
    private String releasePerson;

    @ApiModelProperty(value = "预警状态", example = "1:正在预警中;0:预警已结束", required = true)
    private int alarmState;

    @ApiModelProperty(value = "预警内容", required = true)
    private String contents;

    @ApiModelProperty(value = "预警等级",
            example = "红、橙、黄、蓝;1、2、3级;高、中、低;物联网:一般、重要、紧急", required = true)
    private String alarmLevel;

    @ApiModelProperty(value = "一级预警类型编码", required = true)
    private String alarmTypeLv1;

    @ApiModelProperty(value = "二级预警类型编码", required = true)
    private String alarmTypeLv2;

    @ApiModelProperty(value = "处置状态",
            example = "0:未处置;1:已分拨;2:已办结;3:已归档;50:挂起;80:作废")
    private int disposalState;

    @ApiModelProperty(value = "发送状态", example = "0:未发送;1:已发送")
    private int sendState;

    @ApiModelProperty(value = "渠道", example = "物联网平台")
    private String channel;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "街道名")
    private String streetName;

    @ApiModelProperty(value = "社区名")
    private String communityName;

    @ApiModelProperty(value = "创建时间",hidden = true)
    private Date yjjcCreateTime;
    @ApiModelProperty(value = "更新时间",hidden = true)
    private Date yjjcUpdateTime;
    @ApiModelProperty(value = "楼栋代码",hidden = true)
    private String lddm;
    @ApiModelProperty(value = "84坐标经度",hidden = true)
    private String jd84;
    @ApiModelProperty(value = "84坐标维度",hidden = true)
    private String wd84;
    @ApiModelProperty(value = "区代码",hidden = true)
    private String regionCode;
    @ApiModelProperty(value = "街道代码",hidden = true)
    private String streetCode;
    @ApiModelProperty(value = "社区代码",hidden = true)
    private String communityCode;


//    public AlarmModel(String alarmObject, String fRowId, String originTableName, String systemId,
//                      Date releaseTime, Date cancelTime, String releasePerson, int alarmState, String contents,
//                      String alarmLevel, String alarmTypeLv1, String alarmTypeLv2, int disposalState,
//                      int sendState, String channel, String lddm, String jd84, String wd84,
//                      String regionCode, String streetCode, String communityCode){
//
//
//        this.alarmObject = alarmObject;
//        this.fRowId = fRowId;
//        this.originTableName = originTableName;
//        this.systemId = systemId;
//        this.releaseTime = releaseTime;
//        this.cancelTime = cancelTime;
//        this.releasePerson = releasePerson;
//        this.alarmState = alarmState;
//        this.contents = contents;
//        this.alarmLevel = alarmLevel;
//        this.alarmTypeLv1 = alarmTypeLv1;
//        this.alarmTypeLv2 = alarmTypeLv2;
//        this.disposalState = disposalState;
//        this.sendState = sendState;
//        this.channel = channel;
//        this.lddm = lddm;
//        this.jd84 = jd84;
//        this.wd84 = wd84;
//        this.regionCode = regionCode;
//        this.streetCode = streetCode;
//        this.communityCode = communityCode;
//    }
}
