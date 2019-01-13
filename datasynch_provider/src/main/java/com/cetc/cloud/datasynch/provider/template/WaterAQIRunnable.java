package com.cetc.cloud.datasynch.provider.template;

import com.cetc.cloud.datasynch.provider.core.tools.AQICalcTools;
import com.cetc.cloud.datasynch.provider.service.impl.DbOperateService;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Description：环水 - 计算空气质量AQI指标
 * Created by luolinjie on 2018/10/10.
 */
@Slf4j
public class WaterAQIRunnable implements OuterJobRunnableTemplate {

    private DbOperateService dbOperateService;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final String TABLE_Sites = "QHSJ_T_AIR_BASICINFO";
    private final String TABLE_srcValue = "QHSJ_AIR_MONITOR_HOUR_DATA";
    private final String TABLE_AQI = "QHSJ_AQI_INFO";

    public WaterAQIRunnable(DbOperateService dbOperateService) {
        this.dbOperateService = dbOperateService;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("WATER_AQI_SCHEDULE");
        try {
            generateHourlyAQI();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取预警信息并插入至数据库
     *
     * @throws SQLException
     */
    public void generateHourlyAQI() throws SQLException {
        log.info("Started Scheduled Job:generateHourlyAQI()");
        //获取监测站列表
        String SQL_query_list = "select SITE_CODE from " + TABLE_Sites;
        List<String> sites = dbOperateService.oracleQueryList(SQL_query_list);
        boolean seq_qhsj_aqi_info = dbOperateService.checkIfSequenceExists("SEQ_QHSJ_AQI_INFO");
        if (!seq_qhsj_aqi_info) {
            boolean seq_qhsj_aqi_info1 = dbOperateService.createSequence("SEQ_QHSJ_AQI_INFO");
            if (seq_qhsj_aqi_info1 == true) {
                log.info("created Sequence:SEQ_QHSJ_AQI_INFO");
            }
        }
        for (String siteCode : sites) {
            Date maxGenerateDate = getMaxGenerateDate(siteCode);
            //获取maxDate
            Date maxDate = getMaxCreateDate(siteCode);
            Date start = null;
            Date minDate = getMinCreateDate(siteCode);

            //确定开始时间
            if (maxGenerateDate != null && maxDate != null && minDate != null) {
                if (maxGenerateDate.before(maxDate)) {
                    if (maxGenerateDate.after(minDate)) {
                        start = maxGenerateDate;
                    } else {
                        start = minDate;
                    }
                }
            } else if (minDate != null) {
                start = minDate;
            } else {
                log.error("cannot find minDate!!");
                return;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            while (calendar.getTime().before(maxDate)) {
                //查询并写入当前时间点的监测值
                int count = insertAQI(calendar, siteCode);
                if (count > 0) {
                    log.info("");
                }
                calendar.add(Calendar.HOUR_OF_DAY, 1);
            }
        }

        log.info("\n >>>>>>>>>>> FINISHED generateHourlyAQI!");
    }


    //查询并插入AQI数据
    private int insertAQI(Calendar calendar, String siteCode) {
        String formatedTime = this.formatter.format(calendar.getTime());
        //查询并计算当前AQI数据
        long hourlyDataBySiteCode = getHourlyAQIDataBySiteCode(siteCode, formatedTime);
        if (hourlyDataBySiteCode == 0) {
            return 0;
        }
        int nextSeqVal = dbOperateService.getNextSeqVal("SEQ_QHSJ_AQI_INFO");
        String SQL = "insert into " + TABLE_AQI + "(OBJECT_ID,MONITOR_TIME,SITE_CODE,AQI_VALUE) values ('" + nextSeqVal + "',to_date('" + formatedTime + "','yyyy-mm-dd hh24:mi:ss'),'" + siteCode + "','" + hourlyDataBySiteCode + "')";
        try {
            int i = dbOperateService.oracleUpdateSql(SQL);
            if (i >= 1) {
                log.info("success! SQL:" + SQL);
            }
        } catch (SQLException e) {
            log.error("Error SQL :" + SQL);
            e.printStackTrace();
        }
        return 0;
    }

    private Date getMaxGenerateDate(String siteCode) {

        //获取AQI最大创建时间（最新时间）
        String sql_max_AQIDate = "select max(MONITOR_TIME) from " + TABLE_AQI + " where SITE_CODE='" + siteCode + "'";
        List<String> maxGeneratedDates = null;
        try {
            maxGeneratedDates = dbOperateService.oracleQueryList(sql_max_AQIDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Date maxGenerateDate = null;
        if (maxGeneratedDates.get(0) != null) {
            try {
                maxGenerateDate = formatter.parse(maxGeneratedDates.get(0));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        if (maxGenerateDate != null) {
            return maxGenerateDate;
        } else {
            return null;
        }
    }

    public Date getMaxCreateDate(String siteCode) {
        String SQL = "select max(MONITOR_TIME) from " + TABLE_srcValue + " where SITE_CODE = '" + siteCode + "'";
        List<String> list = null;
        try {
            list = dbOperateService.oracleQueryList(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String s = list.get(0);
        if (s != null) {
            try {
                Date parsedDate = formatter.parse(s);
                return parsedDate;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Date getMinCreateDate(String siteCode) {
        String SQL = "select min(MONITOR_TIME) from " + TABLE_srcValue + " where SITE_CODE = '" + siteCode + "'";
        List<String> list = null;
        try {
            list = dbOperateService.oracleQueryList(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String s = list.get(0);
        if (s != null) {
            try {
                Date parsedDate = formatter.parse(s);
                return parsedDate;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private long getHourlyAQIDataBySiteCode(String siteCode, String date) {
        String SQL_queryData = "select MONITOR_FACTOR_CODE,MONITOR_VALUE from " + TABLE_srcValue
                + " where SITE_CODE = '" + siteCode + "'"
                + " and MONITOR_TIME=to_date('" + date + "', 'yyyy-mm-dd hh24:mi:ss')";
        List<List> resList = null;
        try {
            resList = dbOperateService.oracleQueryList_2member(SQL_queryData);
        } catch (SQLException e) {
            log.error(SQL_queryData);
            e.printStackTrace();
        }
        if (resList == null || resList.size() == 0) {
            return 0;
        }
        return AQICalcTools.getFinalAQIVal(resList);

    }
}


