package com.cetc.cloud.test;

import com.cetc.cloud.datasynch.provider.util.JsonExtractor;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

/**
 * PackageName:   com.cetc.cloud.test
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/11/11 13:57
 * Updater:     by luolinjie
 * Update_Date: 2018/11/11
 * Update_Description: luolinjie 补充
 **/
public class ExtractRuleTest {
    private String data = "{\n" +
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
            "\t\t\"features\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"attributes\": {\n" +
            "\t\t\t\t\t\"OID\": \"8456\",\n" +
            "\t\t\t\t\t\"GEOMETRY\": null,\n" +
            "\t\t\t\t\t\"SYSTEMID\": \"e0e660c04f474a9bacaf7d1d04e474a9\",\n" +
            "\t\t\t\t\t\"EVENT_CODE\": \"440304010201805180068\",\n" +
            "\t\t\t\t\t\"EVENT_NAME\": \"姚春兰2018-05-18 17:59:35上报事件\",\n" +
            "\t\t\t\t\t\"EVENT_CONTENT\": \"益田村58栋104无证经营\",\n" +
            "\t\t\t\t\t\"EVENT_TIME\": \"2018-05-18 17:59:35\",\n" +
            "\t\t\t\t\t\"EVENT_SOURCE\": \"90\",\n" +
            "\t\t\t\t\t\"ISDELETE\": \"0\",\n" +
            "\t\t\t\t\t\"ADDRESS\": \"广东省深圳市福田区益田五路\",\n" +
            "\t\t\t\t\t\"STATE\": \"1\",\n" +
            "\t\t\t\t\t\"CREATER_ID\": \"yaochunlan\",\n" +
            "\t\t\t\t\t\"CREATER_DEPTID\": \"440304010003\",\n" +
            "\t\t\t\t\t\"CREATER_DEPT\": \"益田社区\",\n" +
            "\t\t\t\t\t\"REPORT_NAME\": null,\n" +
            "\t\t\t\t\t\"REPORT_CODE_TYPE\": null,\n" +
            "\t\t\t\t\t\"REPORT_CODE\": null,\n" +
            "\t\t\t\t\t\"REPORT_ADDRESS\": null,\n" +
            "\t\t\t\t\t\"REPORT_PHONE\": null,\n" +
            "\t\t\t\t\t\"FLOWSTATE\": \"区智慧指挥中心\",\n" +
            "\t\t\t\t\t\"FLOWSTATEID\": \"22\",\n" +
            "\t\t\t\t\t\"TEMPLATEID\": null,\n" +
            "\t\t\t\t\t\"EVENT_TYPE\": \"无照违法经营\",\n" +
            "\t\t\t\t\t\"EVENT_TYPEID\": \"10130154\",\n" +
            "\t\t\t\t\t\"EVENT_LV\": \"01\",\n" +
            "\t\t\t\t\t\"SOURCE_DEPTID\": null,\n" +
            "\t\t\t\t\t\"SOURCE_DEPT\": null,\n" +
            "\t\t\t\t\t\"SOURCE_ID\": null,\n" +
            "\t\t\t\t\t\"SOURCE_BLSX\": null,\n" +
            "\t\t\t\t\t\"TIME_LIMIT\": \"72\",\n" +
            "\t\t\t\t\t\"TIME_SL\": \"60\",\n" +
            "\t\t\t\t\t\"EVENT_KEYWORD\": null,\n" +
            "\t\t\t\t\t\"REMARK\": null,\n" +
            "\t\t\t\t\t\"SHENG\": \"广东省\",\n" +
            "\t\t\t\t\t\"SHENGDM\": \"44\",\n" +
            "\t\t\t\t\t\"SHI\": \"深圳市\",\n" +
            "\t\t\t\t\t\"SHIDM\": \"4403\",\n" +
            "\t\t\t\t\t\"QU\": \"福田区\",\n" +
            "\t\t\t\t\t\"QUDM\": \"440304\",\n" +
            "\t\t\t\t\t\"JD\": \"福保街道\",\n" +
            "\t\t\t\t\t\"JDDM\": \"440304010\",\n" +
            "\t\t\t\t\t\"SQ\": \"新港社区\",\n" +
            "\t\t\t\t\t\"SQDM\": \"440304010001\",\n" +
            "\t\t\t\t\t\"WG\": \"新港07\",\n" +
            "\t\t\t\t\t\"WGDM\": \"440304010001007\",\n" +
            "\t\t\t\t\t\"DL\": \"益田五路\",\n" +
            "\t\t\t\t\t\"DLDM\": null,\n" +
            "\t\t\t\t\t\"XQ\": \"(福田花园治安岗亭)\",\n" +
            "\t\t\t\t\t\"XQDM\": null,\n" +
            "\t\t\t\t\t\"LD\": null,\n" +
            "\t\t\t\t\t\"LDDM\": \"4403040040110700005\",\n" +
            "\t\t\t\t\t\"FW\": \"益田五路49号福田花园1栋606\",\n" +
            "\t\t\t\t\t\"FWDM\": \"4403040040110700005000036\",\n" +
            "\t\t\t\t\t\"BZDZ\": null,\n" +
            "\t\t\t\t\t\"FLOWKEY\": null,\n" +
            "\t\t\t\t\t\"FBTIME\": \"2018-05-18 18:01:30\",\n" +
            "\t\t\t\t\t\"BJTIME\": null,\n" +
            "\t\t\t\t\t\"GQTIME\": null,\n" +
            "\t\t\t\t\t\"GDTIME\": null,\n" +
            "\t\t\t\t\t\"PJTIME\": null,\n" +
            "\t\t\t\t\t\"DBTIME\": null,\n" +
            "\t\t\t\t\t\"PSTIME\": null,\n" +
            "\t\t\t\t\t\"GD_EVENT_TYPE\": null,\n" +
            "\t\t\t\t\t\"GD_EVENT_TYPEID\": null,\n" +
            "\t\t\t\t\t\"GQ_TYPE\": null,\n" +
            "\t\t\t\t\t\"GQ_REMARK\": null,\n" +
            "\t\t\t\t\t\"LRR\": null,\n" +
            "\t\t\t\t\t\"LRRQ\": null,\n" +
            "\t\t\t\t\t\"GXR\": null,\n" +
            "\t\t\t\t\t\"GXSJ\": null,\n" +
            "\t\t\t\t\t\"LON\": \"113621.390759928\",\n" +
            "\t\t\t\t\t\"LAT\": \"16365.8700269759\",\n" +
            "\t\t\t\t\t\"TASK_ID\": null,\n" +
            "\t\t\t\t\t\"USER_ID\": null,\n" +
            "\t\t\t\t\t\"STATUS\": null,\n" +
            "\t\t\t\t\t\"CTIME\": \"2018-05-18 18:01:27\"\n" +
            "\t\t\t\t}\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"attributes\": {\n" +
            "\t\t\t\t\t\"OID\": \"8457\",\n" +
            "\t\t\t\t\t\"GEOMETRY\": null,\n" +
            "\t\t\t\t\t\"SYSTEMID\": \"a4828fd6af54443f88330bdb1a7a90e9\",\n" +
            "\t\t\t\t\t\"EVENT_CODE\": \"440304010201805230100\",\n" +
            "\t\t\t\t\t\"EVENT_NAME\": \"李卫芹2018-05-23 19:08:59上报事件\",\n" +
            "\t\t\t\t\t\"EVENT_CONTENT\": \"石厦北三街菜市场乱堆放东西每天都好臭存在市容市貌隐患\",\n" +
            "\t\t\t\t\t\"EVENT_TIME\": \"2018-05-23 19:08:59\",\n" +
            "\t\t\t\t\t\"EVENT_SOURCE\": \"90\",\n" +
            "\t\t\t\t\t\"ISDELETE\": \"0\",\n" +
            "\t\t\t\t\t\"ADDRESS\": \"广东省深圳市福田区石厦北三街7-7\",\n" +
            "\t\t\t\t\t\"STATE\": \"1\",\n" +
            "\t\t\t\t\t\"CREATER_ID\": \"liweiqin\",\n" +
            "\t\t\t\t\t\"CREATER_DEPTID\": \"440304010004\",\n" +
            "\t\t\t\t\t\"CREATER_DEPT\": \"明月社区\",\n" +
            "\t\t\t\t\t\"REPORT_NAME\": null,\n" +
            "\t\t\t\t\t\"REPORT_CODE_TYPE\": null,\n" +
            "\t\t\t\t\t\"REPORT_CODE\": null,\n" +
            "\t\t\t\t\t\"REPORT_ADDRESS\": null,\n" +
            "\t\t\t\t\t\"REPORT_PHONE\": null,\n" +
            "\t\t\t\t\t\"FLOWSTATE\": \"执法员办理\",\n" +
            "\t\t\t\t\t\"FLOWSTATEID\": \"12\",\n" +
            "\t\t\t\t\t\"TEMPLATEID\": null,\n" +
            "\t\t\t\t\t\"EVENT_TYPE\": \"乱堆放、乱摆卖\",\n" +
            "\t\t\t\t\t\"EVENT_TYPEID\": \"10020033\",\n" +
            "\t\t\t\t\t\"EVENT_LV\": \"01\",\n" +
            "\t\t\t\t\t\"SOURCE_DEPTID\": null,\n" +
            "\t\t\t\t\t\"SOURCE_DEPT\": null,\n" +
            "\t\t\t\t\t\"SOURCE_ID\": null,\n" +
            "\t\t\t\t\t\"SOURCE_BLSX\": null,\n" +
            "\t\t\t\t\t\"TIME_LIMIT\": \"72\",\n" +
            "\t\t\t\t\t\"TIME_SL\": \"60\",\n" +
            "\t\t\t\t\t\"EVENT_KEYWORD\": null,\n" +
            "\t\t\t\t\t\"REMARK\": null,\n" +
            "\t\t\t\t\t\"SHENG\": \"广东省\",\n" +
            "\t\t\t\t\t\"SHENGDM\": \"44\",\n" +
            "\t\t\t\t\t\"SHI\": \"深圳市\",\n" +
            "\t\t\t\t\t\"SHIDM\": \"4403\",\n" +
            "\t\t\t\t\t\"QU\": \"福田区\",\n" +
            "\t\t\t\t\t\"QUDM\": \"440304\",\n" +
            "\t\t\t\t\t\"JD\": \"福保街道\",\n" +
            "\t\t\t\t\t\"JDDM\": \"440304010\",\n" +
            "\t\t\t\t\t\"SQ\": \"明月社区\",\n" +
            "\t\t\t\t\t\"SQDM\": \"440304010004\",\n" +
            "\t\t\t\t\t\"WG\": \"明月09\",\n" +
            "\t\t\t\t\t\"WGDM\": \"440304010004009\",\n" +
            "\t\t\t\t\t\"DL\": \"石厦北三街\",\n" +
            "\t\t\t\t\t\"DLDM\": \"2307\",\n" +
            "\t\t\t\t\t\"XQ\": \"华嵘净菜市场\",\n" +
            "\t\t\t\t\t\"XQDM\": null,\n" +
            "\t\t\t\t\t\"LD\": null,\n" +
            "\t\t\t\t\t\"LDDM\": \"4403040040050700012\",\n" +
            "\t\t\t\t\t\"FW\": \"石厦北三街7号华荣净菜市场2层\",\n" +
            "\t\t\t\t\t\"FWDM\": \"4403040040050700012000027\",\n" +
            "\t\t\t\t\t\"BZDZ\": null,\n" +
            "\t\t\t\t\t\"FLOWKEY\": null,\n" +
            "\t\t\t\t\t\"FBTIME\": \"2018-05-23 19:11:04\",\n" +
            "\t\t\t\t\t\"BJTIME\": null,\n" +
            "\t\t\t\t\t\"GQTIME\": null,\n" +
            "\t\t\t\t\t\"GDTIME\": null,\n" +
            "\t\t\t\t\t\"PJTIME\": null,\n" +
            "\t\t\t\t\t\"DBTIME\": null,\n" +
            "\t\t\t\t\t\"PSTIME\": null,\n" +
            "\t\t\t\t\t\"GD_EVENT_TYPE\": null,\n" +
            "\t\t\t\t\t\"GD_EVENT_TYPEID\": null,\n" +
            "\t\t\t\t\t\"GQ_TYPE\": null,\n" +
            "\t\t\t\t\t\"GQ_REMARK\": null,\n" +
            "\t\t\t\t\t\"LRR\": null,\n" +
            "\t\t\t\t\t\"LRRQ\": null,\n" +
            "\t\t\t\t\t\"GXR\": null,\n" +
            "\t\t\t\t\t\"GXSJ\": null,\n" +
            "\t\t\t\t\t\"LON\": \"114220.968207227\",\n" +
            "\t\t\t\t\t\"LAT\": \"17625.4927639477\",\n" +
            "\t\t\t\t\t\"TASK_ID\": null,\n" +
            "\t\t\t\t\t\"USER_ID\": null,\n" +
            "\t\t\t\t\t\"STATUS\": null,\n" +
            "\t\t\t\t\t\"CTIME\": \"2018-05-23 19:10:50\"\n" +
            "\t\t\t\t}\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"attributes\": {\n" +
            "\t\t\t\t\t\"OID\": \"8458\",\n" +
            "\t\t\t\t\t\"GEOMETRY\": null,\n" +
            "\t\t\t\t\t\"SYSTEMID\": \"0a976080e9284ba4a6834bfa04227f37\",\n" +
            "\t\t\t\t\t\"EVENT_CODE\": \"440304010201805230105\",\n" +
            "\t\t\t\t\t\"EVENT_NAME\": \"梁玲2018-05-23 19:55:59上报事件\",\n" +
            "\t\t\t\t\t\"EVENT_CONTENT\": \"石厦东村296一702，集体宿舍，多人居住，电线乱拉，存在安全隐患\",\n" +
            "\t\t\t\t\t\"EVENT_TIME\": \"2018-05-23 19:55:59\",\n" +
            "\t\t\t\t\t\"EVENT_SOURCE\": \"90\",\n" +
            "\t\t\t\t\t\"ISDELETE\": \"0\",\n" +
            "\t\t\t\t\t\"ADDRESS\": \"广东省深圳市福田区福民路185-10\",\n" +
            "\t\t\t\t\t\"STATE\": \"3\",\n" +
            "\t\t\t\t\t\"CREATER_ID\": \"liangling\",\n" +
            "\t\t\t\t\t\"CREATER_DEPTID\": \"440304010005\",\n" +
            "\t\t\t\t\t\"CREATER_DEPT\": \"石厦社区\",\n" +
            "\t\t\t\t\t\"REPORT_NAME\": null,\n" +
            "\t\t\t\t\t\"REPORT_CODE_TYPE\": null,\n" +
            "\t\t\t\t\t\"REPORT_CODE\": null,\n" +
            "\t\t\t\t\t\"REPORT_ADDRESS\": null,\n" +
            "\t\t\t\t\t\"REPORT_PHONE\": null,\n" +
            "\t\t\t\t\t\"FLOWSTATE\": null,\n" +
            "\t\t\t\t\t\"FLOWSTATEID\": \"13\",\n" +
            "\t\t\t\t\t\"TEMPLATEID\": null,\n" +
            "\t\t\t\t\t\"EVENT_TYPE\": \"危险用电\",\n" +
            "\t\t\t\t\t\"EVENT_TYPEID\": \"10010032\",\n" +
            "\t\t\t\t\t\"EVENT_LV\": \"01\",\n" +
            "\t\t\t\t\t\"SOURCE_DEPTID\": null,\n" +
            "\t\t\t\t\t\"SOURCE_DEPT\": null,\n" +
            "\t\t\t\t\t\"SOURCE_ID\": null,\n" +
            "\t\t\t\t\t\"SOURCE_BLSX\": null,\n" +
            "\t\t\t\t\t\"TIME_LIMIT\": \"72\",\n" +
            "\t\t\t\t\t\"TIME_SL\": \"60\",\n" +
            "\t\t\t\t\t\"EVENT_KEYWORD\": null,\n" +
            "\t\t\t\t\t\"REMARK\": null,\n" +
            "\t\t\t\t\t\"SHENG\": \"广东省\",\n" +
            "\t\t\t\t\t\"SHENGDM\": \"44\",\n" +
            "\t\t\t\t\t\"SHI\": \"深圳市\",\n" +
            "\t\t\t\t\t\"SHIDM\": \"03\",\n" +
            "\t\t\t\t\t\"QU\": \"福田区\",\n" +
            "\t\t\t\t\t\"QUDM\": \"440304\",\n" +
            "\t\t\t\t\t\"JD\": \"福保街道\",\n" +
            "\t\t\t\t\t\"JDDM\": \"440304010\",\n" +
            "\t\t\t\t\t\"SQ\": \"石厦社区\",\n" +
            "\t\t\t\t\t\"SQDM\": \"440304010005\",\n" +
            "\t\t\t\t\t\"WG\": \"石厦01\",\n" +
            "\t\t\t\t\t\"WGDM\": \"440304010005001\",\n" +
            "\t\t\t\t\t\"DL\": \"福民路\",\n" +
            "\t\t\t\t\t\"DLDM\": \"3970\",\n" +
            "\t\t\t\t\t\"XQ\": \"众孚花园\",\n" +
            "\t\t\t\t\t\"XQDM\": \"5\",\n" +
            "\t\t\t\t\t\"LD\": null,\n" +
            "\t\t\t\t\t\"LDDM\": \"4403040040090100075\",\n" +
            "\t\t\t\t\t\"FW\": \"福民路185号众孚花园4栋704\",\n" +
            "\t\t\t\t\t\"FWDM\": \"4403040040090100075000028\",\n" +
            "\t\t\t\t\t\"BZDZ\": null,\n" +
            "\t\t\t\t\t\"FLOWKEY\": null,\n" +
            "\t\t\t\t\t\"FBTIME\": \"2018-05-23 19:57:33\",\n" +
            "\t\t\t\t\t\"BJTIME\": \"2018-05-24 15:28:02\",\n" +
            "\t\t\t\t\t\"GQTIME\": \"2018-09-05 00:00:00\",\n" +
            "\t\t\t\t\t\"GDTIME\": \"2018-09-05 00:00:00\",\n" +
            "\t\t\t\t\t\"PJTIME\": \"2018-09-05 15:07:55\",\n" +
            "\t\t\t\t\t\"DBTIME\": \"2018-09-05 00:00:00\",\n" +
            "\t\t\t\t\t\"PSTIME\": \"2018-09-05 00:00:00\",\n" +
            "\t\t\t\t\t\"GD_EVENT_TYPE\": \"危险用电\",\n" +
            "\t\t\t\t\t\"GD_EVENT_TYPEID\": \"10010032\",\n" +
            "\t\t\t\t\t\"GQ_TYPE\": null,\n" +
            "\t\t\t\t\t\"GQ_REMARK\": null,\n" +
            "\t\t\t\t\t\"LRR\": null,\n" +
            "\t\t\t\t\t\"LRRQ\": \"2018-09-05 00:00:00\",\n" +
            "\t\t\t\t\t\"GXR\": null,\n" +
            "\t\t\t\t\t\"GXSJ\": \"2018-09-05 00:00:00\",\n" +
            "\t\t\t\t\t\"LON\": \"113976.0958407186\",\n" +
            "\t\t\t\t\t\"LAT\": \"17240.82653985452\",\n" +
            "\t\t\t\t\t\"TASK_ID\": null,\n" +
            "\t\t\t\t\t\"USER_ID\": null,\n" +
            "\t\t\t\t\t\"STATUS\": null,\n" +
            "\t\t\t\t\t\"CTIME\": \"2018-05-23 19:57:26\"\n" +
            "\t\t\t\t}\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"attributes\": {\n" +
            "\t\t\t\t\t\"OID\": \"8459\",\n" +
            "\t\t\t\t\t\"GEOMETRY\": null,\n" +
            "\t\t\t\t\t\"SYSTEMID\": \"dbb55d15212543a39e668caf7f77aba2\",\n" +
            "\t\t\t\t\t\"EVENT_CODE\": \"440304010201805230108\",\n" +
            "\t\t\t\t\t\"EVENT_NAME\": \"冯琼华2018-05-23 20:00:53上报事件\",\n" +
            "\t\t\t\t\t\"EVENT_CONTENT\": \"福田花园大厦A座14楼安全出口堵塞\",\n" +
            "\t\t\t\t\t\"EVENT_TIME\": \"2018-05-23 20:00:53\",\n" +
            "\t\t\t\t\t\"EVENT_SOURCE\": \"90\",\n" +
            "\t\t\t\t\t\"ISDELETE\": \"0\",\n" +
            "\t\t\t\t\t\"ADDRESS\": \"广东省深圳市福田区福强路3031号\",\n" +
            "\t\t\t\t\t\"STATE\": \"3\",\n" +
            "\t\t\t\t\t\"CREATER_ID\": \"fengqionghua\",\n" +
            "\t\t\t\t\t\"CREATER_DEPTID\": \"440304010001\",\n" +
            "\t\t\t\t\t\"CREATER_DEPT\": \"新港社区\",\n" +
            "\t\t\t\t\t\"REPORT_NAME\": null,\n" +
            "\t\t\t\t\t\"REPORT_CODE_TYPE\": null,\n" +
            "\t\t\t\t\t\"REPORT_CODE\": null,\n" +
            "\t\t\t\t\t\"REPORT_ADDRESS\": null,\n" +
            "\t\t\t\t\t\"REPORT_PHONE\": null,\n" +
            "\t\t\t\t\t\"FLOWSTATE\": null,\n" +
            "\t\t\t\t\t\"FLOWSTATEID\": \"13\",\n" +
            "\t\t\t\t\t\"TEMPLATEID\": null,\n" +
            "\t\t\t\t\t\"EVENT_TYPE\": \"安全出口、逃生通道不畅\",\n" +
            "\t\t\t\t\t\"EVENT_TYPEID\": \"10160005\",\n" +
            "\t\t\t\t\t\"EVENT_LV\": \"01\",\n" +
            "\t\t\t\t\t\"SOURCE_DEPTID\": null,\n" +
            "\t\t\t\t\t\"SOURCE_DEPT\": null,\n" +
            "\t\t\t\t\t\"SOURCE_ID\": null,\n" +
            "\t\t\t\t\t\"SOURCE_BLSX\": null,\n" +
            "\t\t\t\t\t\"TIME_LIMIT\": \"72\",\n" +
            "\t\t\t\t\t\"TIME_SL\": \"60\",\n" +
            "\t\t\t\t\t\"EVENT_KEYWORD\": null,\n" +
            "\t\t\t\t\t\"REMARK\": null,\n" +
            "\t\t\t\t\t\"SHENG\": \"广东省\",\n" +
            "\t\t\t\t\t\"SHENGDM\": \"44\",\n" +
            "\t\t\t\t\t\"SHI\": \"深圳市\",\n" +
            "\t\t\t\t\t\"SHIDM\": \"03\",\n" +
            "\t\t\t\t\t\"QU\": \"福田区\",\n" +
            "\t\t\t\t\t\"QUDM\": \"440304\",\n" +
            "\t\t\t\t\t\"JD\": \"福保街道\",\n" +
            "\t\t\t\t\t\"JDDM\": \"440304010\",\n" +
            "\t\t\t\t\t\"SQ\": \"新港社区\",\n" +
            "\t\t\t\t\t\"SQDM\": \"440304010001\",\n" +
            "\t\t\t\t\t\"WG\": \"新港07\",\n" +
            "\t\t\t\t\t\"WGDM\": \"440304010001007\",\n" +
            "\t\t\t\t\t\"DL\": \"福强路\",\n" +
            "\t\t\t\t\t\"DLDM\": \"2723\",\n" +
            "\t\t\t\t\t\"XQ\": \"中国电信益田营业厅\",\n" +
            "\t\t\t\t\t\"XQDM\": null,\n" +
            "\t\t\t\t\t\"LD\": null,\n" +
            "\t\t\t\t\t\"LDDM\": \"4403040040110700001\",\n" +
            "\t\t\t\t\t\"FW\": \"福强路3031号中国电信益田营业厅101\",\n" +
            "\t\t\t\t\t\"FWDM\": \"4403040040110700001000001\",\n" +
            "\t\t\t\t\t\"BZDZ\": null,\n" +
            "\t\t\t\t\t\"FLOWKEY\": null,\n" +
            "\t\t\t\t\t\"FBTIME\": \"2018-05-23 20:02:43\",\n" +
            "\t\t\t\t\t\"BJTIME\": \"2018-05-24 16:34:08\",\n" +
            "\t\t\t\t\t\"GQTIME\": \"2018-09-05 00:00:00\",\n" +
            "\t\t\t\t\t\"GDTIME\": \"2018-09-05 00:00:00\",\n" +
            "\t\t\t\t\t\"PJTIME\": \"2018-09-05 15:10:38\",\n" +
            "\t\t\t\t\t\"DBTIME\": \"2018-09-05 00:00:00\",\n" +
            "\t\t\t\t\t\"PSTIME\": \"2018-09-05 00:00:00\",\n" +
            "\t\t\t\t\t\"GD_EVENT_TYPE\": \"安全出口、逃生通道不畅\",\n" +
            "\t\t\t\t\t\"GD_EVENT_TYPEID\": \"10160005\",\n" +
            "\t\t\t\t\t\"GQ_TYPE\": null,\n" +
            "\t\t\t\t\t\"GQ_REMARK\": null,\n" +
            "\t\t\t\t\t\"LRR\": null,\n" +
            "\t\t\t\t\t\"LRRQ\": \"2018-09-05 00:00:00\",\n" +
            "\t\t\t\t\t\"GXR\": null,\n" +
            "\t\t\t\t\t\"GXSJ\": \"2018-09-05 00:00:00\",\n" +
            "\t\t\t\t\t\"LON\": \"-11631349.879805898\",\n" +
            "\t\t\t\t\t\"LAT\": \"-2214613.8255822756\",\n" +
            "\t\t\t\t\t\"TASK_ID\": null,\n" +
            "\t\t\t\t\t\"USER_ID\": null,\n" +
            "\t\t\t\t\t\"STATUS\": null,\n" +
            "\t\t\t\t\t\"CTIME\": \"2018-05-23 20:02:32\"\n" +
            "\t\t\t\t}\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"attributes\": {\n" +
            "\t\t\t\t\t\"OID\": \"8460\",\n" +
            "\t\t\t\t\t\"GEOMETRY\": null,\n" +
            "\t\t\t\t\t\"SYSTEMID\": \"fdb43d39a6244457915909e0aae719db\",\n" +
            "\t\t\t\t\t\"EVENT_CODE\": \"440304010201805230120\",\n" +
            "\t\t\t\t\t\"EVENT_NAME\": \"刘桂枝2018-05-23 20:31:55上报事件\",\n" +
            "\t\t\t\t\t\"EVENT_CONTENT\": \"石厦西村25栋污水管破烂，污水乱流。\",\n" +
            "\t\t\t\t\t\"EVENT_TIME\": \"2018-05-23 20:31:55\",\n" +
            "\t\t\t\t\t\"EVENT_SOURCE\": \"90\",\n" +
            "\t\t\t\t\t\"ISDELETE\": \"0\",\n" +
            "\t\t\t\t\t\"ADDRESS\": \"广东省深圳市福田区石厦四街246号\",\n" +
            "\t\t\t\t\t\"STATE\": \"3\",\n" +
            "\t\t\t\t\t\"CREATER_ID\": \"liuguizhi\",\n" +
            "\t\t\t\t\t\"CREATER_DEPTID\": \"440304010005\",\n" +
            "\t\t\t\t\t\"CREATER_DEPT\": \"石厦社区\",\n" +
            "\t\t\t\t\t\"REPORT_NAME\": null,\n" +
            "\t\t\t\t\t\"REPORT_CODE_TYPE\": null,\n" +
            "\t\t\t\t\t\"REPORT_CODE\": null,\n" +
            "\t\t\t\t\t\"REPORT_ADDRESS\": null,\n" +
            "\t\t\t\t\t\"REPORT_PHONE\": null,\n" +
            "\t\t\t\t\t\"FLOWSTATE\": \"执法员办理\",\n" +
            "\t\t\t\t\t\"FLOWSTATEID\": \"13\",\n" +
            "\t\t\t\t\t\"TEMPLATEID\": null,\n" +
            "\t\t\t\t\t\"EVENT_TYPE\": \"下水道堵塞或破损\",\n" +
            "\t\t\t\t\t\"EVENT_TYPEID\": \"10060089\",\n" +
            "\t\t\t\t\t\"EVENT_LV\": \"01\",\n" +
            "\t\t\t\t\t\"SOURCE_DEPTID\": null,\n" +
            "\t\t\t\t\t\"SOURCE_DEPT\": null,\n" +
            "\t\t\t\t\t\"SOURCE_ID\": null,\n" +
            "\t\t\t\t\t\"SOURCE_BLSX\": null,\n" +
            "\t\t\t\t\t\"TIME_LIMIT\": \"72\",\n" +
            "\t\t\t\t\t\"TIME_SL\": \"60\",\n" +
            "\t\t\t\t\t\"EVENT_KEYWORD\": null,\n" +
            "\t\t\t\t\t\"REMARK\": null,\n" +
            "\t\t\t\t\t\"SHENG\": \"广东省\",\n" +
            "\t\t\t\t\t\"SHENGDM\": \"44\",\n" +
            "\t\t\t\t\t\"SHI\": \"深圳市\",\n" +
            "\t\t\t\t\t\"SHIDM\": \"03\",\n" +
            "\t\t\t\t\t\"QU\": \"福田区\",\n" +
            "\t\t\t\t\t\"QUDM\": \"440304\",\n" +
            "\t\t\t\t\t\"JD\": \"福保街道\",\n" +
            "\t\t\t\t\t\"JDDM\": \"440304010\",\n" +
            "\t\t\t\t\t\"SQ\": \"新港社区\",\n" +
            "\t\t\t\t\t\"SQDM\": \"440304010001\",\n" +
            "\t\t\t\t\t\"WG\": \"新港03\",\n" +
            "\t\t\t\t\t\"WGDM\": \"440304010001003\",\n" +
            "\t\t\t\t\t\"DL\": \"石厦四街\",\n" +
            "\t\t\t\t\t\"DLDM\": null,\n" +
            "\t\t\t\t\t\"XQ\": \"民政局婚姻登记处\",\n" +
            "\t\t\t\t\t\"XQDM\": null,\n" +
            "\t\t\t\t\t\"LD\": null,\n" +
            "\t\t\t\t\t\"LDDM\": \"5dacd2a95fc9456796f0376bae404c44\",\n" +
            "\t\t\t\t\t\"FW\": \"石厦四街233号（区计生服务中心岗亭)101\",\n" +
            "\t\t\t\t\t\"FWDM\": \"44030400401103T0002000001\",\n" +
            "\t\t\t\t\t\"BZDZ\": null,\n" +
            "\t\t\t\t\t\"FLOWKEY\": null,\n" +
            "\t\t\t\t\t\"FBTIME\": \"2018-05-23 20:33:53\",\n" +
            "\t\t\t\t\t\"BJTIME\": \"2018-06-20 12:58:29\",\n" +
            "\t\t\t\t\t\"GQTIME\": \"2018-06-20 00:00:00\",\n" +
            "\t\t\t\t\t\"GDTIME\": \"2018-06-20 00:00:00\",\n" +
            "\t\t\t\t\t\"PJTIME\": \"2018-09-05 15:11:51\",\n" +
            "\t\t\t\t\t\"DBTIME\": \"2018-06-20 00:00:00\",\n" +
            "\t\t\t\t\t\"PSTIME\": \"2018-06-20 00:00:00\",\n" +
            "\t\t\t\t\t\"GD_EVENT_TYPE\": \"下水道堵塞或破损\",\n" +
            "\t\t\t\t\t\"GD_EVENT_TYPEID\": \"10060089\",\n" +
            "\t\t\t\t\t\"GQ_TYPE\": null,\n" +
            "\t\t\t\t\t\"GQ_REMARK\": null,\n" +
            "\t\t\t\t\t\"LRR\": null,\n" +
            "\t\t\t\t\t\"LRRQ\": \"2018-06-20 00:00:00\",\n" +
            "\t\t\t\t\t\"GXR\": null,\n" +
            "\t\t\t\t\t\"GXSJ\": \"2018-06-20 00:00:00\",\n" +
            "\t\t\t\t\t\"LON\": \"113778.73597326875\",\n" +
            "\t\t\t\t\t\"LAT\": \"17100.39674403332\",\n" +
            "\t\t\t\t\t\"TASK_ID\": null,\n" +
            "\t\t\t\t\t\"USER_ID\": null,\n" +
            "\t\t\t\t\t\"STATUS\": null,\n" +
            "\t\t\t\t\t\"CTIME\": \"2018-05-23 20:33:47\"\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "}";
    private String data1 = "{\n"+
            "    \"datasetId\": \"4d6de370-69db-412d-81c8-74d182360e85\",\n"+
            "    \"dataTitle\": \"事件信息\",\n"+
            "    \"fields\": [\n"+
            "        {\n"+
            "            \"name\": \"OID\",\n"+
            "            \"type\": \"NUMBER\",\n"+
            "            \"length\": 22\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"GEOMETRY\",\n"+
            "            \"type\": \"MDSYS.SDO_GEOMETRY\",\n"+
            "            \"length\": 2000\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"SYSTEMID\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"EVENT_CODE\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"EVENT_NAME\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"EVENT_CONTENT\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"EVENT_TIME\",\n"+
            "            \"type\": \"DATE\",\n"+
            "            \"length\": 7\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"EVENT_SOURCE\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"ISDELETE\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"ADDRESS\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"STATE\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"CREATER_ID\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"CREATER_DEPTID\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"CREATER_DEPT\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"REPORT_NAME\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"REPORT_CODE_TYPE\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"REPORT_CODE\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"REPORT_ADDRESS\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"REPORT_PHONE\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"FLOWSTATE\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"FLOWSTATEID\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"TEMPLATEID\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"EVENT_TYPE\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"EVENT_TYPEID\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"EVENT_LV\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"SOURCE_DEPTID\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"SOURCE_DEPT\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"SOURCE_ID\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"SOURCE_BLSX\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 128\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"TIME_LIMIT\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 128\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"TIME_SL\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 128\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"EVENT_KEYWORD\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"REMARK\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"SHENG\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"SHENGDM\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"SHI\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"SHIDM\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"QU\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"QUDM\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"JD\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"JDDM\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"SQ\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"SQDM\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"WG\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"WGDM\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"DL\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"DLDM\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"XQ\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"XQDM\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"LD\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"LDDM\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"FW\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"FWDM\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"BZDZ\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"FLOWKEY\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"FBTIME\",\n"+
            "            \"type\": \"DATE\",\n"+
            "            \"length\": 7\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"BJTIME\",\n"+
            "            \"type\": \"DATE\",\n"+
            "            \"length\": 7\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"GQTIME\",\n"+
            "            \"type\": \"DATE\",\n"+
            "            \"length\": 7\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"GDTIME\",\n"+
            "            \"type\": \"DATE\",\n"+
            "            \"length\": 7\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"PJTIME\",\n"+
            "            \"type\": \"DATE\",\n"+
            "            \"length\": 7\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"DBTIME\",\n"+
            "            \"type\": \"DATE\",\n"+
            "            \"length\": 7\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"PSTIME\",\n"+
            "            \"type\": \"DATE\",\n"+
            "            \"length\": 7\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"GD_EVENT_TYPE\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"GD_EVENT_TYPEID\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"GQ_TYPE\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"GQ_REMARK\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"LRR\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"LRRQ\",\n"+
            "            \"type\": \"DATE\",\n"+
            "            \"length\": 7\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"GXR\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"GXSJ\",\n"+
            "            \"type\": \"DATE\",\n"+
            "            \"length\": 7\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"LON\",\n"+
            "            \"type\": \"NUMBER\",\n"+
            "            \"length\": 22\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"LAT\",\n"+
            "            \"type\": \"NUMBER\",\n"+
            "            \"length\": 22\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"TASK_ID\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"USER_ID\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"STATUS\",\n"+
            "            \"type\": \"VARCHAR2\",\n"+
            "            \"length\": 255\n"+
            "        },\n"+
            "        {\n"+
            "            \"name\": \"CTIME\",\n"+
            "            \"type\": \"DATE\",\n"+
            "            \"length\": 7\n"+
            "        }\n"+
            "    ],\n"+
            "\t\t\"features\": [\n"+
            "\t\t\t{\n"+
            "\t\t\t\t\n"+
            "\t\t\t\t\t\"OID\": \"8456\",\n"+
            "\t\t\t\t\t\"GEOMETRY\": null,\n"+
            "\t\t\t\t\t\"SYSTEMID\": \"e0e660c04f474a9bacaf7d1d04e474a9\",\n"+
            "\t\t\t\t\t\"EVENT_CODE\": \"440304010201805180068\",\n"+
            "\t\t\t\t\t\"EVENT_NAME\": \"姚春兰2018-05-18 17:59:35上报事件\",\n"+
            "\t\t\t\t\t\"EVENT_CONTENT\": \"益田村58栋104无证经营\",\n"+
            "\t\t\t\t\t\"EVENT_TIME\": \"2018-05-18 17:59:35\",\n"+
            "\t\t\t\t\t\"EVENT_SOURCE\": \"90\",\n"+
            "\t\t\t\t\t\"ISDELETE\": \"0\",\n"+
            "\t\t\t\t\t\"ADDRESS\": \"广东省深圳市福田区益田五路\",\n"+
            "\t\t\t\t\t\"STATE\": \"1\",\n"+
            "\t\t\t\t\t\"CREATER_ID\": \"yaochunlan\",\n"+
            "\t\t\t\t\t\"CREATER_DEPTID\": \"440304010003\",\n"+
            "\t\t\t\t\t\"CREATER_DEPT\": \"益田社区\",\n"+
            "\t\t\t\t\t\"REPORT_NAME\": null,\n"+
            "\t\t\t\t\t\"REPORT_CODE_TYPE\": null,\n"+
            "\t\t\t\t\t\"REPORT_CODE\": null,\n"+
            "\t\t\t\t\t\"REPORT_ADDRESS\": null,\n"+
            "\t\t\t\t\t\"REPORT_PHONE\": null,\n"+
            "\t\t\t\t\t\"FLOWSTATE\": \"区智慧指挥中心\",\n"+
            "\t\t\t\t\t\"FLOWSTATEID\": \"22\",\n"+
            "\t\t\t\t\t\"TEMPLATEID\": null,\n"+
            "\t\t\t\t\t\"EVENT_TYPE\": \"无照违法经营\",\n"+
            "\t\t\t\t\t\"EVENT_TYPEID\": \"10130154\",\n"+
            "\t\t\t\t\t\"EVENT_LV\": \"01\",\n"+
            "\t\t\t\t\t\"SOURCE_DEPTID\": null,\n"+
            "\t\t\t\t\t\"SOURCE_DEPT\": null,\n"+
            "\t\t\t\t\t\"SOURCE_ID\": null,\n"+
            "\t\t\t\t\t\"SOURCE_BLSX\": null,\n"+
            "\t\t\t\t\t\"TIME_LIMIT\": \"72\",\n"+
            "\t\t\t\t\t\"TIME_SL\": \"60\",\n"+
            "\t\t\t\t\t\"EVENT_KEYWORD\": null,\n"+
            "\t\t\t\t\t\"REMARK\": null,\n"+
            "\t\t\t\t\t\"SHENG\": \"广东省\",\n"+
            "\t\t\t\t\t\"SHENGDM\": \"44\",\n"+
            "\t\t\t\t\t\"SHI\": \"深圳市\",\n"+
            "\t\t\t\t\t\"SHIDM\": \"4403\",\n"+
            "\t\t\t\t\t\"QU\": \"福田区\",\n"+
            "\t\t\t\t\t\"QUDM\": \"440304\",\n"+
            "\t\t\t\t\t\"JD\": \"福保街道\",\n"+
            "\t\t\t\t\t\"JDDM\": \"440304010\",\n"+
            "\t\t\t\t\t\"SQ\": \"新港社区\",\n"+
            "\t\t\t\t\t\"SQDM\": \"440304010001\",\n"+
            "\t\t\t\t\t\"WG\": \"新港07\",\n"+
            "\t\t\t\t\t\"WGDM\": \"440304010001007\",\n"+
            "\t\t\t\t\t\"DL\": \"益田五路\",\n"+
            "\t\t\t\t\t\"DLDM\": null,\n"+
            "\t\t\t\t\t\"XQ\": \"(福田花园治安岗亭)\",\n"+
            "\t\t\t\t\t\"XQDM\": null,\n"+
            "\t\t\t\t\t\"LD\": null,\n"+
            "\t\t\t\t\t\"LDDM\": \"4403040040110700005\",\n"+
            "\t\t\t\t\t\"FW\": \"益田五路49号福田花园1栋606\",\n"+
            "\t\t\t\t\t\"FWDM\": \"4403040040110700005000036\",\n"+
            "\t\t\t\t\t\"BZDZ\": null,\n"+
            "\t\t\t\t\t\"FLOWKEY\": null,\n"+
            "\t\t\t\t\t\"FBTIME\": \"2018-05-18 18:01:30\",\n"+
            "\t\t\t\t\t\"BJTIME\": null,\n"+
            "\t\t\t\t\t\"GQTIME\": null,\n"+
            "\t\t\t\t\t\"GDTIME\": null,\n"+
            "\t\t\t\t\t\"PJTIME\": null,\n"+
            "\t\t\t\t\t\"DBTIME\": null,\n"+
            "\t\t\t\t\t\"PSTIME\": null,\n"+
            "\t\t\t\t\t\"GD_EVENT_TYPE\": null,\n"+
            "\t\t\t\t\t\"GD_EVENT_TYPEID\": null,\n"+
            "\t\t\t\t\t\"GQ_TYPE\": null,\n"+
            "\t\t\t\t\t\"GQ_REMARK\": null,\n"+
            "\t\t\t\t\t\"LRR\": null,\n"+
            "\t\t\t\t\t\"LRRQ\": null,\n"+
            "\t\t\t\t\t\"GXR\": null,\n"+
            "\t\t\t\t\t\"GXSJ\": null,\n"+
            "\t\t\t\t\t\"LON\": \"113621.390759928\",\n"+
            "\t\t\t\t\t\"LAT\": \"16365.8700269759\",\n"+
            "\t\t\t\t\t\"TASK_ID\": null,\n"+
            "\t\t\t\t\t\"USER_ID\": null,\n"+
            "\t\t\t\t\t\"STATUS\": null,\n"+
            "\t\t\t\t\t\"CTIME\": \"2018-05-18 18:01:27\"\n"+
            "\t\t\t\t\n"+
            "\t\t\t},\n"+
            "\t\t\t{\n"+
            "\t\t\n"+
            "\t\t\t\t\t\"OID\": \"8457\",\n"+
            "\t\t\t\t\t\"GEOMETRY\": null,\n"+
            "\t\t\t\t\t\"SYSTEMID\": \"a4828fd6af54443f88330bdb1a7a90e9\",\n"+
            "\t\t\t\t\t\"EVENT_CODE\": \"440304010201805230100\",\n"+
            "\t\t\t\t\t\"EVENT_NAME\": \"李卫芹2018-05-23 19:08:59上报事件\",\n"+
            "\t\t\t\t\t\"EVENT_CONTENT\": \"石厦北三街菜市场乱堆放东西每天都好臭存在市容市貌隐患\",\n"+
            "\t\t\t\t\t\"EVENT_TIME\": \"2018-05-23 19:08:59\",\n"+
            "\t\t\t\t\t\"EVENT_SOURCE\": \"90\",\n"+
            "\t\t\t\t\t\"ISDELETE\": \"0\",\n"+
            "\t\t\t\t\t\"ADDRESS\": \"广东省深圳市福田区石厦北三街7-7\",\n"+
            "\t\t\t\t\t\"STATE\": \"1\",\n"+
            "\t\t\t\t\t\"CREATER_ID\": \"liweiqin\",\n"+
            "\t\t\t\t\t\"CREATER_DEPTID\": \"440304010004\",\n"+
            "\t\t\t\t\t\"CREATER_DEPT\": \"明月社区\",\n"+
            "\t\t\t\t\t\"REPORT_NAME\": null,\n"+
            "\t\t\t\t\t\"REPORT_CODE_TYPE\": null,\n"+
            "\t\t\t\t\t\"REPORT_CODE\": null,\n"+
            "\t\t\t\t\t\"REPORT_ADDRESS\": null,\n"+
            "\t\t\t\t\t\"REPORT_PHONE\": null,\n"+
            "\t\t\t\t\t\"FLOWSTATE\": \"执法员办理\",\n"+
            "\t\t\t\t\t\"FLOWSTATEID\": \"12\",\n"+
            "\t\t\t\t\t\"TEMPLATEID\": null,\n"+
            "\t\t\t\t\t\"EVENT_TYPE\": \"乱堆放、乱摆卖\",\n"+
            "\t\t\t\t\t\"EVENT_TYPEID\": \"10020033\",\n"+
            "\t\t\t\t\t\"EVENT_LV\": \"01\",\n"+
            "\t\t\t\t\t\"SOURCE_DEPTID\": null,\n"+
            "\t\t\t\t\t\"SOURCE_DEPT\": null,\n"+
            "\t\t\t\t\t\"SOURCE_ID\": null,\n"+
            "\t\t\t\t\t\"SOURCE_BLSX\": null,\n"+
            "\t\t\t\t\t\"TIME_LIMIT\": \"72\",\n"+
            "\t\t\t\t\t\"TIME_SL\": \"60\",\n"+
            "\t\t\t\t\t\"EVENT_KEYWORD\": null,\n"+
            "\t\t\t\t\t\"REMARK\": null,\n"+
            "\t\t\t\t\t\"SHENG\": \"广东省\",\n"+
            "\t\t\t\t\t\"SHENGDM\": \"44\",\n"+
            "\t\t\t\t\t\"SHI\": \"深圳市\",\n"+
            "\t\t\t\t\t\"SHIDM\": \"4403\",\n"+
            "\t\t\t\t\t\"QU\": \"福田区\",\n"+
            "\t\t\t\t\t\"QUDM\": \"440304\",\n"+
            "\t\t\t\t\t\"JD\": \"福保街道\",\n"+
            "\t\t\t\t\t\"JDDM\": \"440304010\",\n"+
            "\t\t\t\t\t\"SQ\": \"明月社区\",\n"+
            "\t\t\t\t\t\"SQDM\": \"440304010004\",\n"+
            "\t\t\t\t\t\"WG\": \"明月09\",\n"+
            "\t\t\t\t\t\"WGDM\": \"440304010004009\",\n"+
            "\t\t\t\t\t\"DL\": \"石厦北三街\",\n"+
            "\t\t\t\t\t\"DLDM\": \"2307\",\n"+
            "\t\t\t\t\t\"XQ\": \"华嵘净菜市场\",\n"+
            "\t\t\t\t\t\"XQDM\": null,\n"+
            "\t\t\t\t\t\"LD\": null,\n"+
            "\t\t\t\t\t\"LDDM\": \"4403040040050700012\",\n"+
            "\t\t\t\t\t\"FW\": \"石厦北三街7号华荣净菜市场2层\",\n"+
            "\t\t\t\t\t\"FWDM\": \"4403040040050700012000027\",\n"+
            "\t\t\t\t\t\"BZDZ\": null,\n"+
            "\t\t\t\t\t\"FLOWKEY\": null,\n"+
            "\t\t\t\t\t\"FBTIME\": \"2018-05-23 19:11:04\",\n"+
            "\t\t\t\t\t\"BJTIME\": null,\n"+
            "\t\t\t\t\t\"GQTIME\": null,\n"+
            "\t\t\t\t\t\"GDTIME\": null,\n"+
            "\t\t\t\t\t\"PJTIME\": null,\n"+
            "\t\t\t\t\t\"DBTIME\": null,\n"+
            "\t\t\t\t\t\"PSTIME\": null,\n"+
            "\t\t\t\t\t\"GD_EVENT_TYPE\": null,\n"+
            "\t\t\t\t\t\"GD_EVENT_TYPEID\": null,\n"+
            "\t\t\t\t\t\"GQ_TYPE\": null,\n"+
            "\t\t\t\t\t\"GQ_REMARK\": null,\n"+
            "\t\t\t\t\t\"LRR\": null,\n"+
            "\t\t\t\t\t\"LRRQ\": null,\n"+
            "\t\t\t\t\t\"GXR\": null,\n"+
            "\t\t\t\t\t\"GXSJ\": null,\n"+
            "\t\t\t\t\t\"LON\": \"114220.968207227\",\n"+
            "\t\t\t\t\t\"LAT\": \"17625.4927639477\",\n"+
            "\t\t\t\t\t\"TASK_ID\": null,\n"+
            "\t\t\t\t\t\"USER_ID\": null,\n"+
            "\t\t\t\t\t\"STATUS\": null,\n"+
            "\t\t\t\t\t\"CTIME\": \"2018-05-23 19:10:50\"\n"+
            "\t\t\t\t\n"+
            "\t\t\t},\n"+
            "\t\t\t{\n"+
            "\n"+
            "\t\t\t\t\t\"OID\": \"8458\",\n"+
            "\t\t\t\t\t\"GEOMETRY\": null,\n"+
            "\t\t\t\t\t\"SYSTEMID\": \"0a976080e9284ba4a6834bfa04227f37\",\n"+
            "\t\t\t\t\t\"EVENT_CODE\": \"440304010201805230105\",\n"+
            "\t\t\t\t\t\"EVENT_NAME\": \"梁玲2018-05-23 19:55:59上报事件\",\n"+
            "\t\t\t\t\t\"EVENT_CONTENT\": \"石厦东村296一702，集体宿舍，多人居住，电线乱拉，存在安全隐患\",\n"+
            "\t\t\t\t\t\"EVENT_TIME\": \"2018-05-23 19:55:59\",\n"+
            "\t\t\t\t\t\"EVENT_SOURCE\": \"90\",\n"+
            "\t\t\t\t\t\"ISDELETE\": \"0\",\n"+
            "\t\t\t\t\t\"ADDRESS\": \"广东省深圳市福田区福民路185-10\",\n"+
            "\t\t\t\t\t\"STATE\": \"3\",\n"+
            "\t\t\t\t\t\"CREATER_ID\": \"liangling\",\n"+
            "\t\t\t\t\t\"CREATER_DEPTID\": \"440304010005\",\n"+
            "\t\t\t\t\t\"CREATER_DEPT\": \"石厦社区\",\n"+
            "\t\t\t\t\t\"REPORT_NAME\": null,\n"+
            "\t\t\t\t\t\"REPORT_CODE_TYPE\": null,\n"+
            "\t\t\t\t\t\"REPORT_CODE\": null,\n"+
            "\t\t\t\t\t\"REPORT_ADDRESS\": null,\n"+
            "\t\t\t\t\t\"REPORT_PHONE\": null,\n"+
            "\t\t\t\t\t\"FLOWSTATE\": null,\n"+
            "\t\t\t\t\t\"FLOWSTATEID\": \"13\",\n"+
            "\t\t\t\t\t\"TEMPLATEID\": null,\n"+
            "\t\t\t\t\t\"EVENT_TYPE\": \"危险用电\",\n"+
            "\t\t\t\t\t\"EVENT_TYPEID\": \"10010032\",\n"+
            "\t\t\t\t\t\"EVENT_LV\": \"01\",\n"+
            "\t\t\t\t\t\"SOURCE_DEPTID\": null,\n"+
            "\t\t\t\t\t\"SOURCE_DEPT\": null,\n"+
            "\t\t\t\t\t\"SOURCE_ID\": null,\n"+
            "\t\t\t\t\t\"SOURCE_BLSX\": null,\n"+
            "\t\t\t\t\t\"TIME_LIMIT\": \"72\",\n"+
            "\t\t\t\t\t\"TIME_SL\": \"60\",\n"+
            "\t\t\t\t\t\"EVENT_KEYWORD\": null,\n"+
            "\t\t\t\t\t\"REMARK\": null,\n"+
            "\t\t\t\t\t\"SHENG\": \"广东省\",\n"+
            "\t\t\t\t\t\"SHENGDM\": \"44\",\n"+
            "\t\t\t\t\t\"SHI\": \"深圳市\",\n"+
            "\t\t\t\t\t\"SHIDM\": \"03\",\n"+
            "\t\t\t\t\t\"QU\": \"福田区\",\n"+
            "\t\t\t\t\t\"QUDM\": \"440304\",\n"+
            "\t\t\t\t\t\"JD\": \"福保街道\",\n"+
            "\t\t\t\t\t\"JDDM\": \"440304010\",\n"+
            "\t\t\t\t\t\"SQ\": \"石厦社区\",\n"+
            "\t\t\t\t\t\"SQDM\": \"440304010005\",\n"+
            "\t\t\t\t\t\"WG\": \"石厦01\",\n"+
            "\t\t\t\t\t\"WGDM\": \"440304010005001\",\n"+
            "\t\t\t\t\t\"DL\": \"福民路\",\n"+
            "\t\t\t\t\t\"DLDM\": \"3970\",\n"+
            "\t\t\t\t\t\"XQ\": \"众孚花园\",\n"+
            "\t\t\t\t\t\"XQDM\": \"5\",\n"+
            "\t\t\t\t\t\"LD\": null,\n"+
            "\t\t\t\t\t\"LDDM\": \"4403040040090100075\",\n"+
            "\t\t\t\t\t\"FW\": \"福民路185号众孚花园4栋704\",\n"+
            "\t\t\t\t\t\"FWDM\": \"4403040040090100075000028\",\n"+
            "\t\t\t\t\t\"BZDZ\": null,\n"+
            "\t\t\t\t\t\"FLOWKEY\": null,\n"+
            "\t\t\t\t\t\"FBTIME\": \"2018-05-23 19:57:33\",\n"+
            "\t\t\t\t\t\"BJTIME\": \"2018-05-24 15:28:02\",\n"+
            "\t\t\t\t\t\"GQTIME\": \"2018-09-05 00:00:00\",\n"+
            "\t\t\t\t\t\"GDTIME\": \"2018-09-05 00:00:00\",\n"+
            "\t\t\t\t\t\"PJTIME\": \"2018-09-05 15:07:55\",\n"+
            "\t\t\t\t\t\"DBTIME\": \"2018-09-05 00:00:00\",\n"+
            "\t\t\t\t\t\"PSTIME\": \"2018-09-05 00:00:00\",\n"+
            "\t\t\t\t\t\"GD_EVENT_TYPE\": \"危险用电\",\n"+
            "\t\t\t\t\t\"GD_EVENT_TYPEID\": \"10010032\",\n"+
            "\t\t\t\t\t\"GQ_TYPE\": null,\n"+
            "\t\t\t\t\t\"GQ_REMARK\": null,\n"+
            "\t\t\t\t\t\"LRR\": null,\n"+
            "\t\t\t\t\t\"LRRQ\": \"2018-09-05 00:00:00\",\n"+
            "\t\t\t\t\t\"GXR\": null,\n"+
            "\t\t\t\t\t\"GXSJ\": \"2018-09-05 00:00:00\",\n"+
            "\t\t\t\t\t\"LON\": \"113976.0958407186\",\n"+
            "\t\t\t\t\t\"LAT\": \"17240.82653985452\",\n"+
            "\t\t\t\t\t\"TASK_ID\": null,\n"+
            "\t\t\t\t\t\"USER_ID\": null,\n"+
            "\t\t\t\t\t\"STATUS\": null,\n"+
            "\t\t\t\t\t\"CTIME\": \"2018-05-23 19:57:26\"\n"+
            "\t\t\t\t\n"+
            "\t\t\t},\n"+
            "\t\t\t{\n"+
            "\n"+
            "\t\t\t\t\t\"OID\": \"8459\",\n"+
            "\t\t\t\t\t\"GEOMETRY\": null,\n"+
            "\t\t\t\t\t\"SYSTEMID\": \"dbb55d15212543a39e668caf7f77aba2\",\n"+
            "\t\t\t\t\t\"EVENT_CODE\": \"440304010201805230108\",\n"+
            "\t\t\t\t\t\"EVENT_NAME\": \"冯琼华2018-05-23 20:00:53上报事件\",\n"+
            "\t\t\t\t\t\"EVENT_CONTENT\": \"福田花园大厦A座14楼安全出口堵塞\",\n"+
            "\t\t\t\t\t\"EVENT_TIME\": \"2018-05-23 20:00:53\",\n"+
            "\t\t\t\t\t\"EVENT_SOURCE\": \"90\",\n"+
            "\t\t\t\t\t\"ISDELETE\": \"0\",\n"+
            "\t\t\t\t\t\"ADDRESS\": \"广东省深圳市福田区福强路3031号\",\n"+
            "\t\t\t\t\t\"STATE\": \"3\",\n"+
            "\t\t\t\t\t\"CREATER_ID\": \"fengqionghua\",\n"+
            "\t\t\t\t\t\"CREATER_DEPTID\": \"440304010001\",\n"+
            "\t\t\t\t\t\"CREATER_DEPT\": \"新港社区\",\n"+
            "\t\t\t\t\t\"REPORT_NAME\": null,\n"+
            "\t\t\t\t\t\"REPORT_CODE_TYPE\": null,\n"+
            "\t\t\t\t\t\"REPORT_CODE\": null,\n"+
            "\t\t\t\t\t\"REPORT_ADDRESS\": null,\n"+
            "\t\t\t\t\t\"REPORT_PHONE\": null,\n"+
            "\t\t\t\t\t\"FLOWSTATE\": null,\n"+
            "\t\t\t\t\t\"FLOWSTATEID\": \"13\",\n"+
            "\t\t\t\t\t\"TEMPLATEID\": null,\n"+
            "\t\t\t\t\t\"EVENT_TYPE\": \"安全出口、逃生通道不畅\",\n"+
            "\t\t\t\t\t\"EVENT_TYPEID\": \"10160005\",\n"+
            "\t\t\t\t\t\"EVENT_LV\": \"01\",\n"+
            "\t\t\t\t\t\"SOURCE_DEPTID\": null,\n"+
            "\t\t\t\t\t\"SOURCE_DEPT\": null,\n"+
            "\t\t\t\t\t\"SOURCE_ID\": null,\n"+
            "\t\t\t\t\t\"SOURCE_BLSX\": null,\n"+
            "\t\t\t\t\t\"TIME_LIMIT\": \"72\",\n"+
            "\t\t\t\t\t\"TIME_SL\": \"60\",\n"+
            "\t\t\t\t\t\"EVENT_KEYWORD\": null,\n"+
            "\t\t\t\t\t\"REMARK\": null,\n"+
            "\t\t\t\t\t\"SHENG\": \"广东省\",\n"+
            "\t\t\t\t\t\"SHENGDM\": \"44\",\n"+
            "\t\t\t\t\t\"SHI\": \"深圳市\",\n"+
            "\t\t\t\t\t\"SHIDM\": \"03\",\n"+
            "\t\t\t\t\t\"QU\": \"福田区\",\n"+
            "\t\t\t\t\t\"QUDM\": \"440304\",\n"+
            "\t\t\t\t\t\"JD\": \"福保街道\",\n"+
            "\t\t\t\t\t\"JDDM\": \"440304010\",\n"+
            "\t\t\t\t\t\"SQ\": \"新港社区\",\n"+
            "\t\t\t\t\t\"SQDM\": \"440304010001\",\n"+
            "\t\t\t\t\t\"WG\": \"新港07\",\n"+
            "\t\t\t\t\t\"WGDM\": \"440304010001007\",\n"+
            "\t\t\t\t\t\"DL\": \"福强路\",\n"+
            "\t\t\t\t\t\"DLDM\": \"2723\",\n"+
            "\t\t\t\t\t\"XQ\": \"中国电信益田营业厅\",\n"+
            "\t\t\t\t\t\"XQDM\": null,\n"+
            "\t\t\t\t\t\"LD\": null,\n"+
            "\t\t\t\t\t\"LDDM\": \"4403040040110700001\",\n"+
            "\t\t\t\t\t\"FW\": \"福强路3031号中国电信益田营业厅101\",\n"+
            "\t\t\t\t\t\"FWDM\": \"4403040040110700001000001\",\n"+
            "\t\t\t\t\t\"BZDZ\": null,\n"+
            "\t\t\t\t\t\"FLOWKEY\": null,\n"+
            "\t\t\t\t\t\"FBTIME\": \"2018-05-23 20:02:43\",\n"+
            "\t\t\t\t\t\"BJTIME\": \"2018-05-24 16:34:08\",\n"+
            "\t\t\t\t\t\"GQTIME\": \"2018-09-05 00:00:00\",\n"+
            "\t\t\t\t\t\"GDTIME\": \"2018-09-05 00:00:00\",\n"+
            "\t\t\t\t\t\"PJTIME\": \"2018-09-05 15:10:38\",\n"+
            "\t\t\t\t\t\"DBTIME\": \"2018-09-05 00:00:00\",\n"+
            "\t\t\t\t\t\"PSTIME\": \"2018-09-05 00:00:00\",\n"+
            "\t\t\t\t\t\"GD_EVENT_TYPE\": \"安全出口、逃生通道不畅\",\n"+
            "\t\t\t\t\t\"GD_EVENT_TYPEID\": \"10160005\",\n"+
            "\t\t\t\t\t\"GQ_TYPE\": null,\n"+
            "\t\t\t\t\t\"GQ_REMARK\": null,\n"+
            "\t\t\t\t\t\"LRR\": null,\n"+
            "\t\t\t\t\t\"LRRQ\": \"2018-09-05 00:00:00\",\n"+
            "\t\t\t\t\t\"GXR\": null,\n"+
            "\t\t\t\t\t\"GXSJ\": \"2018-09-05 00:00:00\",\n"+
            "\t\t\t\t\t\"LON\": \"-11631349.879805898\",\n"+
            "\t\t\t\t\t\"LAT\": \"-2214613.8255822756\",\n"+
            "\t\t\t\t\t\"TASK_ID\": null,\n"+
            "\t\t\t\t\t\"USER_ID\": null,\n"+
            "\t\t\t\t\t\"STATUS\": null,\n"+
            "\t\t\t\t\t\"CTIME\": \"2018-05-23 20:02:32\"\n"+
            "\t\t\t\t\n"+
            "\t\t\t},\n"+
            "\t\t\t{\n"+
            "\n"+
            "\t\t\t\t\t\"OID\": \"8460\",\n"+
            "\t\t\t\t\t\"GEOMETRY\": null,\n"+
            "\t\t\t\t\t\"SYSTEMID\": \"fdb43d39a6244457915909e0aae719db\",\n"+
            "\t\t\t\t\t\"EVENT_CODE\": \"440304010201805230120\",\n"+
            "\t\t\t\t\t\"EVENT_NAME\": \"刘桂枝2018-05-23 20:31:55上报事件\",\n"+
            "\t\t\t\t\t\"EVENT_CONTENT\": \"石厦西村25栋污水管破烂，污水乱流。\",\n"+
            "\t\t\t\t\t\"EVENT_TIME\": \"2018-05-23 20:31:55\",\n"+
            "\t\t\t\t\t\"EVENT_SOURCE\": \"90\",\n"+
            "\t\t\t\t\t\"ISDELETE\": \"0\",\n"+
            "\t\t\t\t\t\"ADDRESS\": \"广东省深圳市福田区石厦四街246号\",\n"+
            "\t\t\t\t\t\"STATE\": \"3\",\n"+
            "\t\t\t\t\t\"CREATER_ID\": \"liuguizhi\",\n"+
            "\t\t\t\t\t\"CREATER_DEPTID\": \"440304010005\",\n"+
            "\t\t\t\t\t\"CREATER_DEPT\": \"石厦社区\",\n"+
            "\t\t\t\t\t\"REPORT_NAME\": null,\n"+
            "\t\t\t\t\t\"REPORT_CODE_TYPE\": null,\n"+
            "\t\t\t\t\t\"REPORT_CODE\": null,\n"+
            "\t\t\t\t\t\"REPORT_ADDRESS\": null,\n"+
            "\t\t\t\t\t\"REPORT_PHONE\": null,\n"+
            "\t\t\t\t\t\"FLOWSTATE\": \"执法员办理\",\n"+
            "\t\t\t\t\t\"FLOWSTATEID\": \"13\",\n"+
            "\t\t\t\t\t\"TEMPLATEID\": null,\n"+
            "\t\t\t\t\t\"EVENT_TYPE\": \"下水道堵塞或破损\",\n"+
            "\t\t\t\t\t\"EVENT_TYPEID\": \"10060089\",\n"+
            "\t\t\t\t\t\"EVENT_LV\": \"01\",\n"+
            "\t\t\t\t\t\"SOURCE_DEPTID\": null,\n"+
            "\t\t\t\t\t\"SOURCE_DEPT\": null,\n"+
            "\t\t\t\t\t\"SOURCE_ID\": null,\n"+
            "\t\t\t\t\t\"SOURCE_BLSX\": null,\n"+
            "\t\t\t\t\t\"TIME_LIMIT\": \"72\",\n"+
            "\t\t\t\t\t\"TIME_SL\": \"60\",\n"+
            "\t\t\t\t\t\"EVENT_KEYWORD\": null,\n"+
            "\t\t\t\t\t\"REMARK\": null,\n"+
            "\t\t\t\t\t\"SHENG\": \"广东省\",\n"+
            "\t\t\t\t\t\"SHENGDM\": \"44\",\n"+
            "\t\t\t\t\t\"SHI\": \"深圳市\",\n"+
            "\t\t\t\t\t\"SHIDM\": \"03\",\n"+
            "\t\t\t\t\t\"QU\": \"福田区\",\n"+
            "\t\t\t\t\t\"QUDM\": \"440304\",\n"+
            "\t\t\t\t\t\"JD\": \"福保街道\",\n"+
            "\t\t\t\t\t\"JDDM\": \"440304010\",\n"+
            "\t\t\t\t\t\"SQ\": \"新港社区\",\n"+
            "\t\t\t\t\t\"SQDM\": \"440304010001\",\n"+
            "\t\t\t\t\t\"WG\": \"新港03\",\n"+
            "\t\t\t\t\t\"WGDM\": \"440304010001003\",\n"+
            "\t\t\t\t\t\"DL\": \"石厦四街\",\n"+
            "\t\t\t\t\t\"DLDM\": null,\n"+
            "\t\t\t\t\t\"XQ\": \"民政局婚姻登记处\",\n"+
            "\t\t\t\t\t\"XQDM\": null,\n"+
            "\t\t\t\t\t\"LD\": null,\n"+
            "\t\t\t\t\t\"LDDM\": \"5dacd2a95fc9456796f0376bae404c44\",\n"+
            "\t\t\t\t\t\"FW\": \"石厦四街233号（区计生服务中心岗亭)101\",\n"+
            "\t\t\t\t\t\"FWDM\": \"44030400401103T0002000001\",\n"+
            "\t\t\t\t\t\"BZDZ\": null,\n"+
            "\t\t\t\t\t\"FLOWKEY\": null,\n"+
            "\t\t\t\t\t\"FBTIME\": \"2018-05-23 20:33:53\",\n"+
            "\t\t\t\t\t\"BJTIME\": \"2018-06-20 12:58:29\",\n"+
            "\t\t\t\t\t\"GQTIME\": \"2018-06-20 00:00:00\",\n"+
            "\t\t\t\t\t\"GDTIME\": \"2018-06-20 00:00:00\",\n"+
            "\t\t\t\t\t\"PJTIME\": \"2018-09-05 15:11:51\",\n"+
            "\t\t\t\t\t\"DBTIME\": \"2018-06-20 00:00:00\",\n"+
            "\t\t\t\t\t\"PSTIME\": \"2018-06-20 00:00:00\",\n"+
            "\t\t\t\t\t\"GD_EVENT_TYPE\": \"下水道堵塞或破损\",\n"+
            "\t\t\t\t\t\"GD_EVENT_TYPEID\": \"10060089\",\n"+
            "\t\t\t\t\t\"GQ_TYPE\": null,\n"+
            "\t\t\t\t\t\"GQ_REMARK\": null,\n"+
            "\t\t\t\t\t\"LRR\": null,\n"+
            "\t\t\t\t\t\"LRRQ\": \"2018-06-20 00:00:00\",\n"+
            "\t\t\t\t\t\"GXR\": null,\n"+
            "\t\t\t\t\t\"GXSJ\": \"2018-06-20 00:00:00\",\n"+
            "\t\t\t\t\t\"LON\": \"113778.73597326875\",\n"+
            "\t\t\t\t\t\"LAT\": \"17100.39674403332\",\n"+
            "\t\t\t\t\t\"TASK_ID\": null,\n"+
            "\t\t\t\t\t\"USER_ID\": null,\n"+
            "\t\t\t\t\t\"STATUS\": null,\n"+
            "\t\t\t\t\t\"CTIME\": \"2018-05-23 20:33:47\"\n"+
            "\t\t\t\t\n"+
            "\t\t\t}\n"+
            "\t\t]\n"+
            "}";
    
    private String data3 = "{\n" +
            "    \"total\": 152,\n" +
            "    \"statusCode\": \"200\",\n" +
            "    \"result\": [\n" +
            "        {\n" +
            "            \"bajdld\": \"杨九明\",\n" +
            "            \"bjsj\": null,\n" +
            "            \"bxxs\": \"纠纷\",\n" +
            "            \"bxxsfy\": null,\n" +
            "            \"clsjfy\": null,\n" +
            "            \"clsx\": null,\n" +
            "            \"createBy\": null,\n" +
            "            \"createTime\": null,\n" +
            "            \"dcbm\": null,\n" +
            "            \"fsdd\": \"福田区南华村南华小学\",\n" +
            "            \"fssj\": \"2018-10-22T10:28:00\",\n" +
            "            \"gjc\": null,\n" +
            "            \"gm\": \"3\",\n" +
            "            \"id\": \"5bcfd8e8b0edf3357b12271b\",\n" +
            "            \"isal\": null,\n" +
            "            \"iscollect\": null,\n" +
            "            \"ismg\": null,\n" +
            "            \"jb\": \"1\",\n" +
            "            \"jdld\": null,\n" +
            "            \"jwd\": null,\n" +
            "            \"lastUpdateBy\": null,\n" +
            "            \"lastUpdateTime\": null,\n" +
            "            \"ldps\": \"建议区委政法委协调教育部门尽快处置。\",\n" +
            "            \"lrbm\": \"综治办\",\n" +
            "            \"lrr\": \"shenchixiu\",\n" +
            "            \"lrsj\": \"2018-10-24T10:28:56\",\n" +
            "            \"sfgacz\": \"1\",\n" +
            "            \"sfsj\": \"0\",\n" +
            "            \"sfsqqt\": \"保安员\",\n" +
            "            \"sfsw\": \"0\",\n" +
            "            \"sfsyq\": \"0\",\n" +
            "            \"sjgyqk\": \"2018年初，福田区南华小学因更换劳务派遣安保公司事宜，与保安员周勇、陈汉清、刘江南3人（原在岗服务10余年）产生争议，且一直未得以妥善处理，随后引发纠纷。\\r\\n2018年4月份，我街道在接到南华小学与3名保安员分别反馈的纠纷情况后，街道社区法治办立即派出工作人员约谈3名保安员，在4、5月份期间先后多次到南华小学与校方代表及新入场的安保公司商讨纠纷事态、提供法律意见，并组织纠纷双方进行协商和调解。但由于双方争议焦点不一，且南华小学程芳校长未予以积极配合，导致调解无法继续。在整个过程中，街道社区法治办工作人员一直告诫3名保安员一定要重视校园安全、不能采取任何过激行为，同时积极引导他们通过法定途径主张权益。\",\n" +
            "            \"sjly\": \"劳资,其他,拖欠工资\",\n" +
            "            \"sjmc\": \"南华小学保安员与校方之间因劳务纠纷而引发的不稳定因素\",\n" +
            "            \"ssjd\": \"南园街道\",\n" +
            "            \"ssjwh\": null,\n" +
            "            \"sssq\": \"南华社区工作站\",\n" +
            "            \"xcts\": \"1\",\n" +
            "            \"znbm\": null,\n" +
            "            \"zt\": \"街道上报\",\n" +
            "            \"zysq\": \"尽快恢复社保卡、医保卡的使用功能，并将依法应补发的社保、医保费用以及工资全部补齐，同时尽快恢复正常岗位工作。\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"bajdld\": null,\n" +
            "            \"bjsj\": null,\n" +
            "            \"bxxs\": null,\n" +
            "            \"bxxsfy\": null,\n" +
            "            \"clsjfy\": null,\n" +
            "            \"clsx\": null,\n" +
            "            \"createBy\": null,\n" +
            "            \"createTime\": null,\n" +
            "            \"dcbm\": null,\n" +
            "            \"fsdd\": \"广东省深圳市福田区福田ＣＢＤ益田路6003号荣超中心B座\",\n" +
            "            \"fssj\": \"2018-08-13T11:37:00\",\n" +
            "            \"gjc\": null,\n" +
            "            \"gm\": \"20\",\n" +
            "            \"id\": \"5b713acbb0edf35a59c2432c\",\n" +
            "            \"isal\": null,\n" +
            "            \"iscollect\": null,\n" +
            "            \"ismg\": null,\n" +
            "            \"jb\": \"1\",\n" +
            "            \"jdld\": null,\n" +
            "            \"jwd\": \"114.062187,22.549797\",\n" +
            "            \"lastUpdateBy\": null,\n" +
            "            \"lastUpdateTime\": null,\n" +
            "            \"ldps\": null,\n" +
            "            \"lrbm\": \"福田街道\",\n" +
            "            \"lrr\": \"xiangsong\",\n" +
            "            \"lrsj\": \"2018-08-13T16:01:15\",\n" +
            "            \"sfgacz\": \"0\",\n" +
            "            \"sfsj\": \"0\",\n" +
            "            \"sfsqqt\": null,\n" +
            "            \"sfsw\": \"0\",\n" +
            "            \"sfsyq\": \"0\",\n" +
            "            \"sjgyqk\": \"深圳市财富之家金融网络科技服务有限公司，注册地址：深圳市福田区福田街道金田路2028号皇岗商务中心主楼6楼。8月13日下午，财富中国投资者在市金融办反映诉求，经市金融办领导引导，投资者已经离开。\",\n" +
            "            \"sjly\": \"P2P网贷平台\",\n" +
            "            \"sjmc\": \"财富中国投资者在市金融办反映诉求\",\n" +
            "            \"ssjd\": \"福田街道\",\n" +
            "            \"ssjwh\": null,\n" +
            "            \"sssq\": null,\n" +
            "            \"xcts\": null,\n" +
            "            \"znbm\": null,\n" +
            "            \"zt\": \"区维稳办接收\",\n" +
            "            \"zysq\": null\n" +
            "        }"+
            "    ],\n" +
            "    \"currentPage\": 1,\n" +
            "    \"msg\": \"OK\"\n" +
            "}";
    @Test
    public void testExtract(){
        List<HashMap> hashMaps = JsonExtractor.ExtractListData(data3, "result.*");
        System.out.println(hashMaps);

    }
}
