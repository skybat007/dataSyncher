package com.cetc.cloud.datasynch.provider.util.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PackageName:   com.cetc.cloud.alarm_push.provider.common
 * projectName:   alarm-push-service
 * Description:   LuoLinjie 补充
 * Creator:     by LuoLinjie
 * Create_Date: 2019/2/25 15:26
 * Updater:     by Administrator
 * Update_Date: 2019/2/25 15:26
 * Update_Description: Administrator 补充
 */
@Data
@NoArgsConstructor
public class PostModel {
    public String url ;
    public JSONObject params;
    public JSONObject header;
    public JSONObject body;

    public PostModel(String url, JSONObject params, JSONObject header, JSONObject body) {
        this.url = url;
        this.params = params;
        this.header = header;
        this.body = body;
    }
}
