package com.cetc.cloud.datasynch.provider.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.api.model.Token;
import com.cetc.cloud.datasynch.provider.core.util.HttpUtil;
import com.cetc.cloud.datasynch.provider.core.util.JsonUtil;
import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Http在线请求服务
 * Created by llj on 2018/10/11.
 */
@Service("httpOperateService")
public class HttpOperateService implements com.cetc.cloud.datasynch.provider.service.HttpOperateService {

    private Logger logger = LoggerFactory.getLogger(HttpOperateService.class);
    @Override
    public List<HashMap> doHttpQuery(ScheduleModel model, int pageNum) {
        //获取URL
        String URL = model.getSource();
        List<HashMap> listData = null;

        //获取拼接后的请求参数对象
        JSONObject httpParams = getHttpParams(model, pageNum);

        //获取token
        String tokenStr = model.getHttpToken();
        JSONObject httpResult = null;
        if (null != tokenStr && !"".equals(tokenStr)) {
            Token token = new Token();
            if (tokenStr.contains("=")) {
                String[] split = tokenStr.split("=");
                if ("".equals(split[0]) && "".equals(split[1])) {
                    token.setKey(split[0]);
                    token.setValue(split[1]);
                }
            }
            httpResult = HttpUtil.doGetWithAuthoration(URL, httpParams, token);
        } else {
            httpResult = HttpUtil.doGet(URL, httpParams);
        }

        if (null != httpResult) {
            //获取json解析规则
            String jsonExtractRule = model.getHttpJsonExtractRule();
            //解析，并生成结果数据集
            if (200 == (Integer) httpResult.get(CommonInstance.HTTP_RES_CODE)) {
                String data = (String) httpResult.get("data");
                JSONObject jsonResultData = JSONObject.parseObject(data);

                listData = ExtractListData(jsonResultData, jsonExtractRule);
            }
        } else {
            return null;
        }

        return listData;
    }

    /**
     * 根据传入json解析规则 获取JsonArray形式的Data主体
     */
    public List<HashMap> ExtractListData(JSONObject jsonResultData, String jsonExtractRule) {
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
            List<HashMap> list = JsonUtil.parseArray2List(arrData);
            return list;
        }else {
            arrData = jsonResultData.getJSONArray(jsonExtractRule);
            List<HashMap> list = JsonUtil.parseArray2List(arrData);
            return list;
        }
    }

    /**
     * 根据传入json解析规则 获取JsonArray形式的Data主体
     */
    public int ExtractTotalData(JSONObject jsonResultData, String totalExtractRule) {

        String[] splits = null;
        int size = 1;
        JSONObject extractTotal = jsonResultData;
        String total = null;

        if (totalExtractRule.contains("\\.")) {
            splits = totalExtractRule.split("\\.");
            size = splits.length;
            for (int i = 0; i < size; i++) {
                if (i == size - 1) {
                    total = extractTotal.getString(splits[i]);
                } else {
                    extractTotal = extractTotal.getJSONObject(splits[i]);
                }
            }
        } else {
            total = extractTotal.getString(totalExtractRule);
        }

        if (null != total) {
            return Integer.parseInt(total);
        }
        return 0;
    }

    private JSONObject getHttpParams(ScheduleModel model, int pageNum) {
        JSONObject httpQueryParams = new JSONObject();
        String httpParamExpression = model.getHttpParamExpression();
        String[] paramKeyValues = httpParamExpression.split("&");

        for (int i = 0; i < paramKeyValues.length; i++) {
            String[] split = paramKeyValues[i].split("=");
            if (split.length == 2) {
                String key = split[0];
                String value = split[1];
                if (model.getHttpParamPageNum().equals(key) || model.getHttpParamPageSize().equals(key)) {
                    continue;
                } else {
                    httpQueryParams.put(key, value);
                }
            } else {
                continue;
            }
        }

        //如果该接口需要使用分页来查询的话，就需要添加这个动态参数
        if (null != model.getHttpParamPageNum() && null != model.getHttpParamPageSize()) {
            //组装参数 pageNum和pageSize
            httpQueryParams.put(model.getHttpParamPageNum(), String.valueOf(pageNum));
            int pageSize = model.getPageSize();
            httpQueryParams.put(model.getHttpParamPageSize(), String.valueOf(pageSize));


        }
        return httpQueryParams;
    }

    public int getTotalRows(ScheduleModel model) {
        //获取URL
        String URL = model.getSource();
        int total = 0;
        JSONObject httpParams = null;
        //如果该接口需要使用分页来查询的话，就需要添加这个动态主键
        if (null != model.getHttpParamPageNum() && null != model.getHttpParamPageSize()) {
            //组装参数 pageNum和pageSize
            httpParams = new JSONObject();
            httpParams.put(CommonInstance.PAGE_NUM_NAME, String.valueOf(1));
            httpParams.put(CommonInstance.PAGE_SIZE_NAME, String.valueOf(1));//测试,pageSize设为1
        }
        //获取json解析规则
        String totalExtractRule = model.getHttpTotalExtractRule();

        String httpParamExpression = model.getHttpParamExpression();
        if (null != httpParamExpression) {
            String[] split = httpParamExpression.split("&");
            for (int i = 0; i < split.length; i++) {
                String param = split[i];
                String[] k_v = param.split("=");
                httpParams.put(k_v[0], k_v[1]);
            }
        }
        //获取token
        String tokenStr = model.getHttpToken();
        JSONObject httpResult = null;
        if (null != tokenStr && !"".equals(tokenStr)) {
            Token token = new Token();
            if (tokenStr.contains("=")) {
                String[] split = tokenStr.split("=");
                if ("".equals(split[0]) && "".equals(split[1])) {
                    token.setKey(split[0]);
                    token.setValue(split[1]);
                }
            }
            httpResult = HttpUtil.doGetWithAuthoration(URL, httpParams, token);
        } else {
            httpResult = HttpUtil.doGet(URL, httpParams);
        }

        //解析，并生成结果数据集
        if (200 == (Integer) httpResult.get(CommonInstance.HTTP_RES_CODE)) {
            String data = (String) httpResult.get("data");
            JSONObject jsonResultData = JSONObject.parseObject(data);

            total = ExtractTotalData(jsonResultData, totalExtractRule);
            logger.info("\n---->>> getTotalRows of URL:"+model.getSource()+" is :"+total);
        }

        return total;
    }
}
