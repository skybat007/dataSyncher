package com.cetc.cloud.datasynch.provider.controller;

import com.alibaba.fastjson.JSONObject;
import com.cetc.cloud.datasynch.api.service.SequenceManagerRemoteService;
import com.cetc.cloud.datasynch.provider.service.impl.DbOperateService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SequenceManagerController implements SequenceManagerRemoteService {
    @Autowired
    DbOperateService dbOperateService;

//    private Properties tableSeqMapping;

    @Override
    public List getAllSequenceNameList() {
        return dbOperateService.getAllSequenceNameList();
    }

    @Override
    public String exactAllSequences() throws IOException, SQLException, InterruptedException {
        JSONObject result = new JSONObject();
        int successCount = 0;
        List successTables = new ArrayList<>();
        int failCount = 0;
        List failedTables = new ArrayList<>();
        //todo 将所有异常序列increment_by 改为+1
        List allSequenceNameList = dbOperateService.getAllSequenceNameList();
        for (Object table : allSequenceNameList) {
            try {
                exactSequenceByTbName((String) table);
                successCount++;
                successTables.add(table);
            } catch (Exception e) {
                failCount++;
                failedTables.add(table);
                e.printStackTrace();
            }
        }
        result.put("successCount", successCount);
        result.put("failCount", failCount);
        result.put("successTables", successTables);
        result.put("failTables", failedTables);
        return result.toJSONString();
    }

    private Object[] parseSetToSortedList(Set<Object> keySet) {
        ArrayList<String> list1 = new ArrayList<String>();
        Iterator<Object> iterator = keySet.iterator();
        while (iterator.hasNext()) {
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
            log.info("successfully executed sql:" + sql);
        }
    }

    @Override
    public String exactSequenceByTbName(String tableName) throws IOException, SQLException, InterruptedException {
        JSONObject res = new JSONObject();
        //1.check:表是否存在
        boolean existsTable = dbOperateService.checkIfExistsTable(tableName);
        if (existsTable == false) {
            log.error("table does not exists!" + tableName);
            res.put("msg", "execute false table does not exists!" + tableName);
            return res.toJSONString();
        }
//        //2.获取表名对应序列名的 对照名单
//        if (tableSeqMapping == null) {
//            tableSeqMapping = loadMappingExcel();
//        }
        //3.check:序列是否存在
        String SEQName = dbOperateService.getSequenceName(tableName);

        boolean sequenceExists = dbOperateService.checkIfSequenceExists(SEQName);

        if (sequenceExists == false) {

            log.error("sequence :" + SEQName + " does not exists! target tableName: " + tableName);
            log.info("creating SEQ:" + SEQName);

            boolean sequence = dbOperateService.createSequence(SEQName);
            Thread.sleep(50);
            boolean exists = dbOperateService.checkIfSequenceExists(SEQName);
            if (exists) {
                log.info(">> creating sequence success! SEQ_NAME:" + SEQName);
            }
        }

        //4.检查该表是否有ObjectId字段
        boolean exists = dbOperateService.checkIfColumnExists(tableName, "OBJECT_ID");
        if (exists == false) {
            log.error("cannot find Column: OBJECT_ID");
            res.put("code", -1);
            res.put("msg", "cannot find Column: OBJECT_ID");
            return res.toJSONString();
        } else {
            // 1.获取Max（ObjectId）
            int maxObjectId = dbOperateService.getMaxObjectId(tableName);
            int tableRowCounts = dbOperateService.getTableRowCounts(tableName);
            if (tableRowCounts <= 1) {
                boolean b = dbOperateService.dropSequence(SEQName);
                if (b) {
                    log.info("drop sequence :" + SEQName + " success!");
                    log.info("creating sequence :" + SEQName + " success!");
                    dbOperateService.createSequence(SEQName);
                    res.put("msg", "RESET sequence:" + SEQName + " SUCCESS!");
                    return res.toJSONString();
                } else {
                    res.put("msg", "recreate sequence:" + SEQName + " error!");
                    return res.toJSONString();
                }
            }
            if (maxObjectId == -1 || tableRowCounts > 1 && maxObjectId == 0) {
                String updateSQL = "UPDATE " + tableName + " SET OBJECT_ID=rownum";
                int i = dbOperateService.oracleUpdateSql(updateSQL);
                if (i > 0) {
                    log.info("Successfully executed SQL:" + updateSQL);
                }
                maxObjectId = dbOperateService.getMaxObjectId(tableName);
            }
            // 2.获取sequence的nextValue
            String sequenceName = dbOperateService.getSequenceName(tableName);
            int nextSeqVal = dbOperateService.getNextSeqVal(sequenceName);

            // 3.对比
            //获取差值
            if (-1 != nextSeqVal) {
                int subResult = maxObjectId - nextSeqVal;
                if (subResult == 0) {
                    log.info("no need to exact sequence :" + sequenceName);
                    res.put("code", -1);
                    res.put("msg", "no need to exact sequence :" + sequenceName);
                    return res.toJSONString();
                }
                //todo 纠正后的数据不能小于0
                if (nextSeqVal + subResult < 0) {
                    log.error("error nextSeqValue :" + nextSeqVal);
                    res.put("code", -1);
                    res.put("msg", "error nextSeqValue :" + nextSeqVal);
                    return res.toJSONString();
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

                int nextSeqVal2 = dbOperateService.getNextSeqVal(sequenceName);
                if (nextSeqVal2 == -1) {
                    log.error("cannot get nextVal from " + sequenceName);
                    res.put("msg", "cannot get nextVal from " + sequenceName);
                    return res.toJSONString();
                }
                int i2 = dbOperateService.oracleUpdateSql("alter sequence " + sequenceName + " increment by 1");
                log.info("\r\nfinished exact sequence value!\nsequenceName:" + sequenceName
                        + "\r\n current sequence value:" + nextSeqVal2);
                res.put("code", 0);
                res.put("msg", "successfully exacted sequence：" + sequenceName
                        + "\r\nfinished exact sequence value!\nsequenceName:" + sequenceName
                        + "\r\n current sequence value:" + nextSeqVal2);
                return res.toJSONString();
            }
            return null;
        }
    }


    @Override
    public boolean resetSequenceBySequenceName(String seqName) throws IOException, SQLException, InterruptedException {
        //1.check:表是否存在
        //2.获取表名对应序列名的 对照名单
        //3.check:序列是否存在
        boolean sequenceExists = dbOperateService.checkIfSequenceExists(seqName);
        if (sequenceExists == true) {
            boolean b = dbOperateService.dropSequence(seqName);
            if (b == true) {
                boolean c = dbOperateService.createSequence(seqName);
            } else {
                return false;
            }
        } else {
            return false;
        }

        return true;

    }

    public Properties loadMappingExcel() throws IOException {
        Properties properties = new Properties();
        InputStream resourceAsStream = SequenceManagerController.class.getClassLoader().getResourceAsStream("TableSeqNameMapping.properties");
        properties.load(resourceAsStream);
        return properties;
    }
}
