package com.cetc.cloud.datasynch.provider.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cetc.cloud.datasynch.api.model.*;
import com.cetc.cloud.datasynch.api.service.SingleJobRemoteService;
import com.cetc.cloud.datasynch.provider.core.util.HttpClientUtil2;
import com.cetc.cloud.datasynch.provider.mapper.XinfangEventMapper;
import com.cetc.cloud.datasynch.provider.service.impl.*;
import com.cetc.cloud.datasynch.provider.template.SanxiaoCalcRunnable;
import com.cetc.cloud.datasynch.provider.template.XinfangGetRunnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.controller
 * projectName:   dataSyncher
 * Description:   提供手动运行单次任务的功能
 * Creator:     by luolinjie
 * Create_Date: 2018/11/28 11:09
 * Updater:     by luolinjie
 * Update_Date: 2018/11/28
 * Update_Description: luolinjie 补充
 **/

@RestController
@Slf4j
public class SingleJobController implements SingleJobRemoteService {

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
    @Autowired
    RePullTableController rePullTableController;
    @Autowired
    private OuterUrlsService outerUrlsService;

    @Override
    public void backupTable(String tableName) {
        //执行备份
        dbOperateService.backUpTable(tableName);
    }

    @Override
    public void truncateAndReSynchTable(String tableName) {
        // 执行备份
        dbOperateService.backUpTable(tableName);
        //执行Truncate操作
        dbOperateService.truncateTableByTbName(tableName);
        log.info("DAYLY_REPEATE_JOB: truncated table:" + tableName);

        //重新拉取全表
        ScheduleModel scheduleModel = scheduleService.queryModelByTableName(tableName);
        jobManageService.startOnceJob(scheduleModel);
    }

    @Override
    public void calculateHasTroubleSanXiao() throws SQLException {
        SanxiaoCalcRunnable sanxiaoCalcRunnable = new SanxiaoCalcRunnable(dbQueryService, dbOperateService, httpOperateService, rePullTableController);
        sanxiaoCalcRunnable.calculateHasTroubleSanXiao();
    }

    @Scheduled(cron = "00 40 08 * * ?")
    public void calculateRealSanXiaoCount() throws SQLException {
        Thread.currentThread().setName("calcSanXiaoCount");
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
            log.info("\n Finished! calculateRealSanXiaoCount" + countNum + "\n");
        }

    }

    @Override
    public void insertXinfangDataToday() throws SQLException {
        log.info("Started Scheduled Job:insertXinfangDataToday()");
        XinfangGetRunnable xinfangGetRunnable = new XinfangGetRunnable(outerUrlsService, xinfangEventMapper);
        xinfangGetRunnable.run();
    }

    @Override
    public void insertXinfangHistoryData(String min, String max) throws SQLException {
        log.info("executing insertXinfangHistoryData():\nparam1:min" + min + "\nparam2:max:" + max);
        //1.获取Http请求信息
        DddOuterURLsModel xinfangHistoryModel = outerUrlsService.getModelByTableName("WEEKLY_XINFANG_HISTORY");
        //2.获取关联token请求信息
        DddOuterURLsModel tokenModel = outerUrlsService.getModelByObjectId(xinfangHistoryModel.getToken_link_id());
        //3.获取最新token值
        String tokenString = XinfangGetRunnable.getXinfangTokenStr(tokenModel);
        // 在线请求
        if (tokenString != null) {
            String url1 = xinfangHistoryModel.getUrl();
            String params1 = xinfangHistoryModel.getParams();
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
            String tokenStr = xinfangHistoryModel.getHeaders();
            Token token = HttpClientUtil2.parseTokenStr2Token(tokenStr);
            JSONObject httpQueryRes = HttpClientUtil2.doGetWithAuthoration(URL, params, token);
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
