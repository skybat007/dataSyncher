package com.cetc.cloud.datasynch.provider.template;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cetc.cloud.datasynch.api.model.DddOuterURLsModel;
import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import com.cetc.cloud.datasynch.provider.core.util.JsonExtractor;
import com.cetc.cloud.datasynch.provider.service.impl.DbOperateService;
import com.cetc.cloud.datasynch.provider.service.impl.DbQueryService;
import com.cetc.cloud.datasynch.provider.service.impl.HttpOperateService;
import com.cetc.cloud.datasynch.provider.service.impl.OuterUrlsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.template
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/12/14 15:12
 * Updater:     by luolinjie
 * Update_Date: 2018/12/14
 * Update_Description: luolinjie 补充
 **/
public class ChengguanEventAttachRunnable implements OuterJobRunnableTemplate {
    private Logger logger = LoggerFactory.getLogger(SanxiaoCalcRunnable.class);
    private DbQueryService dbQueryService;
    private DbOperateService dbOperateService;
    private HttpOperateService httpOperateService;
    private OuterUrlsService outerUrlsService;

    @Override
    public void run() {
        Thread.currentThread().setName("CHENGGUAN_ATTATCH");
        insertChengguanEventAttach();
    }

    public ChengguanEventAttachRunnable(DbQueryService dbQueryService, DbOperateService dbOperateService, HttpOperateService httpOperateService, OuterUrlsService outerUrlsService) {
        this.dbQueryService = dbQueryService;
        this.dbOperateService = dbOperateService;
        this.httpOperateService = httpOperateService;
        this.outerUrlsService = outerUrlsService;
    }

    private void insertChengguanEventAttach() {
        //todo 1.查询没有附件的事件对应的system_id和EVENT_CODE(筛选条件：HAS_ATTACHMENT为空)
        String querySQL = "select SYSTEMID,EVENT_CODE FROM BLK_CHENGGUAN_EVENT where HAS_ATTACHMENT is null";
        List<HashMap> result = null;
        try {
            result = dbOperateService.oracleQuerySql(querySQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 1.通过model获取http基本请求信息
        boolean seq_blk_cg_evt_atts = dbOperateService.checkIfSequenceExists("SEQ_BLK_CG_EVT_ATTS");
        if (seq_blk_cg_evt_atts==false){
            dbOperateService.createSequence("SEQ_BLK_CG_EVT_ATTS");
        }
        DddOuterURLsModel outerJobModel = outerUrlsService.getModelByTableName("BLK_CG_EVT_ATTS");
        Integer token_link_id = outerJobModel.getToken_link_id();
        DddOuterURLsModel tokenModel = outerUrlsService.getModelByObjectId(token_link_id);
        JSONObject tokenJSON = httpOperateService.queryDataByOuterUrlModel(tokenModel);
        String tokenData = tokenJSON.getString("data");
        String tokenVal = JsonExtractor.extractTokenStr(tokenData, tokenModel.getResult_extract_rule());
        if (tokenData != null || !tokenData.equals("")) {
            JSONArray addParams = new JSONArray();
            JSONObject tokenParam = new JSONObject();
            //获取到token之后，将其作为参数继续请求该任务的数据
            String tokenKeyName = outerJobModel.getToken_key_name();
            tokenParam.put(CommonInstance.GLOBAL_PARAM_KEYNAME, tokenKeyName);
            tokenParam.put(CommonInstance.GLOBAL_PARAM_VALUENAME, tokenVal);
            addParams.add(tokenParam);
            if (result != null && result.size() != 0) {
                for (HashMap map : result) {
                    String updateHasTagSQL = null;
                    String systemId = (String) map.get("SYSTEMID");//对应附件接口中的BizID参数值
                    // 将systemId作为请求参数，继续下游http请求
                    JSONObject systemIdParam = new JSONObject();
                    systemIdParam.put(CommonInstance.GLOBAL_PARAM_KEYNAME, "BizID");
                    systemIdParam.put(CommonInstance.GLOBAL_PARAM_VALUENAME, systemId);
                    addParams.add(systemIdParam);
                    //在线请求附件链接
                    JSONObject resData = httpOperateService.queryDataByOuterUrlModelAddParam(outerJobModel, systemIdParam, tokenParam);
                    String data = resData.getString("data");
                    //解析
                    List<HashMap> attachList = JsonExtractor.ExtractListData(data, outerJobModel.getResult_extract_rule());
                    //4.入库（BLK_CG_EVT_ATTS）
                    List<Integer> blk_cg_evt_atts = null;
                    try {
                        blk_cg_evt_atts = dbOperateService.insertIntoTargetTableByTableName(attachList, "BLK_CG_EVT_ATTS");
                    }catch (Exception e){
                        logger.error("Error while executing  dbOperateService.insertIntoTargetTableByTableName(),data:"+attachList);
                    }
                    if (blk_cg_evt_atts.get(0) == CommonInstance.SUCCESS) {
                        //todo 5.更新chengguan_event表中的HAS_ATTACHMENT，如果步骤3中解析结果为空，则插入0，不为空则插入1
                        updateHasTagSQL = "update BLK_CHENGGUAN_EVENT set HAS_ATTACHMENT=1 where SYSTEMID='" + systemId + "'";
                    } else {
                        updateHasTagSQL = "update BLK_CHENGGUAN_EVENT set HAS_ATTACHMENT=0 where SYSTEMID='" + systemId + "'";
                    }
                    if (updateHasTagSQL != null) {
                        try {
                            int i = dbOperateService.oracleUpdateSql(updateHasTagSQL);
                            if (i > 0) {
                                logger.info("added chengguan attach success! SYSTEMID:" + systemId);
                            }
                        } catch (Exception e) {
                            logger.error("Error while execute:insertChengguanEventAttach(),relative SQL:"+updateHasTagSQL);
                        }
                    }

                }
            }

        }

    }
}
