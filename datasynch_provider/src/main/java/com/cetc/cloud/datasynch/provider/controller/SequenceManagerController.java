package com.cetc.cloud.datasynch.provider.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cetc.cloud.datasynch.api.service.SequenceManagerRemoteService;
import com.cetc.cloud.datasynch.provider.service.impl.DbOperateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.controller
 * projectName:   dataSyncher
 * Description:   定时更新
 * Creator:     by luolinjie
 * Create_Date: 2018/11/27 9:18
 * Updater:     by luolinjie
 * Update_Date: 2018/11/27
 * Update_Description: luolinjie 补充
 **/
@RestController
public class SequenceManagerController implements SequenceManagerRemoteService {
    Logger logger = LoggerFactory.getLogger(SequenceManagerController.class);
    @Autowired
    DbOperateService dbOperateService;

    private Properties tableSeqMapping;

    @Override
    public List getAllSequenceNameList() {
        return dbOperateService.getAllSequenceNameList();
    }

    @Override
    public String exactAllSequences() throws IOException, SQLException, InterruptedException {
        //todo 将所有异常序列increment_by 改为+1
        List<String> list = dbOperateService.oracleQueryList("select sequence_name \n" +
                "from USER_sequences \n" +
                "WHERE INCREMENT_BY NOT IN(1)");
        if (list.size() > 0) {
            changeIncrementByValueNormal(list);
        }
        tableSeqMapping = loadMappingExcel();
        JSONObject result = new JSONObject();
        int successCount = 0;
        int failCount = 0;
        Set<Object> keySet = tableSeqMapping.keySet();
        Object[] list1 = parseSetToSortedList(keySet);

        JSONArray successTables = new JSONArray();
        JSONArray failedTables = new JSONArray();
        for (Object table : list1) {
            boolean b = exactSequenceByTbName((String) table);
            if (b == true) {
                successCount++;
                successTables.add( table);
            } else {
                failCount++;
                failedTables.add( table);
            }
        }
        result.put("successCount", successCount);
        result.put("failCount", failCount);
        result.put("successTables",successTables);
        result.put("failTables", failedTables);
        return result.toJSONString();
    }

    private Object[] parseSetToSortedList(Set<Object> keySet) {
        ArrayList<String> list1 = new ArrayList<String>();
        Iterator<Object> iterator = keySet.iterator();
        while (iterator.hasNext()){
            Object next = iterator.next();
            list1.add((String) next);
        }
        Object[] arr = list1.toArray();
        Arrays.sort(arr);
        return arr;
    }


    private void changeIncrementByValueNormal(List<String> sequencNameList) throws SQLException {
        for (String sequencName : sequencNameList) {
            String sql = "alter sequence " + sequencName + " increment by 1";
            int i = dbOperateService.oracleUpdateSql(sql);
            logger.info("successfully executed sql:" + sql);
        }
    }

    @Override
    public boolean exactSequenceByTbName(String tableName) throws IOException, SQLException, InterruptedException {
        //1.check:表是否存在
        boolean existsTable = dbOperateService.checkIfExistsTable(tableName);
        if (existsTable == false) {
            logger.error("table does not exists!" + tableName);
        }
        //2.获取表名对应序列名的 对照名单
        if (tableSeqMapping == null) {
            tableSeqMapping = loadMappingExcel();
        }
        //3.check:序列是否存在
        boolean sequenceExists = dbOperateService.checkIfSequenceExists_pure((String) tableSeqMapping.get(tableName));

        if (sequenceExists == false) {
            String SEQName = (String) tableSeqMapping.get(tableName);
            logger.error("sequence :" + SEQName + " does not exists! target tableName: " + tableName);
            logger.info("creating SEQ:" + SEQName);
            if (null != SEQName && !"".equals(SEQName)) {
                boolean sequence = dbOperateService.createSequence(SEQName);
                Thread.sleep(50);
            } else {
                logger.error("sequence name is null :" + SEQName + " target tableName:" + tableName);
                return false;
            }
        }

        //4.检查该表是否有ObjectId字段
        boolean exists = dbOperateService.checkIfColumnExists(tableName, "OBJECT_ID");
//        if ("BLK_LEGAL_PERSON".equals(tableName) || "BLK_HOUSE".equals(tableName)) {
//            System.out.println(tableName);
//        }
        if (exists == false) {
            logger.error("cannot find Column: OBJECT_ID");
            return false;
        } else {
            // 1.获取Max（ObjectId）
            int maxObjectId = dbOperateService.getMaxObjectId(tableName);
            int tableRowCounts = dbOperateService.getTableRowCounts(tableName);
            if (tableRowCounts <= 1) {
                return false;
            }
            if (maxObjectId == -1 || tableRowCounts > 1 && maxObjectId == 0) {
                String updateSQL = "UPDATE " + tableName + " SET OBJECT_ID=rownum";
                int i = dbOperateService.oracleUpdateSql(updateSQL);
                if (i > 0) {
                    logger.info("Successfully executed SQL:" + updateSQL);
                }
                maxObjectId = dbOperateService.getMaxObjectId(tableName);
            }
            // 2.获取sequence的nextValue
            String sequenceName = (String) tableSeqMapping.get(tableName);
            int nextSeqVal = dbOperateService.getNextSeqVal(sequenceName);

            // 3.对比
            //获取差值
            if (-1 != nextSeqVal) {
                int subResult = maxObjectId - nextSeqVal;
                if (subResult == 0) {
                    logger.info("no need to exact sequence :" + sequenceName);
                    return false;
                }
                //todo 纠正后的数据不能小于0
                if (nextSeqVal + subResult < 0) {
                    logger.error("error nextSeqValue :" + nextSeqVal);
                    return false;
                }

                /**
                 *   --将序列数值强制切换到N
                 alter sequence SEQ_DS_SCHEDULE_JOB_INFO increment by 154;    -- n为做自增能够达到预想的值
                 --做一次查询，执行数值切换
                 select SEQ_DS_SCHEDULE_JOB_INFO.nextval from dual;
                 --再将序列的值修改回去
                 alter sequence SEQ_DS_SCHEDULE_JOB_INFO increment by 1;
                 */


                boolean i1 = dbOperateService.oracleExecuteSql("alter sequence " + sequenceName + " increment by " + subResult);


                if (i1 == false) {
                    return false;
                }
                int nextSeqVal2 = dbOperateService.getNextSeqVal(sequenceName);
                if (nextSeqVal2 == -1) {
                    logger.error("cannot get nextVal from " + sequenceName);
                    return false;
                }
                int i2 = dbOperateService.oracleUpdateSql("alter sequence " + sequenceName + " increment by 1");
                logger.info("\r\nfinished exact sequence value!\nsequenceName:" + sequenceName
                        + "\r\n current sequence value:" + nextSeqVal2);
            }
            return true;
        }
    }

    private Properties loadMappingExcel() throws IOException {
        Properties properties = new Properties();
        InputStream resourceAsStream = SequenceManagerController.class.getClassLoader().getResourceAsStream("TableSeqNameMapping.properties");
        properties.load(resourceAsStream);
        return properties;
    }
}
