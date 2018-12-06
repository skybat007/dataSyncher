package com.cetc.cloud.datasynch.provider.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cetc.cloud.datasynch.api.model.XinFangEventModel;
import com.cetc.cloud.datasynch.api.model.XinFangPeopleModel;
import com.cetc.cloud.datasynch.api.service.SingleJobRemoteService;
import com.cetc.cloud.datasynch.provider.core.util.HttpClientUtil2;
import com.cetc.cloud.datasynch.provider.mapper.input.XinfangEventMapper;
import com.cetc.cloud.datasynch.provider.service.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.controller
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/11/28 11:09
 * Updater:     by luolinjie
 * Update_Date: 2018/11/28
 * Update_Description: luolinjie 补充
 **/
@RestController
public class SingleJobController implements SingleJobRemoteService {
    private Logger logger = LoggerFactory.getLogger(SingleJobController.class);

    @Autowired
    private DbOperateService dbOperateService;
    @Autowired
    private SynchJobLogInfoService synchJobLogInfoService;
    @Autowired
    private DbQueryService dbQueryService;
    @Autowired
    private HttpOperateService httpOperateService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private JobManageService jobManageService;

    @Autowired
    private XinfangEventMapper xinfangEventMapper;

    //    @Scheduled(cron = "00 00 23 * * ?")
    @Override
    public void truncateTable() {
        //todo 执行备份
        dbOperateService.backUpTable("BLK_SANXIAO_PLACE");
//        //todo 执行Truncate操作
//        dbOperateService.truncateTableByTbName("BLK_SANXIAO_PLACE");
//        logger.info("DAYLY_REPEATE_JOB: truncated table: BLK_SANXIAO_PLACE");
//        //todo 重新拉取全表
//        ScheduleModel scheduleModel = scheduleService.queryModelByJobId(157);
//        jobManageService.startOnceJob(scheduleModel);
    }

    @Scheduled(cron = "00 15 23 * * ?")
    @Override
    public void calculateHasTroubleSanXiao() throws SQLException {

        //获取有未处理事件的三小场所ID的list
        String getTroublePlaceIds = "SELECT  a.\"ID\"\n" +
                "from BLK_SANXIAO_PLACE a,BLK_CHENGGUAN_EVENT b\n" +
                "WHERE a.name=b.EVENT_NAME\n" +
                "and a.ADDRESS=b.ADDRESS";
        List<String> troublePlaceIdList = dbOperateService.oracleQueryList(getTroublePlaceIds);
        int totalSuccessCount = 0;
        for (int i = 0; i < troublePlaceIdList.size(); i++) {
            String sql = "update BLK_SANXIAO_PLACE set HAS_TROUBLE=1 where id='" + troublePlaceIdList.get(i) + "'";
            int count = dbOperateService.oracleUpdateSql(sql);
            if (count > 0) {
                totalSuccessCount++;
            }
        }
        logger.info("\nCalculateHasTroubleSanXiao:success: " + totalSuccessCount + "\n");
    }

    @Scheduled(cron = "00 16 23 * * ?")
    public void calculateRealSanXiaoCount() throws SQLException {
        String SQL = "select count(1) from BLK_SANXIAO_PLACE where status='0'";
        List<String> list = dbOperateService.oracleQueryList(SQL);
        String countNum = null;
        if (list.size() > 0) {
            countNum = list.get(0);
        } else {
            return;
        }

        String SQL2 = "update BLK_SANXIAO_PLACE_COUNT set COUNT='" + countNum + "'";
        int i = dbOperateService.oracleUpdateSql(SQL2);
        if (i > 0) {
            logger.info("\n Finished! calculateRealSanXiaoCount" + countNum + "\n");
        }

    }

    @Scheduled(cron = "00 40 17 * * ?")
    @Override
    public void insertXinfangDataToday() throws SQLException {
        logger.info("Started Scheduled Job:insertXinfangDataToday()");
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
                while (iterator.hasNext()){
                    JSONObject next = (JSONObject) iterator.next();
                    String VISITNO = next.getString("VISITNO");
                    if(!visitCodeSet.contains(VISITNO)){
                        jsonRes1.add(next);
                    }
                }
                insertXinfangJSONData(jsonRes1);
            }
        }
    }

    @Override
    public void insertXinfangHistoryData(String min, String max) throws SQLException {

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
            String SQL2 = "select URL,params from DS_OUTER_URLS where table_name='WEEKLY_XINFANG_HISTORY'";
            List<HashMap> SQLRes2 = dbOperateService.oracleQuerySql(SQL2);
            String url1 = (String) SQLRes2.get(0).get("URL");
            String params1 = (String) SQLRes2.get(0).get("PARAMS");
            String URL = url1;
            JSONObject params = new JSONObject();
            params.put("access_token", tokenString);
            params.put("min", min);
            params.put("max", max);

            JSONObject paramObject = HttpClientUtil2.getParamObject(params1);
            Iterator<String> iterator = paramObject.keySet().iterator();
            while (iterator.hasNext()) {
                String nextKey = iterator.next();
                params.put(nextKey, paramObject.getString(nextKey));
            }

            JSONObject httpQueryRes = HttpClientUtil2.doGet(URL, params);
            if (200 == httpQueryRes.getIntValue("code")) {
                String dataString = httpQueryRes.getString("data");
                JSONObject data = JSON.parseObject(dataString);
                JSONArray jsonRes = data.getJSONArray("data");
                insertXinfangJSONData(jsonRes);
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
            if (i1 > 1) {
                logger.info("added XinfangEvent:" + xinFangEventModel.toString());
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
                if (i2 > 1) {
                    logger.info("added XinfangPeople:" + personModel.toString());
                }
            }
        }

    }

    @Scheduled(cron = "0/30 * * * * ?")
    public void test() {
        System.out.println("\n>>>>> test Spring cron\n");
    }
}
