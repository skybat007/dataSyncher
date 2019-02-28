package com.cetc.cloud.datasynch.provider.service.impl;

import com.cetc.cloud.datasynch.provider.util.ListUtil;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.service.impl
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/12/11 10:55
 * Updater:     by luolinjie
 * Update_Date: 2018/12/11
 * Update_Description: luolinjie 补充
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DbOperateServiceTest extends TestCase {
    @Autowired
    DbOperateService dbOperateService;

    @Test
    public void testGetMaxObjectId() throws Exception {
        int qhsj_enterprise_hour_data = dbOperateService.getMaxObjectId("COMMUNITY_CODE");
        System.out.println(qhsj_enterprise_hour_data);

    }

    @Test
    public void testGetNextSeqVal() throws Exception {
        int seq_urban_risk_detail = dbOperateService.getNextSeqVal("SEQ_URBAN_RISK_DETAIL");
        System.out.println("SEQ_URBAN_RISK_DETAIL.netxVal:" + seq_urban_risk_detail);
    }

    @Test
    public void testDeleteRedundantTableCopies() throws Exception {
        String tableName = "TEST";
        dbOperateService.deleteRedundantTableCopies(tableName);
    }

    @Test
    public void testGetTableRowCounts() throws Exception {
        int counts = dbOperateService.getTableRowCounts("BLK_SANXIAO_PLACE");
        System.out.println(counts);
    }

    @Test
    public void testOracleBatchSql() throws Exception {
        String sql1 = "update temp SET ID='1'";
        String sql2 = "update temp SET ID='2'";
        String sql3 = "update temp SET ID='3'";
        String sql4 = "update temp SET ID='df'";
        String sql5 = "insert into temp(id) values ('5')";

        List<String> sqls = new ArrayList<>();
        sqls.add(sql1);
        sqls.add(sql2);
        sqls.add(sql3);
        sqls.add(sql4);
        sqls.add(sql5);
        int[] batchSql = dbOperateService.oracleBatchSql(ListUtil.toStringArray(sqls));
        ArrayList arrayList = new ArrayList();
        for (int i : batchSql) {
            arrayList.add(i);
        }
        System.out.println(arrayList);
    }

    @Test
    public void testBackUpTable() throws Exception {
        String blk_sanxiao_place_count = dbOperateService.backUpTable("BLK_SANXIAO_PLACE_COUNT");
        System.out.println(blk_sanxiao_place_count);
    }
}