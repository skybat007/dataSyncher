package com.cetc.cloud.test;

import me.kagura.JSONQuery;

/**
 * PackageName:   com.cetc.cloud.test
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/11/9 17:50
 * Updater:     by luolinjie
 * Update_Date: 2018/11/9
 * Update_Description: luolinjie 补充
 **/
public class JsonQueryTest {
    public void testJsonQuery(){

//        JSONQuery.select(jsonData,"");
    }
    private String jsonData = "{\n" +
            "    \"datasetId\": \"4d6de370-69db-412d-81c8-74d182360e85\",\n" +
            "    \"dataTitle\": \"事件信息\",\n" +
            "    \"fields\": [\n" +
            "        {\n" +
            "            \"name\": \"OID\",\n" +
            "            \"type\": \"NUMBER\",\n" +
            "            \"length\": 22\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"GEOMETRY\",\n" +
            "            \"type\": \"MDSYS.SDO_GEOMETRY\",\n" +
            "            \"length\": 2000\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"SYSTEMID\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"EVENT_CODE\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"EVENT_NAME\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"EVENT_CONTENT\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"EVENT_TIME\",\n" +
            "            \"type\": \"DATE\",\n" +
            "            \"length\": 7\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"EVENT_SOURCE\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"ISDELETE\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"ADDRESS\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"STATE\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"CREATER_ID\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"CREATER_DEPTID\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"CREATER_DEPT\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"REPORT_NAME\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"REPORT_CODE_TYPE\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"REPORT_CODE\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"REPORT_ADDRESS\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"REPORT_PHONE\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"FLOWSTATE\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"FLOWSTATEID\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"TEMPLATEID\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"EVENT_TYPE\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"EVENT_TYPEID\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"EVENT_LV\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"SOURCE_DEPTID\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"SOURCE_DEPT\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"SOURCE_ID\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"SOURCE_BLSX\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 128\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"TIME_LIMIT\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 128\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"TIME_SL\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 128\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"EVENT_KEYWORD\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"REMARK\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"SHENG\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"SHENGDM\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"SHI\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"SHIDM\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"QU\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"QUDM\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"JD\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"JDDM\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"SQ\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"SQDM\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"WG\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"WGDM\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"DL\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"DLDM\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"XQ\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"XQDM\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"LD\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"LDDM\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"FW\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"FWDM\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"BZDZ\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"FLOWKEY\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"FBTIME\",\n" +
            "            \"type\": \"DATE\",\n" +
            "            \"length\": 7\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"BJTIME\",\n" +
            "            \"type\": \"DATE\",\n" +
            "            \"length\": 7\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"GQTIME\",\n" +
            "            \"type\": \"DATE\",\n" +
            "            \"length\": 7\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"GDTIME\",\n" +
            "            \"type\": \"DATE\",\n" +
            "            \"length\": 7\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"PJTIME\",\n" +
            "            \"type\": \"DATE\",\n" +
            "            \"length\": 7\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"DBTIME\",\n" +
            "            \"type\": \"DATE\",\n" +
            "            \"length\": 7\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"PSTIME\",\n" +
            "            \"type\": \"DATE\",\n" +
            "            \"length\": 7\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"GD_EVENT_TYPE\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"GD_EVENT_TYPEID\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"GQ_TYPE\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"GQ_REMARK\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"LRR\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"LRRQ\",\n" +
            "            \"type\": \"DATE\",\n" +
            "            \"length\": 7\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"GXR\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"GXSJ\",\n" +
            "            \"type\": \"DATE\",\n" +
            "            \"length\": 7\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"LON\",\n" +
            "            \"type\": \"NUMBER\",\n" +
            "            \"length\": 22\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"LAT\",\n" +
            "            \"type\": \"NUMBER\",\n" +
            "            \"length\": 22\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"TASK_ID\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"USER_ID\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"STATUS\",\n" +
            "            \"type\": \"VARCHAR2\",\n" +
            "            \"length\": 255\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"CTIME\",\n" +
            "            \"type\": \"DATE\",\n" +
            "            \"length\": 7\n" +
            "        }\n" +
            "    ],\n" +
            "    \"features\": [\n" +
            "        {\n" +
            "            \"attributes\": {\n" +
            "                \"OID\": \"8456\",\n" +
            "                \"GEOMETRY\": null,\n" +
            "                \"SYSTEMID\": \"e0e660c04f474a9bacaf7d1d04e474a9\",\n" +
            "                \"EVENT_CODE\": \"440304010201805180068\",\n" +
            "                \"EVENT_NAME\": \"姚春兰2018-05-18 17:59:35上报事件\",\n" +
            "                \"EVENT_CONTENT\": \"益田村58栋104无证经营\",\n" +
            "                \"EVENT_TIME\": \"2018-05-18 17:59:35\",\n" +
            "                \"EVENT_SOURCE\": \"90\",\n" +
            "                \"ISDELETE\": \"0\",\n" +
            "                \"ADDRESS\": \"广东省深圳市福田区益田五路\",\n" +
            "                \"STATE\": \"1\",\n" +
            "                \"CREATER_ID\": \"yaochunlan\",\n" +
            "                \"CREATER_DEPTID\": \"440304010003\",\n" +
            "                \"CREATER_DEPT\": \"益田社区\",\n" +
            "                \"REPORT_NAME\": null,\n" +
            "                \"REPORT_CODE_TYPE\": null,\n" +
            "                \"REPORT_CODE\": null,\n" +
            "                \"REPORT_ADDRESS\": null,\n" +
            "                \"REPORT_PHONE\": null,\n" +
            "                \"FLOWSTATE\": \"区智慧指挥中心\",\n" +
            "                \"FLOWSTATEID\": \"22\",\n" +
            "                \"TEMPLATEID\": null,\n" +
            "                \"EVENT_TYPE\": \"无照违法经营\",\n" +
            "                \"EVENT_TYPEID\": \"10130154\",\n" +
            "                \"EVENT_LV\": \"01\",\n" +
            "                \"SOURCE_DEPTID\": null,\n" +
            "                \"SOURCE_DEPT\": null,\n" +
            "                \"SOURCE_ID\": null,\n" +
            "                \"SOURCE_BLSX\": null,\n" +
            "                \"TIME_LIMIT\": \"72\",\n" +
            "                \"TIME_SL\": \"60\",\n" +
            "                \"EVENT_KEYWORD\": null,\n" +
            "                \"REMARK\": null,\n" +
            "                \"SHENG\": \"广东省\",\n" +
            "                \"SHENGDM\": \"44\",\n" +
            "                \"SHI\": \"深圳市\",\n" +
            "                \"SHIDM\": \"4403\",\n" +
            "                \"QU\": \"福田区\",\n" +
            "                \"QUDM\": \"440304\",\n" +
            "                \"JD\": \"福保街道\",\n" +
            "                \"JDDM\": \"440304010\",\n" +
            "                \"SQ\": \"新港社区\",\n" +
            "                \"SQDM\": \"440304010001\",\n" +
            "                \"WG\": \"新港07\",\n" +
            "                \"WGDM\": \"440304010001007\",\n" +
            "                \"DL\": \"益田五路\",\n" +
            "                \"DLDM\": null,\n" +
            "                \"XQ\": \"(福田花园治安岗亭)\",\n" +
            "                \"XQDM\": null,\n" +
            "                \"LD\": null,\n" +
            "                \"LDDM\": \"4403040040110700005\",\n" +
            "                \"FW\": \"益田五路49号福田花园1栋606\",\n" +
            "                \"FWDM\": \"4403040040110700005000036\",\n" +
            "                \"BZDZ\": null,\n" +
            "                \"FLOWKEY\": null,\n" +
            "                \"FBTIME\": \"2018-05-18 18:01:30\",\n" +
            "                \"BJTIME\": null,\n" +
            "                \"GQTIME\": null,\n" +
            "                \"GDTIME\": null,\n" +
            "                \"PJTIME\": null,\n" +
            "                \"DBTIME\": null,\n" +
            "                \"PSTIME\": null,\n" +
            "                \"GD_EVENT_TYPE\": null,\n" +
            "                \"GD_EVENT_TYPEID\": null,\n" +
            "                \"GQ_TYPE\": null,\n" +
            "                \"GQ_REMARK\": null,\n" +
            "                \"LRR\": null,\n" +
            "                \"LRRQ\": null,\n" +
            "                \"GXR\": null,\n" +
            "                \"GXSJ\": null,\n" +
            "                \"LON\": \"113621.390759928\",\n" +
            "                \"LAT\": \"16365.8700269759\",\n" +
            "                \"TASK_ID\": null,\n" +
            "                \"USER_ID\": null,\n" +
            "                \"STATUS\": null,\n" +
            "                \"CTIME\": \"2018-05-18 18:01:27\"\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"attributes\": {\n" +
            "                \"OID\": \"8457\",\n" +
            "                \"GEOMETRY\": null,\n" +
            "                \"SYSTEMID\": \"a4828fd6af54443f88330bdb1a7a90e9\",\n" +
            "                \"EVENT_CODE\": \"440304010201805230100\",\n" +
            "                \"EVENT_NAME\": \"李卫芹2018-05-23 19:08:59上报事件\",\n" +
            "                \"EVENT_CONTENT\": \"石厦北三街菜市场乱堆放东西每天都好臭存在市容市貌隐患\",\n" +
            "                \"EVENT_TIME\": \"2018-05-23 19:08:59\",\n" +
            "                \"EVENT_SOURCE\": \"90\",\n" +
            "                \"ISDELETE\": \"0\",\n" +
            "                \"ADDRESS\": \"广东省深圳市福田区石厦北三街7-7\",\n" +
            "                \"STATE\": \"1\",\n" +
            "                \"CREATER_ID\": \"liweiqin\",\n" +
            "                \"CREATER_DEPTID\": \"440304010004\",\n" +
            "                \"CREATER_DEPT\": \"明月社区\",\n" +
            "                \"REPORT_NAME\": null,\n" +
            "                \"REPORT_CODE_TYPE\": null,\n" +
            "                \"REPORT_CODE\": null,\n" +
            "                \"REPORT_ADDRESS\": null,\n" +
            "                \"REPORT_PHONE\": null,\n" +
            "                \"FLOWSTATE\": \"执法员办理\",\n" +
            "                \"FLOWSTATEID\": \"12\",\n" +
            "                \"TEMPLATEID\": null,\n" +
            "                \"EVENT_TYPE\": \"乱堆放、乱摆卖\",\n" +
            "                \"EVENT_TYPEID\": \"10020033\",\n" +
            "                \"EVENT_LV\": \"01\",\n" +
            "                \"SOURCE_DEPTID\": null,\n" +
            "                \"SOURCE_DEPT\": null,\n" +
            "                \"SOURCE_ID\": null,\n" +
            "                \"SOURCE_BLSX\": null,\n" +
            "                \"TIME_LIMIT\": \"72\",\n" +
            "                \"TIME_SL\": \"60\",\n" +
            "                \"EVENT_KEYWORD\": null,\n" +
            "                \"REMARK\": null,\n" +
            "                \"SHENG\": \"广东省\",\n" +
            "                \"SHENGDM\": \"44\",\n" +
            "                \"SHI\": \"深圳市\",\n" +
            "                \"SHIDM\": \"4403\",\n" +
            "                \"QU\": \"福田区\",\n" +
            "                \"QUDM\": \"440304\",\n" +
            "                \"JD\": \"福保街道\",\n" +
            "                \"JDDM\": \"440304010\",\n" +
            "                \"SQ\": \"明月社区\",\n" +
            "                \"SQDM\": \"440304010004\",\n" +
            "                \"WG\": \"明月09\",\n" +
            "                \"WGDM\": \"440304010004009\",\n" +
            "                \"DL\": \"石厦北三街\",\n" +
            "                \"DLDM\": \"2307\",\n" +
            "                \"XQ\": \"华嵘净菜市场\",\n" +
            "                \"XQDM\": null,\n" +
            "                \"LD\": null,\n" +
            "                \"LDDM\": \"4403040040050700012\",\n" +
            "                \"FW\": \"石厦北三街7号华荣净菜市场2层\",\n" +
            "                \"FWDM\": \"4403040040050700012000027\",\n" +
            "                \"BZDZ\": null,\n" +
            "                \"FLOWKEY\": null,\n" +
            "                \"FBTIME\": \"2018-05-23 19:11:04\",\n" +
            "                \"BJTIME\": null,\n" +
            "                \"GQTIME\": null,\n" +
            "                \"GDTIME\": null,\n" +
            "                \"PJTIME\": null,\n" +
            "                \"DBTIME\": null,\n" +
            "                \"PSTIME\": null,\n" +
            "                \"GD_EVENT_TYPE\": null,\n" +
            "                \"GD_EVENT_TYPEID\": null,\n" +
            "                \"GQ_TYPE\": null,\n" +
            "                \"GQ_REMARK\": null,\n" +
            "                \"LRR\": null,\n" +
            "                \"LRRQ\": null,\n" +
            "                \"GXR\": null,\n" +
            "                \"GXSJ\": null,\n" +
            "                \"LON\": \"114220.968207227\",\n" +
            "                \"LAT\": \"17625.4927639477\",\n" +
            "                \"TASK_ID\": null,\n" +
            "                \"USER_ID\": null,\n" +
            "                \"STATUS\": null,\n" +
            "                \"CTIME\": \"2018-05-23 19:10:50\"\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"attributes\": {\n" +
            "                \"OID\": \"8458\",\n" +
            "                \"GEOMETRY\": null,\n" +
            "                \"SYSTEMID\": \"0a976080e9284ba4a6834bfa04227f37\",\n" +
            "                \"EVENT_CODE\": \"440304010201805230105\",\n" +
            "                \"EVENT_NAME\": \"梁玲2018-05-23 19:55:59上报事件\",\n" +
            "                \"EVENT_CONTENT\": \"石厦东村296一702，集体宿舍，多人居住，电线乱拉，存在安全隐患\",\n" +
            "                \"EVENT_TIME\": \"2018-05-23 19:55:59\",\n" +
            "                \"EVENT_SOURCE\": \"90\",\n" +
            "                \"ISDELETE\": \"0\",\n" +
            "                \"ADDRESS\": \"广东省深圳市福田区福民路185-10\",\n" +
            "                \"STATE\": \"3\",\n" +
            "                \"CREATER_ID\": \"liangling\",\n" +
            "                \"CREATER_DEPTID\": \"440304010005\",\n" +
            "                \"CREATER_DEPT\": \"石厦社区\",\n" +
            "                \"REPORT_NAME\": null,\n" +
            "                \"REPORT_CODE_TYPE\": null,\n" +
            "                \"REPORT_CODE\": null,\n" +
            "                \"REPORT_ADDRESS\": null,\n" +
            "                \"REPORT_PHONE\": null,\n" +
            "                \"FLOWSTATE\": null,\n" +
            "                \"FLOWSTATEID\": \"13\",\n" +
            "                \"TEMPLATEID\": null,\n" +
            "                \"EVENT_TYPE\": \"危险用电\",\n" +
            "                \"EVENT_TYPEID\": \"10010032\",\n" +
            "                \"EVENT_LV\": \"01\",\n" +
            "                \"SOURCE_DEPTID\": null,\n" +
            "                \"SOURCE_DEPT\": null,\n" +
            "                \"SOURCE_ID\": null,\n" +
            "                \"SOURCE_BLSX\": null,\n" +
            "                \"TIME_LIMIT\": \"72\",\n" +
            "                \"TIME_SL\": \"60\",\n" +
            "                \"EVENT_KEYWORD\": null,\n" +
            "                \"REMARK\": null,\n" +
            "                \"SHENG\": \"广东省\",\n" +
            "                \"SHENGDM\": \"44\",\n" +
            "                \"SHI\": \"深圳市\",\n" +
            "                \"SHIDM\": \"03\",\n" +
            "                \"QU\": \"福田区\",\n" +
            "                \"QUDM\": \"440304\",\n" +
            "                \"JD\": \"福保街道\",\n" +
            "                \"JDDM\": \"440304010\",\n" +
            "                \"SQ\": \"石厦社区\",\n" +
            "                \"SQDM\": \"440304010005\",\n" +
            "                \"WG\": \"石厦01\",\n" +
            "                \"WGDM\": \"440304010005001\",\n" +
            "                \"DL\": \"福民路\",\n" +
            "                \"DLDM\": \"3970\",\n" +
            "                \"XQ\": \"众孚花园\",\n" +
            "                \"XQDM\": \"5\",\n" +
            "                \"LD\": null,\n" +
            "                \"LDDM\": \"4403040040090100075\",\n" +
            "                \"FW\": \"福民路185号众孚花园4栋704\",\n" +
            "                \"FWDM\": \"4403040040090100075000028\",\n" +
            "                \"BZDZ\": null,\n" +
            "                \"FLOWKEY\": null,\n" +
            "                \"FBTIME\": \"2018-05-23 19:57:33\",\n" +
            "                \"BJTIME\": \"2018-05-24 15:28:02\",\n" +
            "                \"GQTIME\": \"2018-09-05 00:00:00\",\n" +
            "                \"GDTIME\": \"2018-09-05 00:00:00\",\n" +
            "                \"PJTIME\": \"2018-09-05 15:07:55\",\n" +
            "                \"DBTIME\": \"2018-09-05 00:00:00\",\n" +
            "                \"PSTIME\": \"2018-09-05 00:00:00\",\n" +
            "                \"GD_EVENT_TYPE\": \"危险用电\",\n" +
            "                \"GD_EVENT_TYPEID\": \"10010032\",\n" +
            "                \"GQ_TYPE\": null,\n" +
            "                \"GQ_REMARK\": null,\n" +
            "                \"LRR\": null,\n" +
            "                \"LRRQ\": \"2018-09-05 00:00:00\",\n" +
            "                \"GXR\": null,\n" +
            "                \"GXSJ\": \"2018-09-05 00:00:00\",\n" +
            "                \"LON\": \"113976.0958407186\",\n" +
            "                \"LAT\": \"17240.82653985452\",\n" +
            "                \"TASK_ID\": null,\n" +
            "                \"USER_ID\": null,\n" +
            "                \"STATUS\": null,\n" +
            "                \"CTIME\": \"2018-05-23 19:57:26\"\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"attributes\": {\n" +
            "                \"OID\": \"8459\",\n" +
            "                \"GEOMETRY\": null,\n" +
            "                \"SYSTEMID\": \"dbb55d15212543a39e668caf7f77aba2\",\n" +
            "                \"EVENT_CODE\": \"440304010201805230108\",\n" +
            "                \"EVENT_NAME\": \"冯琼华2018-05-23 20:00:53上报事件\",\n" +
            "                \"EVENT_CONTENT\": \"福田花园大厦A座14楼安全出口堵塞\",\n" +
            "                \"EVENT_TIME\": \"2018-05-23 20:00:53\",\n" +
            "                \"EVENT_SOURCE\": \"90\",\n" +
            "                \"ISDELETE\": \"0\",\n" +
            "                \"ADDRESS\": \"广东省深圳市福田区福强路3031号\",\n" +
            "                \"STATE\": \"3\",\n" +
            "                \"CREATER_ID\": \"fengqionghua\",\n" +
            "                \"CREATER_DEPTID\": \"440304010001\",\n" +
            "                \"CREATER_DEPT\": \"新港社区\",\n" +
            "                \"REPORT_NAME\": null,\n" +
            "                \"REPORT_CODE_TYPE\": null,\n" +
            "                \"REPORT_CODE\": null,\n" +
            "                \"REPORT_ADDRESS\": null,\n" +
            "                \"REPORT_PHONE\": null,\n" +
            "                \"FLOWSTATE\": null,\n" +
            "                \"FLOWSTATEID\": \"13\",\n" +
            "                \"TEMPLATEID\": null,\n" +
            "                \"EVENT_TYPE\": \"安全出口、逃生通道不畅\",\n" +
            "                \"EVENT_TYPEID\": \"10160005\",\n" +
            "                \"EVENT_LV\": \"01\",\n" +
            "                \"SOURCE_DEPTID\": null,\n" +
            "                \"SOURCE_DEPT\": null,\n" +
            "                \"SOURCE_ID\": null,\n" +
            "                \"SOURCE_BLSX\": null,\n" +
            "                \"TIME_LIMIT\": \"72\",\n" +
            "                \"TIME_SL\": \"60\",\n" +
            "                \"EVENT_KEYWORD\": null,\n" +
            "                \"REMARK\": null,\n" +
            "                \"SHENG\": \"广东省\",\n" +
            "                \"SHENGDM\": \"44\",\n" +
            "                \"SHI\": \"深圳市\",\n" +
            "                \"SHIDM\": \"03\",\n" +
            "                \"QU\": \"福田区\",\n" +
            "                \"QUDM\": \"440304\",\n" +
            "                \"JD\": \"福保街道\",\n" +
            "                \"JDDM\": \"440304010\",\n" +
            "                \"SQ\": \"新港社区\",\n" +
            "                \"SQDM\": \"440304010001\",\n" +
            "                \"WG\": \"新港07\",\n" +
            "                \"WGDM\": \"440304010001007\",\n" +
            "                \"DL\": \"福强路\",\n" +
            "                \"DLDM\": \"2723\",\n" +
            "                \"XQ\": \"中国电信益田营业厅\",\n" +
            "                \"XQDM\": null,\n" +
            "                \"LD\": null,\n" +
            "                \"LDDM\": \"4403040040110700001\",\n" +
            "                \"FW\": \"福强路3031号中国电信益田营业厅101\",\n" +
            "                \"FWDM\": \"4403040040110700001000001\",\n" +
            "                \"BZDZ\": null,\n" +
            "                \"FLOWKEY\": null,\n" +
            "                \"FBTIME\": \"2018-05-23 20:02:43\",\n" +
            "                \"BJTIME\": \"2018-05-24 16:34:08\",\n" +
            "                \"GQTIME\": \"2018-09-05 00:00:00\",\n" +
            "                \"GDTIME\": \"2018-09-05 00:00:00\",\n" +
            "                \"PJTIME\": \"2018-09-05 15:10:38\",\n" +
            "                \"DBTIME\": \"2018-09-05 00:00:00\",\n" +
            "                \"PSTIME\": \"2018-09-05 00:00:00\",\n" +
            "                \"GD_EVENT_TYPE\": \"安全出口、逃生通道不畅\",\n" +
            "                \"GD_EVENT_TYPEID\": \"10160005\",\n" +
            "                \"GQ_TYPE\": null,\n" +
            "                \"GQ_REMARK\": null,\n" +
            "                \"LRR\": null,\n" +
            "                \"LRRQ\": \"2018-09-05 00:00:00\",\n" +
            "                \"GXR\": null,\n" +
            "                \"GXSJ\": \"2018-09-05 00:00:00\",\n" +
            "                \"LON\": \"-11631349.879805898\",\n" +
            "                \"LAT\": \"-2214613.8255822756\",\n" +
            "                \"TASK_ID\": null,\n" +
            "                \"USER_ID\": null,\n" +
            "                \"STATUS\": null,\n" +
            "                \"CTIME\": \"2018-05-23 20:02:32\"\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"attributes\": {\n" +
            "                \"OID\": \"8460\",\n" +
            "                \"GEOMETRY\": null,\n" +
            "                \"SYSTEMID\": \"fdb43d39a6244457915909e0aae719db\",\n" +
            "                \"EVENT_CODE\": \"440304010201805230120\",\n" +
            "                \"EVENT_NAME\": \"刘桂枝2018-05-23 20:31:55上报事件\",\n" +
            "                \"EVENT_CONTENT\": \"石厦西村25栋污水管破烂，污水乱流。\",\n" +
            "                \"EVENT_TIME\": \"2018-05-23 20:31:55\",\n" +
            "                \"EVENT_SOURCE\": \"90\",\n" +
            "                \"ISDELETE\": \"0\",\n" +
            "                \"ADDRESS\": \"广东省深圳市福田区石厦四街246号\",\n" +
            "                \"STATE\": \"3\",\n" +
            "                \"CREATER_ID\": \"liuguizhi\",\n" +
            "                \"CREATER_DEPTID\": \"440304010005\",\n" +
            "                \"CREATER_DEPT\": \"石厦社区\",\n" +
            "                \"REPORT_NAME\": null,\n" +
            "                \"REPORT_CODE_TYPE\": null,\n" +
            "                \"REPORT_CODE\": null,\n" +
            "                \"REPORT_ADDRESS\": null,\n" +
            "                \"REPORT_PHONE\": null,\n" +
            "                \"FLOWSTATE\": \"执法员办理\",\n" +
            "                \"FLOWSTATEID\": \"13\",\n" +
            "                \"TEMPLATEID\": null,\n" +
            "                \"EVENT_TYPE\": \"下水道堵塞或破损\",\n" +
            "                \"EVENT_TYPEID\": \"10060089\",\n" +
            "                \"EVENT_LV\": \"01\",\n" +
            "                \"SOURCE_DEPTID\": null,\n" +
            "                \"SOURCE_DEPT\": null,\n" +
            "                \"SOURCE_ID\": null,\n" +
            "                \"SOURCE_BLSX\": null,\n" +
            "                \"TIME_LIMIT\": \"72\",\n" +
            "                \"TIME_SL\": \"60\",\n" +
            "                \"EVENT_KEYWORD\": null,\n" +
            "                \"REMARK\": null,\n" +
            "                \"SHENG\": \"广东省\",\n" +
            "                \"SHENGDM\": \"44\",\n" +
            "                \"SHI\": \"深圳市\",\n" +
            "                \"SHIDM\": \"03\",\n" +
            "                \"QU\": \"福田区\",\n" +
            "                \"QUDM\": \"440304\",\n" +
            "                \"JD\": \"福保街道\",\n" +
            "                \"JDDM\": \"440304010\",\n" +
            "                \"SQ\": \"新港社区\",\n" +
            "                \"SQDM\": \"440304010001\",\n" +
            "                \"WG\": \"新港03\",\n" +
            "                \"WGDM\": \"440304010001003\",\n" +
            "                \"DL\": \"石厦四街\",\n" +
            "                \"DLDM\": null,\n" +
            "                \"XQ\": \"民政局婚姻登记处\",\n" +
            "                \"XQDM\": null,\n" +
            "                \"LD\": null,\n" +
            "                \"LDDM\": \"5dacd2a95fc9456796f0376bae404c44\",\n" +
            "                \"FW\": \"石厦四街233号（区计生服务中心岗亭)101\",\n" +
            "                \"FWDM\": \"44030400401103T0002000001\",\n" +
            "                \"BZDZ\": null,\n" +
            "                \"FLOWKEY\": null,\n" +
            "                \"FBTIME\": \"2018-05-23 20:33:53\",\n" +
            "                \"BJTIME\": \"2018-06-20 12:58:29\",\n" +
            "                \"GQTIME\": \"2018-06-20 00:00:00\",\n" +
            "                \"GDTIME\": \"2018-06-20 00:00:00\",\n" +
            "                \"PJTIME\": \"2018-09-05 15:11:51\",\n" +
            "                \"DBTIME\": \"2018-06-20 00:00:00\",\n" +
            "                \"PSTIME\": \"2018-06-20 00:00:00\",\n" +
            "                \"GD_EVENT_TYPE\": \"下水道堵塞或破损\",\n" +
            "                \"GD_EVENT_TYPEID\": \"10060089\",\n" +
            "                \"GQ_TYPE\": null,\n" +
            "                \"GQ_REMARK\": null,\n" +
            "                \"LRR\": null,\n" +
            "                \"LRRQ\": \"2018-06-20 00:00:00\",\n" +
            "                \"GXR\": null,\n" +
            "                \"GXSJ\": \"2018-06-20 00:00:00\",\n" +
            "                \"LON\": \"113778.73597326875\",\n" +
            "                \"LAT\": \"17100.39674403332\",\n" +
            "                \"TASK_ID\": null,\n" +
            "                \"USER_ID\": null,\n" +
            "                \"STATUS\": null,\n" +
            "                \"CTIME\": \"2018-05-23 20:33:47\"\n" +
            "            }\n" +
            "        }\n" +
            "    ]\n" +
            "}";

}
