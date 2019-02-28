package com.cetc.cloud.datasynch.provider.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Description：
 * Created by luolinjie on 2018/5/5.
 */
@Slf4j
public class ConfigUtil {

    private static Properties properties;
    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(ConfigUtil.class.getClassLoader().getResource("tb_layerName.properties").getPath())));
        } catch (IOException e) {
            log.error("error while loading config file : tb_layerName.properties");
            e.printStackTrace();
        }
    }

    public static String getLayerId(String indexName){
        return properties.getProperty(indexName);
    }
}
