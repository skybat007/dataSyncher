package com.cetc.cloud.datasynch.provider.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * Created by llj on 2018/10/14.
 */
public class JsonUtil {
    public static List<HashMap> parseArray2List(JSONArray array) {
        List<HashMap> list = new ArrayList<HashMap>();
        if (null != array) {
            Iterator<Object> iterator = array.iterator();
            while (iterator.hasNext()) {
                JSONObject next = (JSONObject) iterator.next();
                Set<String> keySet = next.keySet();
                Iterator<String> iterator1 = keySet.iterator();
                HashMap<String,String> map = new HashMap<String, String>();
                while (iterator1.hasNext()){
                    String next1 = iterator1.next();
                    map.put(next1,next.getString(next1));
                }
                list.add(map);
            }
            return list;
        } else {
            return null;
        }
    }
}
