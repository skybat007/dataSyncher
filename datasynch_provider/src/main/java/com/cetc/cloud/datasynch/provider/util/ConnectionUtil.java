package com.cetc.cloud.datasynch.provider.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Description：
 * Created by luolinjie on 2017/11/14.
 */
@Slf4j
public class ConnectionUtil {

    public static String getNetworkRawData(String urlStr) {
        StringBuilder document = new StringBuilder();
        InputStream is = null;
        Scanner sc = null;
        try {
//            updateSession();
            //1、创建URLConnction.
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();
            //2、设置connection的属性
            connection.setConnectTimeout(300000);
            connection.setReadTimeout(300000);
            connection.setRequestProperty("Request Method","POST");

            //3.连接
            connection.connect();
            //5.获取内容
            is = connection.getInputStream();
            sc = new Scanner(is,"UTF-8");

            while (sc.hasNextLine()) {
                document.append(sc.nextLine());
            }
        } catch (Exception e) {
            log.error("connection error! URL:" + urlStr);
            e.printStackTrace();
        }finally {
            try {
                sc.close();
            }catch (Exception e1){
                log.error("close scanner error! URL:" + urlStr);
                e1.printStackTrace();
            }
            try {
                is.close();
            }catch (IOException e) {
                log.error("close inputStream error! URL:" + urlStr);
                e.printStackTrace();
            }
        }

        return document.toString();
    }
    public static String sendGET(String urlStr) {
        StringBuilder document = new StringBuilder();
        InputStream is = null;
        Scanner sc = null;
        try {
            //1、创建URLConnction.
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();
            //2、设置connection的属性
            connection.setConnectTimeout(300000);
            connection.setReadTimeout(300000);
            connection.setRequestProperty("Request Method","GET");
            //3.连接
            connection.connect();
            //5.获取内容
            is = connection.getInputStream();
            sc = new Scanner(is,"UTF-8");

            while (sc.hasNextLine()) {
                document.append(sc.nextLine());
            }
        } catch (Exception e) {
            log.error("connection error! URL:" + urlStr);
            e.printStackTrace();
        }finally {
            try {
                sc.close();
            }catch (Exception e1){
                log.error("close scanner error! URL:" + urlStr);
                e1.printStackTrace();
            }
            try {
                is.close();
            }catch (IOException e) {
                log.error("close inputStream error! URL:" + urlStr);
                e.printStackTrace();
            }
        }

        return document.toString();
    }
    public static String FormatDate(String pre, String suf) {
        Date date = new Date();
        String regex = "";
        regex = "YYYYMMdd";
        SimpleDateFormat formater = new SimpleDateFormat(regex);
        String formatDate = formater.format(date);

        String time = pre + formatDate + suf;
        return time;
    }

}
