package com.cetc.cloud.datasynch.provider.controller;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.provider.core.util.HttpClientUtil2;
import com.cetc.cloud.datasynch.provider.service.impl.*;
import com.cetc.cloud.datasynch.provider.template.MyScheduleRunnable;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.controller
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/11/28 11:09
 * Updater:     by luolinjie
 * Update_Date: 2018/11/28
 * Update_Description: luolinjie 补充
 **/
@RestController
public class SingleJobController implements SingleJobRemoteService {
    private Logger logger = LoggerFactory.getLogger(SingleJobController.class);

    @Autowired
    private DbOperateService dbOperateService;
    @Autowired
    private SynchJobLogInfoService synchJobLogInfoService;
    @Autowired
    private DbQueryService dbQueryService;
    @Autowired
    private HttpOperateService httpOperateService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private JobManageService jobManageService;


    @Scheduled(cron = "00 00 23 * * ?")
    @Override
    public void truncateTable() {
        //todo 执行备份
        dbOperateService.backUpTable("BLK_SANXIAO_PLACE");
//        //todo 执行Truncate操作
//        dbOperateService.truncateTableByTbName("BLK_SANXIAO_PLACE");
//        logger.info("DAYLY_REPEATE_JOB: truncated table: BLK_SANXIAO_PLACE");
//        //todo 重新拉取全表
//        ScheduleModel scheduleModel = scheduleService.queryModelByJobId(157);
//        jobManageService.startOnceJob(scheduleModel);
    }

    @Scheduled(cron="00 15 23 * * ?")
    @Override
    public void calculateHasTroubleSanXiao() throws SQLException {

        //获取有未处理事件的三小场所ID的list
        String getTroublePlaceIds = "SELECT  a.\"ID\"\n" +
                "from BLK_SANXIAO_PLACE a,BLK_CHENGGUAN_EVENT b\n" +
                "WHERE a.name=b.EVENT_NAME\n" +
                "and a.ADDRESS=b.ADDRESS";
        List<String> troublePlaceIdList = dbOperateService.oracleQueryList(getTroublePlaceIds);
        int totalSuccessCount = 0;
        for (int i = 0; i < troublePlaceIdList.size(); i++) {
            String sql = "update BLK_SANXIAO_PLACE set HAS_TROUBLE=1 where id='" + troublePlaceIdList.get(i) + "'";
            int count = dbOperateService.oracleUpdateSql(sql);
            if (count > 0) {
                totalSuccessCount++;
            }
        }
        logger.info("\nCalculateHasTroubleSanXiao:success: " + totalSuccessCount+"\n");
    }

    @Scheduled(cron = "00 16 23 * * ?")
    public void calculateRealSanXiaoCount() throws SQLException {
        String SQL = "select count(1) from BLK_SANXIAO_PLACE where status='0'";
        List<String> list = dbOperateService.oracleQueryList(SQL);
        String countNum =null;
        if (list.size()>0) {
            countNum = list.get(0);
        }else {
            return;
        }

        String SQL2 = "update BLK_SANXIAO_PLACE_COUNT set COUNT='"+countNum+"'";
        int i = dbOperateService.oracleUpdateSql(SQL2);
        if (i>0){
            logger.info("\n Finished! calculateRealSanXiaoCount"+countNum+"\n");
        }

    }

    @Scheduled(cron = "00 00 22 * * ?")
    public void insertXinfangData() throws SQLException {

        //todo 获取token
//        HttpClientUtil2.doGet();
        //todo 在线请求

        //todo 遍历插入（需要比对）


    }
}
