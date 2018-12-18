package com.cetc.cloud.datasynch.provider.template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cetc.cloud.datasynch.api.model.XinFangEventModel;
import com.cetc.cloud.datasynch.api.model.XinFangPeopleModel;
import com.cetc.cloud.datasynch.provider.core.util.HttpClientUtil2;
import com.cetc.cloud.datasynch.provider.mapper.input.XinfangEventMapper;
import com.cetc.cloud.datasynch.provider.service.impl.DbOperateService;
import com.cetc.cloud.datasynch.provider.service.impl.DbQueryService;
import com.cetc.cloud.datasynch.provider.service.impl.HttpOperateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description：创建定时 执行计算 实例
 * Created by luolinjie on 2018/10/10.
 */
@Slf4j
public class XinfangGetRunnable implements OuterJobRunnableTemplate  {

    private DbQueryService dbQueryService;
    private DbOperateService dbOperateService;
    private HttpOperateService httpOperateService;

    public XinfangGetRunnable(DbQueryService dbQueryService, DbOperateService dbOperateService, HttpOperateService httpOperateService) {
        this.dbQueryService = dbQueryService;
        this.dbOperateService = dbOperateService;
        this.httpOperateService = httpOperateService;
    }

    @Autowired
    private XinfangEventMapper xinfangEventMapper;

    @Override
    public void run() {
        Thread.currentThread().setName("XINFANG_TODAY");
        try {
            insertXinfangDataToday();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertXinfangDataToday() throws SQLException {
        log.info("Started Scheduled Job:insertXinfangDataToday()");
        String SQL = "select URL,BODY from DS_OUTER_URLS where table_name='WEEKLY_XINFANG_TOKEN'";
        List<HashMap> SQLRes = dbOperateService.oracleQuerySql(SQL);
        String url = (String) SQLRes.get(0).get("URL");
        String body = (String) SQLRes.get(0).get("BODY");
        String tokenString = null;
        //todo 获取token
        JSONObject jsonObject = HttpClientUtil2.doPostWithBody(url, null, body);
        if (jsonObject.getInteger("code") == 200) {
            String tokenJson = jsonObject.getString("data");
            JSONObject tokenJsonObj = JSON.parseObject(tokenJson);
            tokenString = tokenJsonObj.getString("access_token");
            System.out.println("====>> get token:" + tokenString);
        } else {
            return;
        }
        //todo 在线请求
        if (tokenString != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String formatedDate = format.format(new Date());
            String SQL2 = "select URL from DS_OUTER_URLS where table_name='WEEKLY_XINFANG'";
            List<HashMap> SQLRes2 = dbOperateService.oracleQuerySql(SQL2);
            String url1 = (String) SQLRes2.get(0).get("URL");
            String URL = url1;
            JSONObject params = new JSONObject();
            params.put("access_token", tokenString);
            params.put("date", formatedDate);

            JSONObject httpQueryRes = HttpClientUtil2.doGet(URL, params);
            if (200 == httpQueryRes.getIntValue("code")) {
                String dataString = httpQueryRes.getString("data");
                JSONArray jsonRes = JSON.parseArray(dataString);
                JSONArray jsonRes1 = new JSONArray();
                Set<String> visitCodeSet = xinfangEventMapper.getVisitCodeList();
                Iterator<Object> iterator = jsonRes.iterator();
                while (iterator.hasNext()) {
                    JSONObject next = (JSONObject) iterator.next();
                    String VISITNO = next.getString("VISITNO");
                    if (!visitCodeSet.contains(VISITNO)) {
                        jsonRes1.add(next);
                    }
                }
                insertXinfangJSONData(jsonRes1);
            }
        }
    }

    private void insertXinfangJSONData(JSONArray jsonRes) {
//        int count = jsonRes.getIntValue("count");
        //todo 获取信访visitCode集合，用于比对重复值


        JSONArray dataArr = jsonRes;
        for (int i = 0; i < dataArr.size(); i++) {
            JSONObject obj = dataArr.getJSONObject(i);
            XinFangEventModel xinFangEventModel = new XinFangEventModel();
            xinFangEventModel.setVisitNo(obj.getString("VisitNo"));
            xinFangEventModel.setVisitTime(obj.getString("VisitTime"));
            xinFangEventModel.setVisitPersonnelNum(obj.getIntValue("VisitPersonnelNum"));
            xinFangEventModel.setContradiction(obj.getString("Contradiction"));
            xinFangEventModel.setMattersName(obj.getString("MattersName"));
            xinFangEventModel.setVisitMattersRemark(obj.getString("VisitMattersRemark"));
            xinFangEventModel.setEventAddress(obj.getString("EventAddress"));
            xinFangEventModel.setPerformanceName(obj.getString("PerformanceName"));
            xinFangEventModel.setDepartmentName(obj.getString("DepartmentName"));
            xinFangEventModel.setVisitPlace(obj.getString("VisitPlace"));
            xinFangEventModel.setOutAttitude(obj.getString("OutAttitude"));
            xinFangEventModel.setIsIncludedStatistics(String.valueOf(obj.getBoolean("IsIncludedStatistics")));
            xinFangEventModel.setVisitAddressName(obj.getString("VisitAddressName"));
            xinFangEventModel.setSource(obj.getString("Source"));
            xinFangEventModel.setVisitType(obj.getString("VisitType"));
            int i1 = xinfangEventMapper.addEvent(xinFangEventModel);
            if (i1 > 0) {
                log.info("added XinfangEvent:" + xinFangEventModel.toString());
            }
            JSONArray idCardInfos = obj.getJSONArray("IDCardInfos");
            for (int j = 0; j < idCardInfos.size(); j++) {
                JSONObject personObj = idCardInfos.getJSONObject(j);
                XinFangPeopleModel personModel = new XinFangPeopleModel();
                personModel.setVisitNo(obj.getString("VisitNo"));
                personModel.setName(personObj.getString("Name"));
                personModel.setSex(personObj.getString("Sex"));
                personModel.setDateofBirth(personObj.getString("DateOfBirth"));
                personModel.setAddress(personObj.getString("Address"));
                personModel.setCertKind(personObj.getString("CertKind"));
                personModel.setCertNo(personObj.getString("CertNo"));
                personModel.setIssuing(personObj.getString("Issuing"));
                personModel.setCensusRegister(personObj.getString("CensusRegister"));
                personModel.setNation(personObj.getString("Nation"));
                personModel.setCertificateValidity(personObj.getString("CertificateValidity"));
                personModel.setContactNumber(personObj.getString("ContactNumber"));
                personModel.setCertAddress(personObj.getString("CertAddress"));
                personModel.setIsMain(personObj.getIntValue("IsMain"));
                int i2 = xinfangEventMapper.addPerson(personModel);
                if (i2 > 0) {
                    log.info("added XinfangPeople:" + personModel.toString());
                }
            }
        }

    }
}


