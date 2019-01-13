package com.cetc.cloud.datasynch.provider.core.util;

import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 实现CMD的ping命令
 * @author tgg
 */
@Slf4j
public class Ping {

    public static boolean ping2(String ipAddress) throws Exception {
        int timeOut = 3000 ;
        boolean status = InetAddress.getByName(ipAddress).isReachable(timeOut);
        return status;
    }

    public static boolean ping(String ipAddress) {
        int pingTimes = 3 ;
        int timeOut =3000;
        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();
        // 将要执行的ping命令,此命令是windows格式的命令
        String pingCommand = "ping " + ipAddress + " -n " + pingTimes    + " -w " + timeOut;
        // Linux命令如下
        // String pingCommand = "ping" -c " + pingTimes    + " -w " + timeOut + ipAddress;
        try {
            if (log.isDebugEnabled()) {
                log.debug(pingCommand);
            }
            // 执行命令并获取输出
            Process p = r.exec(pingCommand);
            if (p == null) {
                return false;
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            int connectedCount = 0;
            String line;
            // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
            while ((line = in.readLine()) != null) {
                connectedCount += getCheckResult(line);
            }
            // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
            return connectedCount == pingTimes;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
    //若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
    private static int getCheckResult(String line) {  // System.out.println("控制台输出的结果为:"+line);
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",    Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            return 1;
        }
        return 0;
    }

    /**
     * 从URL中提取域名/IP地址
     * @param URL
     * @return
     */
    public static String getIpAddressFromURL(String URL){

        String[] split = URL.split("//");
        if (split.length>=2) {
            String tail = split[1];
            String IP_port = tail.split("/")[0];
            if (IP_port.contains(":")){
                log.info(IP_port.split(":")[0]);
                return IP_port.split(":")[0];
            }else {
                log.info(IP_port);
                return IP_port;
            }
        }
        return null;
    }
}