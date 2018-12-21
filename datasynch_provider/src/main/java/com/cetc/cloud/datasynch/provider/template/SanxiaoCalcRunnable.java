package com.cetc.cloud.datasynch.provider.template;

import com.cetc.cloud.datasynch.provider.controller.RePullTableController;
import com.cetc.cloud.datasynch.provider.service.impl.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;

/**
 * Description：创建定时 执行计算 实例
 * Created by luolinjie on 2018/10/10.
 */
@Slf4j
@NoArgsConstructor
public class SanxiaoCalcRunnable implements OuterJobRunnableTemplate {

    private DbQueryService dbQueryService;
    private DbOperateService dbOperateService;
    private HttpOperateService httpOperateService;
    private RePullTableController rePullTableController;

    public SanxiaoCalcRunnable(DbQueryService dbQueryService, DbOperateService dbOperateService, HttpOperateService httpOperateService,RePullTableController rePullTableController) {
        this.dbQueryService = dbQueryService;
        this.dbOperateService = dbOperateService;
        this.httpOperateService = httpOperateService;
        this.rePullTableController = rePullTableController;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("calcTrblSanXiao");
        //todo 执行计算,输出匹配的三小场所id
        try {
            rePullTableController.clearAndPullAgainTableByTableName("BLK_SANXIAO_PLACE");
            calculateHasTroubleSanXiao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void calculateHasTroubleSanXiao() throws SQLException {
        String sql0 = "update BLK_SANXIAO_PLACE set HAS_TROUBLE=0";
        int count0 = dbOperateService.oracleUpdateSql(sql0);
        log.info("reset HAS_TROUBLE=0,changed rows:" + count0);

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
        log.info("\nCalculateHasTroubleSanXiao:success: " + totalSuccessCount + "\n");
    }

}


