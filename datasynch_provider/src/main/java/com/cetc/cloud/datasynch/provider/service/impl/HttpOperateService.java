package com.cetc.cloud.datasynch.provider.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.api.model.Token;
import com.cetc.cloud.datasynch.provider.core.util.HttpUtil;
import com.cetc.cloud.datasynch.provider.core.util.JsonUtil;
import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Http在线请求服务
 * Created by llj on 2018/10/11.
 */
@Service("httpOperateService")
public class HttpOperateService implements com.cetc.cloud.datasynch.provider.service.HttpOperateService {

    @Override
    public List<HashMap> doHttpQuery(ScheduleModel model, int pageNum) {
        //获取URL
        String URL = model.getSource();
        List<HashMap> listData = null;

        //组装参数 pageNum和pageSize
        JSONObject params = new JSONObject();
        params.put(CommonInstance.PAGE_NUM_NAME, String.valueOf(pageNum));
        int pageSize = model.getPageSize();
        params.put(CommonInstance.PAGE_SIZE_NAME, String.valueOf(pageSize));

        //获取json解析规则
        String jsonExtractRule = model.getJsonExtractRule();

        //获取token
        String tokenStr = model.getToken();

        JSONObject httpResult = null;
        if (null != tokenStr && !"".equals(tokenStr)) {
            Token token = new Token();
            if (tokenStr.contains(":")) {
                String[] split = tokenStr.split(":");
                if ("".equals(split[0]) && "".equals(split[1])) {
                    token.setKey(split[0]);
                    token.setValue(split[1]);
                }
            }
            httpResult = HttpUtil.doGetWithAuthoration(URL, params, token);
        } else {
            httpResult = HttpUtil.doGet(URL,params);
        }

        //解析，并生成结果数据集
        if (200 == Integer.parseInt((String) httpResult.get("code"))) {
            String data = (String) httpResult.get("data");
            JSONObject jsonResultData = JSONObject.parseObject(data);

            listData = ExtractListData(jsonResultData, jsonExtractRule);
        }
        return listData;
    }

    /**
     * 根据传入json解析规则 获取JsonArray形式的Data主体
     */
    public List<HashMap> ExtractListData(JSONObject jsonResultData, String jsonExtractRule) {
        String[] splits = jsonExtractRule.split("\\.");

        int size = splits.length;

        JSONObject extractJson = jsonResultData;
        JSONArray arrData = null;

        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                arrData = extractJson.getJSONArray(splits[i]);
            } else {
                extractJson = extractJson.getJSONObject(splits[i]);
            }
        }
        List<HashMap> list = JsonUtil.parseArray2List(arrData);
        return list;
    }

}
