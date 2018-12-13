package com.cetc.cloud.datasynch.provider.template;

import com.cetc.cloud.datasynch.provider.service.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * Description：创建定时 执行计算 实例
 * Created by luolinjie on 2018/10/10.
 */
public class SanxiaoCalcRunnable implements Runnable {

    private Logger logger = LoggerFactory.getLogger(SanxiaoCalcRunnable.class);
    private DbQueryService dbQueryService;
    private DbOperateService dbOperateService;
    private HttpOperateService httpOperateService;

    public SanxiaoCalcRunnable(DbQueryService dbQueryService, DbOperateService dbOperateService, HttpOperateService httpOperateService) {
        this.dbQueryService = dbQueryService;
        this.dbOperateService = dbOperateService;
        this.httpOperateService = httpOperateService;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("calcTrblSanXiao");
        //todo 执行计算,输出匹配的三小场所id
        try {
            calculateHasTroubleSanXiao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void calculateHasTroubleSanXiao() throws SQLException {


        //获取有未处理事件的三小场所ID的list
        String getTroublePlaceIds = "SELECT DISTINCT a.\"ID\"\n" +
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
        logger.info("\nCalculateHasTroubleSanXiao:success: " + totalSuccessCount + "\n");
    }

}


