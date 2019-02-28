package com.cetc.cloud.datasynch.provider.tools;


import org.apache.commons.lang3.time.DateUtils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Description：数据库相关工具
 * Created by luolinjie on 2018/10/18.
 */
public class DbTools {
    public static final String Type_CHAR = "CHAR";
    public static final String Type_NCHAR = "NCHAR";
    public static final String Type_VARCHAR = "VARCHAR";
    public static final String Type_VARCHAR2 = "VARCHAR2";
    public static final String Type_NVARCHAR2 = "NVARCHAR2";

    public static final String Type_NUMBER = "NUMBER";

    public static final String Type_LONG = "LONG";
    public static final String Type_FLOAT = "FLOAT";
    public static final String Type_DATE = "DATE";
    public static final String Type_TIMESTAMP = "TIMESTAMP"; // 可能存在多种形式：TIMESTAMP(6) TIMESTAMP(0)

    public static final String Type_CLOB = "CLOB"; //大字符串建议使用这种类型
    public static final String Type_BLOB = "BLOB"; //注意！！坚决不要使用该数据类型
    public static final String Type_NCLOB = "NCLOB";

    public static final String[] partterns = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-ddTHH:mm:ss","yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss\\.0","yyyy/MM/dd HH:mm:ss"};
    public static final String parttern = "yyyy-MM-dd HH:mm:ss";

    public static SimpleDateFormat formatter = new SimpleDateFormat(parttern);
    public static String specialTimeValueRegex = "^[\\d]{13}$";
    public static Pattern pattern_NumberTime = Pattern.compile(specialTimeValueRegex);

    /**
     * 获取组装后的字段内容
     */
    public static String getDecoratedColumn(String columnType, String value) throws UnsupportedEncodingException {

        if (null == value || "".equals(value) || Type_BLOB.equals(columnType)) {
            return "null";
        }

        if (Type_FLOAT.equals(columnType) || Type_LONG.equals(columnType) || Type_NUMBER.equals(columnType)
                || Type_VARCHAR2.equals(columnType) || Type_NVARCHAR2.equals(columnType) || Type_VARCHAR2.equals(columnType)
                || Type_CHAR.equals(columnType) || Type_NCHAR.equals(columnType) || Type_VARCHAR.equals(columnType)
                || Type_CLOB.equals(columnType) || Type_NCLOB.equals(columnType)) {
            byte[] bytes = value.getBytes();
            if (bytes.length > 3999) {
                byte[] bytes1 = Arrays.copyOfRange(bytes, 0, 3999);
                value = new String(bytes1, "UTF-8");
            }
            if (value.contains("'")) {
                value = value.replaceAll("'", "''");
            }
            return "'" + value.trim() + "'";
        } else if (Type_DATE.equals(columnType)) {
            if (value.contains(".")) {
                value = value.substring(0, value.indexOf("."));
            }
            if (value.contains("T")){
                value = value.replaceAll("T"," ");
            }
            if (pattern_NumberTime.matcher(value).matches()) {
                value = formatter.format(new java.sql.Date(Long.valueOf(value)));
                return "TO_DATE('" + value + "', 'YYYY-MM-DD HH24:MI:SS')";
            }
            try {
                Date date = DateUtils.parseDate(value, partterns);
                value = formatter.format(date);
                return "TO_DATE('" + value + "', 'YYYY-MM-DD HH24:MI:SS')";
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (columnType.contains(Type_TIMESTAMP)) {
            return "TO_TIMESTAMP('" + value + "', 'YYYY-MM-DD HH24:MI:SS')";
        }

        return null;
    }
}
