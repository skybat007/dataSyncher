package com.cetc.cloud.datasynch.provider.core.tools;


import java.io.UnsupportedEncodingException;
import java.util.Arrays;

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

    /**
     * 获取组装后的字段内容
     */
    public static String getDecoratedColumn(String columnType, String value) throws UnsupportedEncodingException {

        if (null == value || Type_BLOB.equals(columnType)) {
            return "null";
        }

        if (Type_FLOAT.equals(columnType) || Type_LONG.equals(columnType) || Type_NUMBER.equals(columnType)
                || Type_VARCHAR2.equals(columnType) || Type_NVARCHAR2.equals(columnType) || Type_VARCHAR2.equals(columnType)
                || Type_CHAR.equals(columnType) || Type_NCHAR.equals(columnType) || Type_VARCHAR.equals(columnType)
                || Type_CLOB.equals(columnType) || Type_NCLOB.equals(columnType)) {
            byte[] bytes = value.getBytes();
            if (bytes.length > 3999) {
                byte[] bytes1 = Arrays.copyOfRange(bytes, 0, 3999);
                value = new String(bytes1,"UTF-8");
            }
            if (value.contains("'")) {
                value = value.replaceAll("'", "''");
            }
            return "'" + value.trim() + "'";
        } else if (Type_DATE.equals(columnType)) {
            if (value.contains(".")) {
                value = value.substring(0, value.indexOf("."));
            }
            return "TO_DATE('" + value + "', 'YYYY-MM-DD HH24:MI:SS')";
        } else if (columnType.contains(Type_TIMESTAMP)) {
            return "TO_TIMESTAMP('" + value + "', 'YYYY-MM-DD HH24:MI:SS')";
        }

        return null;
    }
}
