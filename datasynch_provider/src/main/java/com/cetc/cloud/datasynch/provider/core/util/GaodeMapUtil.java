package com.cetc.cloud.datasynch.provider.core.util;

import com.alibaba.fastjson.JSONObject;

/**
 * Description：高德在线地址解析工具
 * Created by luolinjie on 2018/4/18.
 */
public class GaodeMapUtil {

    public static final String key = "e49ee3f1dd13f8664c3d289188b8a52c";
    public static final String URL = "http://restapi.amap.com/v3/geocode/geo";

    public static JSONObject getOnlineCoordinates(String address, String city) {

        JSONObject params = new JSONObject();
        params.put("key", key);
        params.put("address", address);
        params.put("city", city);
        JSONObject res = HttpUtil.doGet(URL, params);

        return res;
    }

    public static void main(String[] args){

        JSONObject res = getOnlineCoordinates("金地工业区 102栋1楼 嘉景装饰材料", "深圳");

        if (res == null){
            System.out.println("error! null value");
        }
        String coordinates = res.getJSONObject("data").getJSONArray("geocodes").getJSONObject(0).getString("location");

        System.out.println(coordinates);
    }
}
