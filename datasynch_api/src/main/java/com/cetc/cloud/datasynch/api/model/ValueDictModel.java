package com.cetc.cloud.datasynch.api.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * PackageName:   com.cetc.cloud.datasynch.api.model
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/12/5 15:57
 * Updater:     by luolinjie
 * Update_Date: 2018/12/5
 * Update_Description: luolinjie 补充
 **/
@Data
@TableName("DS_VALUE_DICT")
public class ValueDictModel implements Serializable {
    @TableField("OBJECT_ID")
    private String objectId;
    @TableField("TABLE_NAME")
    private String tableName;
    @TableField("COLUMN_NAME")
    private String columnName;
    @TableField("CODE")
    private String code;
    @TableField("CODE_IN_CHINESE")
    private String codeInChinese;
    @TableField("TB_IN_CHINESE")
    private String tbInChinese;
    @TableField("COL_IN_CHINESE")
    private String colInChinese;
    @TableField("CREATE_TIME")
    private String createTime;
    @TableField("UPDATE_TIME")
    private String updateTime;
}
