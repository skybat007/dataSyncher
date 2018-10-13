package com.cetc.datasynch.service;

import com.alibaba.fastjson.JSONObject;
import com.cetc.datasynch.common.CommonConfig;
import com.cetc.datasynch.core.util.HttpUtil;
import com.cetc.datasynch.model.ScheduleModel;
import com.cetc.datasynch.model.Token;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Http在线发起服务
 * Created by llj on 2018/10/11.
 */
@Service
public class HttpOperateService {

    public List<HashMap> doHttpQuery(ScheduleModel model, int pageNum) {
        //获取URL
        String URL = model.getSource();

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
        if (200==httpResult.get("code")){
            String data = (String) httpResult.get("data");

            JSONObject jsonResultData = JSONObject.parseObject(data);

            List<JSONObject> listData = getListData(jsonResultData,jsonExtractRule);


        }


        return null;
    }

    private List<JSONObject> getListData(JSONObject jsonResultData, String jsonExtractRule) {


    }
}
