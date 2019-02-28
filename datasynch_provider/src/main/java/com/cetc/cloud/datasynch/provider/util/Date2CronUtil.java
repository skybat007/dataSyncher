package com.cetc.cloud.datasynch.provider.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.util
 * projectName:   dataSyncher
 * Description:   LuoLinjie 补充
 * Creator:     by LuoLinjie
 * Create_Date: 2019/2/28 11:17
 * Updater:     by Administrator
 * Update_Date: 2019/2/28 11:17
 * Update_Description: Administrator 补充
 */

public class Date2CronUtil {
    /***
     *
     * @param date
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDateByPattern(Date date,String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /***
     * convert Date to cron ,eg.  "0 06 10 15 1 ? 2014"
     * @param date  : 时间点
     * @return
     */
    public static String getCron(java.util.Date  date){
        String dateFormat="ss mm HH dd MM ?";
        return formatDateByPattern(date, dateFormat);
    }
}
