package com.cetc.datasynch.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MyURLConnection {
    private static final Logger logger = LoggerFactory.getLogger(MyURLConnection.class);
    String url;
    String type;
    String body;
    String result;

    public MyURLConnection(){

    }

    /**
     * 单线程 http客户端
     * @param url
     * @param type
     * @param body
     * @return
     */
    public String request(String url, String type, String body) throws IOException {
        URLConnection urlConnection = null;
        HttpURLConnection httpURLConnection=null;
        try {
            logger.debug("[ur: " + url + " ,type: " + type + " ,body: ]\n" + body);
//            URL url = new URL(url);
            this.url = url;
            this.type = type;
            this.body = body;

            urlConnection = new URL(url).openConnection();
            httpURLConnection = (HttpURLConnection) urlConnection;

            /*输入默认为false，post需要打开*/
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");

            httpURLConnection.setRequestMethod(type);


//            httpURLConnection.setConnectTimeout(3000);


            httpURLConnection.connect();


            OutputStream outputStream = httpURLConnection.getOutputStream();

            outputStream.write(body.getBytes());


            InputStream inputStream = httpURLConnection.getInputStream();


            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            result = builder.toString();
            logger.debug(result);

        }finally{
            assert httpURLConnection != null;
            httpURLConnection.disconnect();/*关闭连接*/
        }

        return result;

    }
}
