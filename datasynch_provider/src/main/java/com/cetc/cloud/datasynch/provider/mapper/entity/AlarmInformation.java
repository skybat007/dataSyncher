package com.cetc.cloud.datasynch.provider.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2018-12-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ALARM_INFORMATION")
public class AlarmInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 全表自增id
     */
    @TableId("OBJECT_ID")
    private Integer objectId;

    /**
     * 预警对象
     */
    @TableField("ALARM_OBJECT")
    private String alarmObject;

    /**
     * 外键关联OBJECT_ID
     */
    @TableField("F_ROW_ID")
    private Integer fRowId;

    /**
     * 源表名
     */
    @TableField("ORIGIN_TABLE_NAME")
    private String originTableName;

    /**
     * 综治业务编号
     */
    @TableField("SYSTEM_ID")
    private String systemId;

    /**
     * 发布时间
     */
    @TableField("RELEASE_TIME")
    private Date releaseTime;

    /**
     * 取消时间
     */
    @TableField("CANCEL_TIME")
    private Date cancelTime;

    /**
     * 发布人
     */
    @TableField("RELEASE_PERSON")
    private String releasePerson;

    /**
     * 预警状态（1:正在预警中；0:预警已结束）
     */
    @TableField("ALARM_STATE")
    private Integer alarmState;

    /**
     * 预警内容
     */
    @TableField("CONTENTS")
    private String contents;

    /**
     * 预警等级
     */
    @TableField("ALARM_LEVEL")
    private String alarmLevel;

    /**
     * 一级预警等级编码
     */
    @TableField("ALARM_TYPE_LV1")
    private String alarmTypeLv1;

    /**
     * 二级预警等级编码
     */
    @TableField("ALARM_TYPE_LV2")
    private String alarmTypeLv2;

    /**
     * 处置状态（0.未处置、2.办结、3.已评价、3.归档、50.挂起、80.作废）
     */
    @TableField("DISPOSAL_STATE")
    private Integer disposalState;

    /**
     * 发送状态（0.未发送；1.已发送）
     */
    @TableField("SEND_STATE")
    private Integer sendState;

    /**
     * 渠道
     */
    @TableField("CHANNEL")
    private String channel;

    /**
     * 创建日期
     */
    @TableField("YJJC_CREATE_TIME")
    private Date yjjcCreateTime;

    /**
     * 更新时间
     */
    @TableField("YJJC_UPDATE_TIME")
    private Date yjjcUpdateTime;

    /**
     * 楼栋代码
     */
    @TableField("LDDM")
    private String lddm;

    /**
     * 经度84
     */
    @TableField("JD84")
    private String jd84;

    /**
     * 纬度84
     */
    @TableField("WD84")
    private String wd84;

    /**
     * 区代码
     */
    @TableField("REGION_CODE")
    private String regionCode;

    /**
     * 街道代码
     */
    @TableField("STREET_CODE")
    private String streetCode;

    /**
     * 社区代码
     */
    @TableField("COMMUNITY_CODE")
    private String communityCode;

    @TableField("ADDRESS")
    private String address;

    @TableField("STREET_NAME")
    private String streetName;

    @TableField("COMMUNITY_NAME")
    private String communityName;


}
