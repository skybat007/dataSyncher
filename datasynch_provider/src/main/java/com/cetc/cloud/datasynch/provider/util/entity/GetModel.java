package com.cetc.cloud.datasynch.provider.util.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.ToString;

/**
 * PackageName:   com.cetc.cloud.common
 * projectName:   alarm_push_service
 * Description:   LuoLinjie 补充
 * Creator:     by LuoLinjie
 * Create_Date: 2019/2/26 11:19
 * Updater:     by Administrator
 * Update_Date: 2019/2/26 11:19
 * Update_Description: Administrator 补充
 */
@Data
@ToString
public class GetModel {
    public String url ;
    public JSONObject params;
    public JSONObject header;
}
