package com.cetc.cloud.datasynch.provider.util;

import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by Administrator on 2018/12/18.
 */
public class HttpClientUtil2Test extends TestCase {

    @Test
    public void testToHttpParamStr() throws Exception {
        String url = "http://www.baidu.com";
        JSONObject params = new JSONObject();
        params.put("key1", "value1");
        params.put("key2", "value2");
        params.put("key3", "value3");
        String s = HttpClientUtil2.toHttpParamStr(url, params);
        System.out.println(s);
        String url2 = "http://www.baidu.com";
        JSONObject params2 = new JSONObject();
        String s2 = HttpClientUtil2.toHttpParamStr(url2, params2);
        System.out.println(s2);
    }
}