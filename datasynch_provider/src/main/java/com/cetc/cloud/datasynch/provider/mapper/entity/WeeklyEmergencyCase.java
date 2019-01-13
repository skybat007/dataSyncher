package com.cetc.cloud.datasynch.provider.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2018-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("WEEKLY_EMERGENCY_CASE")
public class WeeklyEmergencyCase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 原始id
     */
    @TableField("ID")
    private Double id;

    /**
     * 事件等级
     */
    @TableField("LEVEL")
    private String level;

    /**
     * 事件名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 事件类型
     */
    @TableField("TYPE")
    private String type;

    /**
     * 事件类型代码
     */
    @TableField("TYPE_CODE")
    private String typeCode;

    /**
     * 事件发生地址
     */
    @TableField("ADDRESS")
    private String address;

    /**
     * 事件描述
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 死亡人数
     */
    @TableField("DEATH_PEOPLE")
    private Double deathPeople;

    /**
     * 受伤人数
     */
    @TableField("INJURE_PEOPLE")
    private Double injurePeople;

    /**
     * 财产损失
     */
    @TableField("PROPERTY_DAMAGE")
    private String propertyDamage;

    /**
     * 事件原因
     */
    @TableField("REASON")
    private String reason;

    /**
     * 相关人数
     */
    @TableField("RELATE_PEOPLE")
    private Double relatePeople;

    /**
     * 上报单位
     */
    @TableField("SEND_UNIT")
    private String sendUnit;

    /**
     * 经度
     */
    @TableField("X")
    private String x;

    /**
     * 纬度
     */
    @TableField("Y")
    private String y;

    /**
     * 经纬度
     */
    @TableField("XY")
    private String xy;

    /**
     * 事件起始时间
     */
    @TableField("START_TIME")
    private LocalDateTime startTime;

    /**
     * 网格名称
     */
    @TableField("GRID_NAME")
    private String gridName;

    /**
     * 登记人姓名
     */
    @TableField("REGISTER_PEOPLE_NAME")
    private String registerPeopleName;

    /**
     * 登记时间
     */
    @TableField("REGISTER_TIME")
    private LocalDateTime registerTime;

    /**
     * 84经度
     */
    @TableField("JD84")
    private String jd84;

    /**
     * 84纬度
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

    /**
     * system自增id
     */
    @TableField("OBJECT_ID")
    private Double objectId;

    /**
     * 事故类型
     */
    @TableField("ACCIDENT_TYPE")
    private String accidentType;

    /**
     * 创建时间
     */
    @TableField("YJJC_CREATE_TIME")
    private LocalDateTime yjjcCreateTime;

    /**
     * 更新时间
     */
    @TableField("YJJC_UPDATE_TIME")
    private LocalDateTime yjjcUpdateTime;

    @TableField("STREET_NAME")
    private String streetName;

    @TableField("COMMUNITY_NAME")
    private String communityName;


}
