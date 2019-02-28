package com.cetc.cloud.test;

import com.cetc.cloud.datasynch.provider.util.FileDownloadUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Slf4j
public class ImageDownLoader {

    @Autowired
    @Qualifier("readOnlyJdbcTemplate")
    JdbcTemplate readOnlyJdbcTemplate;

    @Test
    public void testDownloadImages() throws Exception {
        //读取列表
        String SQL = "SELECT DISTINCT filepath FROM \"QXFJ_BUILD_IMAGE_V\" \n" +
                "WHERE filepath IS NOT NULL\n" +
                "AND filepath NOT IN (SELECT filepath FROM \"QXFJ_BUILD_IMAGE_V\" \n" +
                "WHERE filepath ='undefined' )";
        String rootpath = "D:\\build_image_v";
        SqlRowSet sqlRowSet = readOnlyJdbcTemplate.queryForRowSet(SQL);
        ArrayList<String> filePathList = new ArrayList<String>();
        while (sqlRowSet.next()) {
            filePathList.add(sqlRowSet.getString(1));
        }

        for (String path : filePathList) {
            downLoadFile(path,rootpath);
        }
    }

    public void downLoadFile(String url,String rootpath ) {

        String fileSavePath;
        //0.获取文件名称
        String[] split = url.split("/");
        String fileName = split[split.length - 1];
        //1.截取文件路径
        fileSavePath = url.replaceAll("/" + fileName, "");
        String subPath = fileSavePath.substring(24);
        System.out.println(subPath);

        //2.判断该路径是否存在，如果不存在则创建
        fileSavePath = rootpath + subPath;

        //3.下载文件
        try {
            FileDownloadUtil.download(url, fileName, fileSavePath);
        }catch (Exception e){
            log.error("\r\nURL:"+url+"\r\nrootpath:"+rootpath);
        }
    }
}