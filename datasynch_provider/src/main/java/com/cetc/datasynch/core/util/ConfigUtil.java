package com.cetc.datasynch.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Description：
 * Created by luolinjie on 2018/5/5.
 */
public class ConfigUtil {

    private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
    private static Properties properties;
    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(ConfigUtil.class.getClassLoader().getResource("tb_layerName.properties").getPath())));
        } catch (IOException e) {
            logger.error("error while loading config file : tb_layerName.properties");
            e.printStackTrace();
        }
    }

    public static String getLayerId(String indexName){
        return properties.getProperty(indexName);
    }
}
