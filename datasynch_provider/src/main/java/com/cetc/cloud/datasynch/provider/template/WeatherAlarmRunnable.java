package com.cetc.cloud.datasynch.provider.template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cetc.cloud.datasynch.api.model.DddOuterURLsModel;
import com.cetc.cloud.datasynch.api.model.Token;
import com.cetc.cloud.datasynch.provider.core.util.HttpClientUtil2;
import com.cetc.cloud.datasynch.provider.service.impl.DbOperateService;
import com.cetc.cloud.datasynch.provider.service.impl.OuterUrlsService;
import com.cetc.cloud.datasynch.provider.core.tools.DbTools;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Description：创建定时 执行计算 实例
 * Created by luolinjie on 2018/10/10.
 */
@Slf4j
public class WeatherAlarmRunnable implements OuterJobRunnableTemplate {

    private DbOperateService dbOperateService;
    private OuterUrlsService outerUrlsService;

    public WeatherAlarmRunnable( DbOperateService dbOperateService, OuterUrlsService outerUrlsService) {
        this.dbOperateService = dbOperateService;
        this.outerUrlsService = outerUrlsService;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("WEATHER_FORECAST_SZ");
        try {
            insertWeatherAlarmInfoNow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取预警信息并插入至数据库
     *
     * @throws SQLException
     */
    public void insertWeatherAlarmInfoNow() throws SQLException {
        log.info("Started Scheduled Job:insertWeatherAlarmInfoNow()");
        //考虑WarningContent为空 、或者json无法被解析的情况
        DddOuterURLsModel weatherForecastModel = outerUrlsService.getModelByTableName("WEATHER_FORECAST_SZ");
        String url = weatherForecastModel.getUrl();
        String headers = weatherForecastModel.getHeaders();
        Token token = HttpClientUtil2.parseTokenStr2Token(headers);
        JSONObject res = HttpClientUtil2.doGetWithAuthoration(url, null, token);
        if (res.getIntValue("code") == 200) {
            String data = res.getString("data");
            JSONArray array = JSON.parseArray(data);
            for (int i = 0; i < array.size(); i++) {
                JSONObject singleInfo = array.getJSONObject(i);
                try {
                    String warningContent = singleInfo.getString("WarningContent");
                    JSONArray warningInfo = singleInfo.getJSONArray("WarningInfo");
                    JSONObject warningInfoObj = warningInfo.getJSONObject(0);
                    String releaseTime = warningInfoObj.getString("ReleaseTime");
                    String releaseArea = warningInfoObj.getString("ReleaseArea");
                    String alertCategory = warningInfoObj.getString("AlertCategory");
                    String warningLevel = warningInfoObj.getString("WarningLevel");

                    if (releaseArea==null||"".equals(releaseArea)||
                            releaseTime==null||"".equals(releaseTime)||
                            alertCategory==null||"".equals(alertCategory)||
                            warningLevel==null||"".equals(warningLevel)||
                    warningContent==null||"".equals(warningContent)
                            ) continue;
                    //获取 "字段-字段类型" 映射map
                    HashMap<String, String> tbStructureMap = dbOperateService.queryTableStructureByTableName2("WEATHER_ALARM");
                    //获取字段类型
                    String releasearea = tbStructureMap.get("RELEASEAREA");
                    String alertcategory = tbStructureMap.get("ALERTCATEGORY");
                    String warninglevel = tbStructureMap.get("WARNINGLEVEL");
                    String releasetime = tbStructureMap.get("RELEASETIME");
                    String warningcontent = tbStructureMap.get("WARNINGCONTENT");
                    String decoratedColumn_releaseArea = DbTools.getDecoratedColumn(releasearea, releaseArea);
                    String decoratedColumn_alertcategory = DbTools.getDecoratedColumn(alertcategory, alertCategory);
                    String decoratedColumn_warninglevel = DbTools.getDecoratedColumn(warninglevel, warningLevel);
                    String decoratedColumn_releasetime = DbTools.getDecoratedColumn(releasetime, releaseTime);
                    String decoratedColumn_warningcontent = DbTools.getDecoratedColumn(warningcontent, warningContent);
                    String sql = "insert into weather_alarm(OBJECT_ID,RELEASEAREA,ALERTCATEGORY,WARNINGLEVEL,RELEASETIME,WARNINGCONTENT)" +
                            " values( SEQ_WEATHER_ALARM.NEXTVAL,"
                            +decoratedColumn_releaseArea+","
                            +decoratedColumn_alertcategory+","
                            +decoratedColumn_warninglevel+","
                            +decoratedColumn_releasetime+","
                            +decoratedColumn_warningcontent
                            + ")";
                    log.info("SQL:"+sql);
                    int i1 = dbOperateService.oracleUpdateSql(sql);
                    if (i1 > 0) {
                        log.info("execute success! SQL :" + sql);
                    } else {
                        log.info("execute failed! SQL :" + sql);
                    }
                } catch (Exception e) {
                    log.error("SQL error， Maybe Data is Duplicated: print Data:" + data);
//                    e.printStackTrace();
                }
            }
        } else {
            log.error("error while get OnLine WeatherForecast Info:Model:" + weatherForecastModel.toString());
        }

    }

}


