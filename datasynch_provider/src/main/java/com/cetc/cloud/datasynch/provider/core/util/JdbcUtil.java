package com.cetc.cloud.datasynch.provider.core.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * jdbc工具类
 *
 * @author llj
 */
public class JdbcUtil {
    private static String url = "jdbc:mysql://localhost:3306/db_31project_alpha?tinyInt1isBit=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&zeroDateTimeBehavior=convertToNull";
    private static String user = "root";
    private static String password = "123456";

    /**
     * 将注册驱动程序放在静态代码块中
     */
//    static {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            System.out.println("jdbc驱动程序注册失败！");
//        }
//    }


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
            }
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
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