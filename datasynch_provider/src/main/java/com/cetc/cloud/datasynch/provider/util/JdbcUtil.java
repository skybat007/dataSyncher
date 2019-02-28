package com.cetc.cloud.datasynch.provider.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * jdbc工具类
 *
 * @author llj
 */
@Slf4j
public class JdbcUtil {

    private static String url = "jdbc:mysql://10.192.19.108:3306/db_31project_alpha?tinyInt1isBit=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&zeroDateTimeBehavior=convertToNull";
    private static String user = "root";
    private static String password = "123456";


    public static JdbcUtil getInstance() {
        return new JdbcUtil();
    }

    /**
     * 抽取获取连接对象的方法
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 抽取获取连接对象的方法
     */
    public static Connection getConnection(String url, String user, String password) {
        Connection conn = null;
        try {
            if (url.contains("jdbc:oracle")) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection(url, user, password);
            }else {
                Class.forName("mysql.jdbc.driver.MysqlDriver");
                conn = DriverManager.getConnection(url, user, password);
            }
        } catch (Exception e) {
            log.error("\r\nurl:"+url+"\r\n user:"+user+"\r\n password:"+password);
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 释放资源的方法
     */
    public static void close(Connection conn, Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

}