package com.cetc.cloud.datasynch.provider.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by llj on 2018/10/14.
 */
public class JsonExtractor {

    static Logger logger = LoggerFactory.getLogger(JsonExtractor.class);

    /**
     * 函数功能：根据传入json解析规则 获取"JsonObject"中的"JsonArray"形式的Data主体，并以List\<HashMap\>形式返回
     * <p>
     * 注：jsonExtractRule语法：
     * 1.每个Extract表达式必须有 "*"
     * 2.每个Extract表达式有0到若干个"."
     * 3.jsonData中的层级标识key之间，用"."隔开
     * 4."*"之前的表达式为JSONObject格式抽取
     * 5."*"之后的表达式为JSONArray中抽取，需要遍历
     **/
    public static List<HashMap> ExtractListData(String data, String jsonExtractRule) {
        if (null == jsonExtractRule || "".equals(jsonExtractRule) || null == data || "".equals(data)) {
            return null;
        }
        List<HashMap> resListMap = null;

        if (!jsonExtractRule.contains("*")) {//jsonExtractRule必须包含*
            return null;
        }
        if (jsonExtractRule.contains("*")) {// *的个数只能为1个
            char[] chars = jsonExtractRule.toCharArray();
            int count = 0;
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '*') {
                    count++;
                }
            }
            if (count > 1) {
                logger.error("number of \'*\' cannot more than 1!");
                return null;
            }
        }

        if (jsonExtractRule.contains(".")) {
            if (jsonExtractRule.startsWith("*") && data.startsWith("[") && data.endsWith("]")) {//"*"在头部
                JSONArray array = JSON.parseArray(data);//将data转换成JSONArray
                Iterator<Object> dataIterator = array.iterator();
                //对提取规则进行预处理，去除不必要的"*"
                String[] split = jsonExtractRule.split("\\.");
                List<String> rule2list = Arrays.asList(split);
                if (rule2list.size() == 0) {
                    logger.error("rule2list.size()==0, please check your jsonExtractRule!");
                    return null;
                }
                List<String> subExtractRule1 = rule2list.subList(1, rule2list.size());

                while (dataIterator.hasNext()) {
                    JSONObject nextData = (JSONObject) dataIterator.next();
                    JSONObject jsonObjectData = null;
                    for (String subRule : subExtractRule1) {
                        jsonObjectData = nextData.getJSONObject(subRule);
                    }
                    //将获取到的结果抽取到List<HashMap>中
                    Set<String> keySet = jsonObjectData.keySet();
                    Iterator<String> iterator1 = keySet.iterator();
                    HashMap<String, String> map = new HashMap<String, String>();
                    while (iterator1.hasNext()) {
                        String next1 = iterator1.next();
                        map.put(next1, nextData.getString(next1));
                    }
                    resListMap.add(map);
                }
                return resListMap;
            } else if (jsonExtractRule.endsWith("*") && data.startsWith("{") && data.endsWith("}")) {//"*"在尾部

                JSONObject objectData = JSON.parseObject(data);
                String[] split = jsonExtractRule.split("\\.");
                List<String> rule2list = Arrays.asList(split);
                if (rule2list.size() == 0) {
                    logger.error("rule2list.size()==0, please check your jsonExtractRule!");
                    return null;
                }
                List<String> subExtractRule1 = rule2list.subList(0, rule2list.size() - 1);
                JSONArray jsonArray = null;
                for (int i = 0; i < subExtractRule1.size(); i++) {
                    if (i == subExtractRule1.size() - 1) {
                        jsonArray = objectData.getJSONArray(subExtractRule1.get(i));
                    } else {
                        objectData = objectData.getJSONObject(subExtractRule1.get(i));
                    }
                }
                return parseArray2List(jsonArray);

            } else if (jsonExtractRule.matches("^\\w+[\\.\\w+]*\\.\\*[\\.\\w+]*\\.\\w+$") && data.startsWith("{") && data.endsWith("}")) {//"*"在中间
                JSONObject objectData = JSON.parseObject(data);
                String[] split = jsonExtractRule.split("\\*");
                List<String> objectExtractRule = getSubRule(split[0]);
                List<String> arrayExtractRule = getSubRule(split[1]);
                JSONArray array = getJSONArrayInJSONObj(objectData, objectExtractRule);
                JSONArray array1 = getArrayInJSONArray(array, arrayExtractRule);

                return parseArray2List(array1);
            }
        }else if (jsonExtractRule.equals("*") && data.startsWith("[") && data.endsWith("]")) {//只有"*"
            JSONArray array1 = JSON.parseArray(data);
            return parseArray2List(array1);
        }
        return null;
    }

    private static JSONArray getArrayInJSONArray(JSONArray array, List<String> arrayExtractRule) {
        if (null == array || null == arrayExtractRule || "".equals(arrayExtractRule)) {
            return null;
        }
        JSONArray resArray = new JSONArray();
        Iterator<Object> iterator = array.iterator();
        while (iterator.hasNext()) {
            JSONObject objectData = (JSONObject) iterator.next();
            for (int i = 0; i < arrayExtractRule.size(); i++) {
                objectData = objectData.getJSONObject(arrayExtractRule.get(i));
            }
            resArray.add(objectData);
        }
        return resArray;
    }

    private static JSONArray getJSONArrayInJSONObj(JSONObject objectData, List<String> subExtractRule) {
        JSONArray resArray = new JSONArray();
        for (int i = 0; i < subExtractRule.size(); i++) {
            if (i == subExtractRule.size() - 1) {
                resArray = objectData.getJSONArray(subExtractRule.get(i));
            } else {
                objectData = objectData.getJSONObject(subExtractRule.get(i));
            }
        }
        return resArray;
    }

    public static List<String> getSubRule(String s) {

        String[] split = s.split("\\.");
        ArrayList<String> resList = new ArrayList<String>();
        List<String> list = Arrays.asList(split);
        for (int i = 0; i < list.size(); i++) {
            if (null == list.get(i) || "".equals(list.get(i))) {
                continue;
            } else {
                resList.add(list.get(i));
            }
        }
        return resList;
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
