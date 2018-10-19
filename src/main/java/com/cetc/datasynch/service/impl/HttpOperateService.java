package com.cetc.datasynch.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cetc.datasynch.common.CommonConfig;
import com.cetc.datasynch.core.util.HttpUtil;
import com.cetc.datasynch.core.util.JsonUtil;
import com.cetc.datasynch.model.ScheduleModel;
import com.cetc.datasynch.model.Token;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Http在线请求服务
 * Created by llj on 2018/10/11.
 */
@Service
public class HttpOperateService implements com.cetc.datasynch.service.HttpOperateService {

    @Override
    public List<HashMap> doHttpQuery(ScheduleModel model, int pageNum) {
        //获取URL
        String URL = model.getSource();
        List<HashMap> listData = null;
        //组装参数 pageNum和pageSize
        JSONObject params = new JSONObject();
        params.put(CommonConfig.PAGE_NUM_NAME, String.valueOf(pageNum));
        int pageSize = model.getPageSize();
        params.put(CommonConfig.PAGE_SIZE_NAME, String.valueOf(pageSize));

        //获取token
        Token token = model.getToken();

        //获取json解析规则
        String jsonExtractRule = model.getJsonExtractRule();

        JSONObject httpResult = HttpUtil.doGetWithAuthoration(URL, params, token);
        if (200==Integer.parseInt((String)httpResult.get("code"))){
            String data = (String) httpResult.get("data");

            JSONObject jsonResultData = JSONObject.parseObject(data);

            listData = getListData(jsonResultData,jsonExtractRule);
        }
        return listData;
    }

    /**
     * 根据传入json解析规则，获取JsonArray形式的Data主体
     */
    public List<HashMap> getListData(JSONObject jsonResultData, String jsonExtractRule) {
        String[] splits = jsonExtractRule.split("\\.");

        int size = splits.length;

        JSONObject extractJson = jsonResultData;
        JSONArray arrData = null;

        for (int i=0;i<size;i++){
            if (i==size-1){
                arrData = extractJson.getJSONArray(splits[i]);
            }else {
                extractJson = extractJson.getJSONObject(splits[i]);
            }
        }
        List<HashMap> list = JsonUtil.parseArray2List(arrData);
        return list;
    }

}
