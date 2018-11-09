package com.cetc.cloud.datasynch.provider.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * Created by llj on 2018/10/14.
 */
public class JsonExtractor {
    /**
     * 根据传入json解析规则 获取"JsonObject"中的"JsonArray"形式的Data主体
     */
    public static List<HashMap> ExtractListData(JSONObject jsonResultData, String jsonExtractRule) {
        String[] splits = null;
        JSONArray arrData = null;
        splits = jsonExtractRule.split("\\.");
        List<String> list = Arrays.asList(splits);


        int size = splits.length;


        getArrayContent(jsonResultData, list);

        List<HashMap> list2 = parseArray2List(arrData);

        return list2;
    }

    private static JSONArray getArrayContent(JSONObject jsonResultData, List<String> jsonListQuery) {

//        if (jsonListQuery.size() != 0) {
//            if (!"*".equals(jsonListQuery.get(0))){
//                JSONObject jsonObject = jsonResultData.getJSONObject(jsonListQuery.get(0));
//                getArrayContent();
//            }else if ("*".equals(jsonListQuery.get(0))){
//                jsonResultData.getJSONArray();
//                return jsonResultData;
//            }
//            jsonListQuery = jsonListQuery.subList(1, jsonListQuery.size());
//        }
        return null;
    }

    /**
     * 根据传入json解析规则 获取"JsonObject"中的"JsonArray"形式的Data主体
     */
    public static List<HashMap> ExtractListData1(JSONObject jsonResultData, String jsonExtractRule) {
        String[] splits = null;
        JSONArray arrData = null;
        if (jsonExtractRule.contains("\\.")) {
            splits = jsonExtractRule.split("\\.");

            int size = splits.length;

            JSONObject extractJson = jsonResultData;

            for (int i = 0; i < size; i++) {
                if (i == size - 1) {
                    arrData = extractJson.getJSONArray(splits[i]);
                } else {
                    extractJson = extractJson.getJSONObject(splits[i]);
                }
            }
            List<HashMap> list = JsonExtractor.parseArray2List(arrData);
            return list;
        } else {
            arrData = jsonResultData.getJSONArray(jsonExtractRule);
            List<HashMap> list = JsonExtractor.parseArray2List(arrData);
            return list;
        }
    }

    /**
     * 根据传入json解析规则 从JSONArray对象中获取"JsonArray"形式的Data主体
     */
    public static List<HashMap> ExtractListData2(JSONArray jsonResultData) {
        List<HashMap> list = JsonExtractor.parseArray2List(jsonResultData);
        return list;
    }


    public static List<HashMap> parseArray2List(JSONArray array) {
        List<HashMap> list = new ArrayList<HashMap>();
        if (null != array) {
            Iterator<Object> iterator = array.iterator();
            while (iterator.hasNext()) {
                JSONObject next = (JSONObject) iterator.next();
                Set<String> keySet = next.keySet();
                Iterator<String> iterator1 = keySet.iterator();
                HashMap<String, String> map = new HashMap<String, String>();
                while (iterator1.hasNext()) {
                    String next1 = iterator1.next();
                    map.put(next1, next.getString(next1));
                }
                list.add(map);
            }
            return list;
        } else {
            return null;
        }
    }
}
