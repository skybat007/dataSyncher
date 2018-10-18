package com.cetc.datasynch.tools;


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
    public static final String Type_DATE = "DATE";
    public static final String Type_TIMESTAMP = "TIMESTAMP"; // 可能存在多种形式：TIMESTAMP(6) TIMESTAMP(0)

    public static final String Type_CLOB = "CLOB";
    public static final String Type_BLOB = "BLOB";
    public static final String Type_NCLOB = "NCLOB";

//    public static final String Type_UNDEFINED = "UNDEFINED";

    public static String getDecoratedColumn(String columnType, String value) {

        if (null == value) {
            return "null";
        }

        if (Type_NUMBER.equals(columnType) || Type_VARCHAR2.equals(columnType) || Type_NVARCHAR2.equals(columnType) || Type_VARCHAR2.equals(columnType)) {
            return "'" + value + "'";
        } else if (Type_DATE.equals(columnType)||Type_TIMESTAMP.equals(columnType)) {
            return "TO_DATE('" + value + "', 'YYYY-MM-DD HH24:MI:SS')";
        }

        return null;
    }
}
