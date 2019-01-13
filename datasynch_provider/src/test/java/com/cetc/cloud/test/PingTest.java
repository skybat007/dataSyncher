package com.cetc.cloud.test;

import com.cetc.cloud.datasynch.provider.core.util.Ping;
import org.junit.Test;

/**
 * PackageName:   com.cetc.cloud.test
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/10/27 15:52
 * Updater:     by luolinjie
 * Update_Date: 2018/10/27
 * Update_Description: luolinjie 补充
 **/
public class PingTest {

    @Test
    public void testPing(){
        String URL = "https://data.szmb.gov.cn:8090/szmbdata/open/openData.do?type=3&appid=1535964485300&appKey=e5bf506b-9308-4529-a21e-d3790034542e";
        String ipAddressFromURL = Ping.getIpAddressFromURL(URL);
        System.out.println(ipAddressFromURL);
    }
}
