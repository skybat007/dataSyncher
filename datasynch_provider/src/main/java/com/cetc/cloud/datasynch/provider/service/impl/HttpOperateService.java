package com.cetc.cloud.datasynch.provider.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.api.model.SynchJobLogInfoModel;
import com.cetc.cloud.datasynch.api.model.Token;
import com.cetc.cloud.datasynch.provider.core.util.HttpClientUtil2;
import com.cetc.cloud.datasynch.provider.core.util.HttpUtil;
import com.cetc.cloud.datasynch.provider.core.util.JsonExtractor;
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
public class HttpOperateService {

    private Logger logger = LoggerFactory.getLogger(HttpOperateService.class);

    /**
     * 通过HTTP协议请求对应的URL获取数据
     *
     * @param model
     * @param pageNum
     * @return
     */
    public List<HashMap> doHttpQueryList(ScheduleModel model, int pageNum) {
        //获取URL
        String URL = model.getSource();
        List<HashMap> listData = null;
        JSONObject httpParams = null;

        httpParams = getHttpParams(model, pageNum);

        //获取token
        String tokenStr = model.getHttpToken();
        JSONObject httpResult;
        if (null != tokenStr && !"".equals(tokenStr)) {
            Token token = new Token();
            if (tokenStr.contains(":")) {
                String[] split = tokenStr.split(":");
                if (!"".equals(split[0]) && !"".equals(split[1])) {
                    token.setKey(split[0]);
                    token.setValue(split[1]);
                }
            }
            httpResult = HttpClientUtil2.doGetWithAuthoration(URL, httpParams, token);
        } else {
            httpResult = HttpClientUtil2.doGet(URL, httpParams);
        }

        //获取json解析规则
        String jsonExtractRule = model.getHttpJsonExtractRule();
        //解析，并生成结果数据集
        if (200 == (Integer)httpResult.get(CommonInstance.HTTP_RES_CODE)) {
            String data = (String) httpResult.get("data");
            listData = JsonExtractor.ExtractListData(data, jsonExtractRule);
        }

        return listData;
    }

    public int getHttpCurrentPageTotalRows(ScheduleModel model, SynchJobLogInfoModel logInfoModel) {
        List<HashMap> queryResult = doHttpQueryList(model, logInfoModel.getLastQueryPageNum());
        if (null != queryResult) {
            return queryResult.size();
        } else {
            return 0;
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
        if (null != totalExtractRule) {
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
        }
        if (null != total) {
            return Integer.parseInt(total);
        }
        return 0;
    }

    private JSONObject getHttpParams(ScheduleModel model, int pageNum) {
        JSONObject httpQueryParams = new JSONObject();
        String httpParamExpression = model.getHttpParamExpression();

        if (null != httpParamExpression) {
            String[] paramKeyValues = httpParamExpression.split("&");

            for (int i = 0; i < paramKeyValues.length; i++) {
                String[] split = paramKeyValues[i].split("=");
                if (split.length == 2) {
                    String key = split[0];
                    String value = split[1];
                    if (CommonInstance.DO_PAGING == model.getIsPagingQuery()) {
                        if (model.getHttpParamPageNum().equals(key) || model.getHttpParamPageSize().equals(key)) {
                            continue;
                        } else {
                            httpQueryParams.put(key, value);
                        }
                    }else if (CommonInstance.NO_PAGING == model.getIsPagingQuery()){
                        httpQueryParams.put(key, value);
                    }
                } else {
                    continue;
                }
            }
        }

        //如果该接口需要使用分页来查询的话，就需要添加这个动态参数
        if (null != model.getHttpPagingType() && null != model.getHttpParamPageNum() && null != model.getHttpParamPageSize()) {
            /**一般类型：pageNum=1&pageSize=100*/
            if (CommonInstance.HTTP_PAGING_TYPE_NORMAL .equals( model.getHttpPagingType())) {
                //组装参数 pageNum和pageSize
                httpQueryParams.put(model.getHttpParamPageNum(), String.valueOf(pageNum));
                httpQueryParams.put(model.getHttpParamPageSize(), String.valueOf(model.getPageSize()));
                /**安监接口类型:page={"pagenum":"1","pagesize":"50" }*/
            } else if (CommonInstance.HTTP_PAGING_TYPE_JSON_QAJJ .equals( model.getHttpPagingType())) {
                JSONObject innerPageParam = new JSONObject();
                innerPageParam.put(CommonInstance.HTTP_PAGING_TYPE_JSON_QAJJ_key_pagenum, String.valueOf(pageNum));
                innerPageParam.put(CommonInstance.HTTP_PAGING_TYPE_JSON_QAJJ_key_pagesize, String.valueOf(model.getPageSize()));
                httpQueryParams.put(model.getHttpParamPageNum(), innerPageParam);
                /**城管案件：STARTPOSITION=0&MAXCOUNT=1000*/
            } else if (CommonInstance.HTTP_PAGING_TYPE_COUNT .equals( model.getHttpPagingType())) {
                int startPosition = (pageNum - 1) * model.getPageSize();
                httpQueryParams.put(CommonInstance.HTTP_PAGING_TYPE_COUNT_key_chengguan, String.valueOf(startPosition));
                httpQueryParams.put(model.getHttpParamPageSize(), String.valueOf(model.getPageSize()));
            }
        }
        return httpQueryParams;
    }


}
