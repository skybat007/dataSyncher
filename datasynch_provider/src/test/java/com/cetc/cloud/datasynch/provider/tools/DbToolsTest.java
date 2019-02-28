package com.cetc.cloud.datasynch.provider.tools;

import junit.framework.TestCase;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.tools
 * projectName:   dataSyncher
 * Description:   LuoLinjie 补充
 * Creator:     by LuoLinjie
 * Create_Date: 2019/2/19 21:58
 * Updater:     by Administrator
 * Update_Date: 2019/2/19 21:58
 * Update_Description: Administrator 补充
 */

public class DbToolsTest extends TestCase {

    public static final String[] partterns = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss\\.0","yyyy/MM/dd HH:mm:ss"};
    public static final String parttern = "yyyy-MM-dd HH:mm:ss";
    public static SimpleDateFormat formatter = new SimpleDateFormat(parttern);
    public static String specialTimeValueRegex = "^[\\d]{13}$";
    public static Pattern pattern_NumberTime = Pattern.compile(specialTimeValueRegex);

    @Test
    public void testGetDecoratedColumn() throws Exception {
//        String value = "1537170500000";
//        String value = "2018-01-21 21:17";
        String value = "2018/2/21 21:17:00";
        if (value.contains(".")) {
            value = value.substring(0, value.indexOf("."));
        }
        Matcher matcher = pattern_NumberTime.matcher(value);
        if (pattern_NumberTime.matcher(value).matches()) {
            value = formatter.format(new java.sql.Date(Long.valueOf(value)));
            System.out.println(value);
//            System.exit(1);
//            return ;
        }
        try {
            Date date = DateUtils.parseDate(value, partterns);
            value = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        System.out.println(value);
    }

}