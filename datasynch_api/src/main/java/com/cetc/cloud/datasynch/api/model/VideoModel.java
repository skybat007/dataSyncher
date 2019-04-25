package com.cetc.cloud.datasynch.api.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * PackageName:   com.cetc.cloud.datasynch.api.model
 * projectName:   dataSyncher
 * Description:   LuoLinjie 补充
 * Creator:     by LuoLinjie
 * Create_Date: 2019/3/5 15:59
 * Updater:     by Administrator
 * Update_Date: 2019/3/5 15:59
 * Update_Description: Administrator 补充
 */
@Data
@TableName("VIDEO_POLICE")
public class VideoModel {

    @TableField("CAMERAID")
    String cameraId;
//    @TableField("ID")
//    int id;
    @TableField("DEVICE_CHANNEL_ID")
    String devicechanid;
    @TableField("DISTRICT")
    String orgName;         //所属机构
    @TableField("GB_CODE")
    String gbCode;
    @TableField("ADDRESS")
    String address;
    @TableField("MAINT_UNIT")
    String maintunit;       //维保单位
    @TableField("NAME")
    String cameraName;
    @TableField("IP")
    String gzsb;
    @TableField("START_DATE")
    Date startDate;
    @TableField("STATE")
    String state;
    @TableField("RECORD")
    String record;
    @TableField("CATEGORY")
    String constcategory;       //建设类型
    @TableField("APPEARANCE")
    String cameratypedesc;      //外观：固定枪机 等
    @TableField("RESOLUTION")
    String cameraqualitydesc;//清晰度 :高清 等
    @TableField("CREATE_TIME")
    Date createTime;
    @TableField("UPDATE_TIME")
    Date updateTime;
    @TableField("UPDATE_STATUS")
    String updateStatus;
    @TableField("JD84")
    String jd84;
    @TableField("WD84")
    String wd84;
    @TableField("MONITOR_ANGLE")
    String jkfw;

}
