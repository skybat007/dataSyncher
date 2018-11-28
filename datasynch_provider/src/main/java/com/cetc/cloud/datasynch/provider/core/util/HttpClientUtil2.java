package com.cetc.cloud.datasynch.provider.core.util;

import com.alibaba.fastjson.JSONObject;
import com.cetc.cloud.datasynch.api.model.Token;
import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.core.util
 * projectName:   dataSyncher
 * Description:   Http连接工具--连接池版本
 * Creator:     by luolinjie
 * Create_Date: 2018/11/19 18:01
 * Updater:     by luolinjie
 * Update_Date: 2018/11/19
 * Update_Description: luolinjie 补充
 **/
public class HttpClientUtil2 {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil2.class);

    static ConcurrentHashMap<String, PoolingHttpClientConnectionManager> cmHashMap =
            new ConcurrentHashMap<String, PoolingHttpClientConnectionManager>();
    static ConcurrentHashMap<String, CloseableHttpClient> clientHashMap =
            new ConcurrentHashMap<String, CloseableHttpClient>();

    private static synchronized CloseableHttpClient getHttpClient(String url) {
        String[] ip_port = getIpAndPortFromUrl(url);
        String ip = ip_port[0];
        int port = Integer.parseInt(ip_port[1]);
        if (clientHashMap.keySet().contains(ip + "-" + port)) {
            return clientHashMap.get(ip + "-" + port);
        } else {
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(200);// 将最大连接数增加到200
            cm.setDefaultMaxPerRoute(20);// 将每个路由基础的连接增加到20
            HttpHost localhost = new HttpHost(ip, 80);
            cm.setMaxPerRoute(new HttpRoute(localhost), 10);//将目标主机的最大连接数增加到10
            CloseableHttpClient buildHttpClient = HttpClients.custom()
                    .setConnectionManager(cm)
                    .build();
            cmHashMap.put(ip + "-" + port, cm);
            clientHashMap.put(ip + "-" + port, buildHttpClient);
            return buildHttpClient;
        }

    }
    /**
     * Get方法：不带Token认证
     * @param url
     * @param params
     * @return
     * @throws URISyntaxException
     */
    public static JSONObject doGet(String url, JSONObject params) {
        CloseableHttpClient httpClient = getHttpClient(url);
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", null);
        result.put("code", 200);
        result.put("msg", null);
        HttpGet httpGet = null;
        StatusLine status = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            if (!MapUtils.isEmpty(params)) {
                for (String key : params.keySet()) {
                    builder.setParameter(key, params.getString(key));
                }
            }
            httpGet = new HttpGet(builder.build());
            RequestConfig config = RequestConfig.custom()
                    .setSocketTimeout(6000)
                    .setConnectTimeout(6000)
                    .setConnectionRequestTimeout(6000).build();
            httpGet.setConfig(config);
            HttpResponse response = httpClient.execute(httpGet);
            status = response.getStatusLine();                          //获取返回的状态码
            HttpEntity entity = response.getEntity();                   //获取响应内容
            result.put("success", true);
            result.put("data", EntityUtils.toString(entity, "UTF-8"));
            result.put("code", 200);
            result.put("msg", "请求成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("code", 500);
            result.put("msg", "请求异常，异常信息：" + e.getClass() + "->" + e.getMessage());
        } finally {
            httpGet.abort();//中止请求，连接被释放回连接池
        }
        return result;
    }
    public static JSONObject doGetWithBody(String url, JSONObject params,JSONObject bodys) {
        CloseableHttpClient httpClient = getHttpClient(url);
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", null);
        result.put("code", 200);
        result.put("msg", null);
        HttpGet httpGet = null;
        StatusLine status = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            if (!MapUtils.isEmpty(params)) {
                for (String key : params.keySet()) {
                    builder.setParameter(key, params.getString(key));
                }
            }
            httpGet = new HttpGet(builder.build());
            RequestConfig config = RequestConfig.custom()
                    .setSocketTimeout(6000)
                    .setConnectTimeout(6000)
                    .setConnectionRequestTimeout(6000).build();
            httpGet.setConfig(config);
            if (null!=bodys && "".equals(bodys)) {
                Iterator<String> iterator = bodys.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    httpGet.setHeader(key, bodys.getString(key));
                }
            }

            HttpResponse response = httpClient.execute(httpGet);
            status = response.getStatusLine();                          //获取返回的状态码
            HttpEntity entity = response.getEntity();                   //获取响应内容
            result.put("success", true);
            result.put("data", EntityUtils.toString(entity, "UTF-8"));
            result.put("code", 200);
            result.put("msg", "请求成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("code", 500);
            result.put("msg", "请求异常，异常信息：" + e.getClass() + "->" + e.getMessage());
        } finally {
            httpGet.abort();//中止请求，连接被释放回连接池
        }
        return result;
    }

    /**
     * Get方法：带Token认证
     *
     * @param url
     * @param params
     * @param token
     * @return
     */
    public static JSONObject doGetWithAuthoration(String url, JSONObject params, Token token) {
        CloseableHttpClient httpClient = getHttpClient(url);
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("data", null);
        result.put("code", 200);
        result.put("msg", null);
        HttpGet httpGet = null;
        StatusLine status = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            if (!MapUtils.isEmpty(params)) {
                for (String key : params.keySet()) {
                    builder.setParameter(key, params.getString(key));
                }
            }
            httpGet = new HttpGet(builder.build());
            RequestConfig config = RequestConfig.custom()
                    .setSocketTimeout(30000)
                    .setConnectTimeout(30000)
                    .setConnectionRequestTimeout(30000).build();
            httpGet.setConfig(config);
            httpGet.setHeader(token.getKey(), token.getValue());
            HttpResponse response = httpClient.execute(httpGet);
            status = response.getStatusLine();                          //获取返回的状态码
            HttpEntity entity = response.getEntity();                   //获取响应内容
            result.put("success", true);
            result.put("data", EntityUtils.toString(entity, "UTF-8"));
            result.put("code", 200);
            result.put("msg", "请求成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("code", 500);
            result.put("msg", "请求异常，异常信息：" + e.getClass() + "->" + e.getMessage());
        } finally {
            httpGet.abort();//中止请求，连接被释放回连接池
        }
        return result;

    }



    private static String[] getIpAndPortFromUrl(String URL) {
        if (URL != null && !"".equals(URL)) {
            String[] ip_port = new String[2];
            String[] split =  URL.split("//");
            String s1 = split[1].split("/")[0];
            if (s1.contains(":")){
                ip_port[0] = s1.split(":")[0];
                ip_port[1] = s1.split(":")[1];
            }else {
                ip_port[0] = s1;
                ip_port[1] = "80";
            }
            return ip_port;
        } else {
            return null;
        }
    }


    public static String get(String url, JSONObject paramMap) throws IOException, URISyntaxException {
        CloseableHttpClient httpClient = getHttpClient(url);
        String result = null;
        URIBuilder builder = new URIBuilder(url);
        if (!MapUtils.isEmpty(paramMap)) {
            for (String key : paramMap.keySet()) {
                builder.setParameter(key, paramMap.getString(key));
            }
        }
        HttpGet httpGet = new HttpGet(builder.build());
        RequestConfig config = RequestConfig.custom()
                .setSocketTimeout(30000)
                .setConnectTimeout(30000)
                .setConnectionRequestTimeout(30000).build();
        httpGet.setConfig(config);
        HttpResponse response = httpClient.execute(httpGet);
        StatusLine status = response.getStatusLine();                   //获取返回的状态码
        HttpEntity entity = response.getEntity();                       //获取响应内容
        result = EntityUtils.toString(entity, "UTF-8");
        if (!(status.getStatusCode() == HttpStatus.SC_OK)) {
            logger.error("put request error:\n" + result);
        }
        httpGet.abort();//中止请求，连接被释放回连接池
        return result;
    }

    public static String put(String url, String params) throws IOException {
        CloseableHttpClient httpClient = getHttpClient(url);
        String result = null;
        HttpPut httpPut = new HttpPut(url);

        httpPut.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpPut.setEntity(new StringEntity(params));
        HttpResponse response = httpClient.execute(httpPut);
        StatusLine status = response.getStatusLine();                   //获取返回的状态码
        HttpEntity entity = response.getEntity();                       //获取响应内容
        result = EntityUtils.toString(entity, "UTF-8");
        if (!(status.getStatusCode() == HttpStatus.SC_OK)) {
            logger.error("put request error:\n" + result);
        }
        httpPut.abort();//中止请求，连接被释放回连接池
        return result;
    }

    public static String post(String url, String params) throws IOException {
        CloseableHttpClient httpClient = getHttpClient(url);
        String result = "";
        HttpPost httpPost = new HttpPost(url);

        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.setEntity(new StringEntity(params));
        HttpResponse response = httpClient.execute(httpPost);
        StatusLine status = response.getStatusLine();                   //获取返回的状态码
        HttpEntity entity = response.getEntity();                       //获取响应内容
        result = EntityUtils.toString(entity, "UTF-8");
        if (!(status.getStatusCode() == HttpStatus.SC_OK)) {
            logger.error("post request error:\n" + result);
        }
        httpPost.abort();//中止请求，连接被释放回连接池
        return result;
    }

    public static String get(String url, Map<String, String> paramMap) throws IOException, URISyntaxException {
        CloseableHttpClient httpClient = getHttpClient(url);
        String result = null;
        URIBuilder builder = new URIBuilder(url);
        if (!MapUtils.isEmpty(paramMap)) {
            for (String key : paramMap.keySet()) {
                builder.setParameter(key, paramMap.get(key));
            }
        }

        HttpGet httpGet = new HttpGet(builder.build());
        RequestConfig config = RequestConfig.custom()
                .setSocketTimeout(30000)
                .setConnectTimeout(30000)
                .setConnectionRequestTimeout(30000).build();
        httpGet.setConfig(config);
        HttpResponse response = httpClient.execute(httpGet);
        StatusLine status = response.getStatusLine();                   //获取返回的状态码
        HttpEntity entity = response.getEntity();                       //获取响应内容
        result = EntityUtils.toString(entity, "UTF-8");
        if (!(status.getStatusCode() == HttpStatus.SC_OK)) {
            logger.error("put request error:\n" + result);
        }
        httpGet.abort();//中止请求，连接被释放回连接池
        return result;
    }


    public static String delete(String url) throws IOException {
        CloseableHttpClient httpClient = getHttpClient(url);
        String result = null;

        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setHeader("Content-Type", "application/json;charset=UTF-8");
        HttpResponse response = httpClient.execute(httpDelete);
        StatusLine status = response.getStatusLine();                   //获取返回的状态码
        HttpEntity entity = response.getEntity();                       //获取响应内容
        result = EntityUtils.toString(entity, "UTF-8");
        if (!(status.getStatusCode() == HttpStatus.SC_OK)) {
            logger.error("delete request error:\n" + result);
        }
        httpDelete.abort();//中止请求，连接被释放回连接池
        return result;
    }

    public static JSONObject getHttpParams(String httpParamExpression){
        JSONObject httpQueryParams = new JSONObject();
        if (null != httpParamExpression) {
            String[] paramKeyValues = httpParamExpression.split("&");

            for (int i = 0; i < paramKeyValues.length; i++) {
                String[] split = paramKeyValues[i].split("=");
                if (split.length == 2) {
                    String key = split[0];
                    String value = split[1];
                    httpQueryParams.put(key, value);
                } else {
                    continue;
                }
            }
        }
        return httpQueryParams;
    }
}
