package com.cetc.cloud.datasynch.provider.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Description：
 * Created by luolinjie on 2018/2/8.
 */
public class BaiduAPIUtil {
    public static void main(String[] args){
        String address = "观澜森林公园";
        String URL = "http://api.map.baidu.com/cloudgc/v1?address="+address+"&city=深圳市&ak=3lS2lFIKpRqclITYyzUgVB0cBkGbWI8R";
        String s = ConnectionUtil.sendGET(URL);

        JSONObject obj = JSON.parseObject(s);
        JSONArray array = obj.getJSONArray("result");
        JSONObject obj1 = array.getJSONObject(0);
        String district = obj1.getJSONObject("address_components").getString("district");
        JSONObject location = obj1.getJSONObject("location");
        String longitude = location.getString("lng");
        String latitude = location.getString("lat");

        System.out.println("行政区:"+district);
        System.out.println("经度:"+longitude+" ,纬度："+latitude);

    }


    public static String getDistrictByLocationName(String address ){
        String URL = "http://api.map.baidu.com/cloudgc/v1?address="+address+"&city=深圳市&ak=3lS2lFIKpRqclITYyzUgVB0cBkGbWI8R";
        String s = ConnectionUtil.sendGET(URL);

        JSONObject obj = JSON.parseObject(s);
        JSONArray array = obj.getJSONArray("result");
        JSONObject obj1 = array.getJSONObject(0);
        String district = obj1.getJSONObject("address_components").getString("district");
        JSONObject location = obj1.getJSONObject("location");
        System.out.println("解析到行政区:"+district);

        return district;
    }

    public static String[] getCoordinateByLocationName(String address ) throws InterruptedException {
        Thread.sleep(300);
        String URL = "http://api.map.baidu.com/cloudgc/v1?address="+address+"&city=深圳市&ak=3lS2lFIKpRqclITYyzUgVB0cBkGbWI8R";
        String s = ConnectionUtil.sendGET(URL);

        JSONObject obj = JSON.parseObject(s);
        JSONArray array = obj.getJSONArray("result");
        if (array.size()==0){
            return null;
        }
        JSONObject obj1 = array.getJSONObject(0);
        if (obj1.size()>0){
            String district = obj1.getJSONObject("address_components").getString("district");
            JSONObject location = obj1.getJSONObject("location");
            String longitude = location.getString("lng");
            String latitude = location.getString("lat");
            String[] strings = {longitude,latitude};
            System.out.println("解析"+address+",---经度:"+longitude+" ,--纬度："+latitude);
            return strings;
        }
        return null;
    }
}
