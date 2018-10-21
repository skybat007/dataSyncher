package com.cetc.cloud.datasynch.provider.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "myProps") //接收application.yml中的myProps下面的属性
public class MyProps {
    public static String OracleIp;
    public static String OracleUsrName;
    public static String OraclePassword;

    public static String getOracleIp() {
        return OracleIp;
    }

    public static void setOracleIp(String oracleIp) {
        OracleIp = oracleIp;
    }

    public static String getOracleUsrName() {
        return OracleUsrName;
    }

    public static void setOracleUsrName(String oracleUsrName) {
        OracleUsrName = oracleUsrName;
    }

    public static String getOraclePassword() {
        return OraclePassword;
    }

    public static void setOraclePassword(String oraclePassword) {
        OraclePassword = oraclePassword;
    }
}