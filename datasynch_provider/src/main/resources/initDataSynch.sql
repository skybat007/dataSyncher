/*
Navicat Oracle Data Transfer
Oracle Client Version : 10.2.0.5.0

Source Server         : ZHFT123
Source Server Version : 110200
Source Host           : localhost:1521
Source Schema         : ZHFT123

Target Server Type    : ORACLE
Target Server Version : 110200
File Encoding         : 65001

Date: 2018-10-11 17:07:48
*/
--创建序列
DROP sequence  SEQ_DS_SCHEDULE_JOB_INFO;
create sequence SEQ_DS_SCHEDULE_JOB_INFO
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
cache 50;

DROP sequence  SEQ_DS_SYNCH_JOB_LOG_INFO;
create sequence SEQ_DS_SYNCH_JOB_LOG_INFO
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
cache 50;

DROP sequence  SEQ_DS_COLUMN_MAPPING_INFO;
create sequence SEQ_DS_COLUMN_MAPPING_INFO
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
cache 50;


-- ----------------------------
-- Table structure for DS_SCHEDULE_JOB_INFO
-- ----------------------------
DROP TABLE "ZHFT123"."DS_SCHEDULE_JOB_INFO";
CREATE TABLE "ZHFT123"."DS_SCHEDULE_JOB_INFO" (
  "ID" NUMBER NULL ,
  "CONN_TYPE" NUMBER NULL ,
  "SOURCE" VARCHAR2(1024 BYTE) NULL ,
  "ORDER_BY_COL_NAME" VARCHAR2(255 BYTE) NULL ,
  "HTTP_PARAM_EXPRESSION" VARCHAR2(1024 BYTE) NULL ,
  "HTTP_TOKEN" VARCHAR2(1024 BYTE) NULL ,
  "HTTP_PARAM_PAGESIZE" VARCHAR2(255 BYTE) NULL ,
  "HTTP_PARAM_PAGENUM" VARCHAR2(255 BYTE) NULL ,
  "HTTP_JSON_EXTRACTRULE" VARCHAR2(255 BYTE) NULL ,
  "TARGET_TABLE_NAME" VARCHAR2(255 BYTE) NULL ,
  "PAGE_SIZE" NUMBER NULL ,
  "CRON_EXPRESSION" VARCHAR2(255 BYTE) NULL ,
  "IS_ENABLED" NUMBER DEFAULT 0 NULL ,
  "CREATE_TIME" DATE DEFAULT sysdate  NULL ,
  "UPDATE_TIME" DATE DEFAULT sysdate  NULL
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "ZHFT123"."DS_SCHEDULE_JOB_INFO"."ID" IS '自增主键';
COMMENT ON COLUMN "ZHFT123"."DS_SCHEDULE_JOB_INFO"."CONN_TYPE" IS '连接方式(0：数据库方式 1：接口方式)';
COMMENT ON COLUMN "ZHFT123"."DS_SCHEDULE_JOB_INFO"."SOURCE" IS '源(请求URL路径/前置机view视图的名称)';
COMMENT ON COLUMN "ZHFT123"."DS_SCHEDULE_JOB_INFO"."ORDER_BY_COL_NAME" IS '源-排序字段名称';
COMMENT ON COLUMN "ZHFT123"."DS_SCHEDULE_JOB_INFO"."HTTP_PARAM_EXPRESSION" IS '入参表达式+pageSize、pageNum映射规则';
COMMENT ON COLUMN "ZHFT123"."DS_SCHEDULE_JOB_INFO"."HTTP_TOKEN" IS '入参表达式+pageSize、pageNum映射规则';
COMMENT ON COLUMN "ZHFT123"."DS_SCHEDULE_JOB_INFO"."HTTP_PARAM_PAGESIZE" IS '入参表达式+pageSize、pageNum映射规则';
COMMENT ON COLUMN "ZHFT123"."DS_SCHEDULE_JOB_INFO"."HTTP_PARAM_PAGENUM" IS '入参表达式+pageSize、pageNum映射规则';
COMMENT ON COLUMN "ZHFT123"."DS_SCHEDULE_JOB_INFO"."HTTP_JSON_EXTRACTRULE" IS '入参表达式+pageSize、pageNum映射规则';
COMMENT ON COLUMN "ZHFT123"."DS_SCHEDULE_JOB_INFO"."TARGET_TABLE_NAME" IS '入库表名';
COMMENT ON COLUMN "ZHFT123"."DS_SCHEDULE_JOB_INFO"."PAGE_SIZE" IS '分页大小';
COMMENT ON COLUMN "ZHFT123"."DS_SCHEDULE_JOB_INFO"."CRON_EXPRESSION" IS '定时表达式';
COMMENT ON COLUMN "ZHFT123"."DS_SCHEDULE_JOB_INFO"."IS_ENABLED" IS '是否开启（0：关闭 1：开启）';
COMMENT ON COLUMN "ZHFT123"."DS_SCHEDULE_JOB_INFO"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "ZHFT123"."DS_SCHEDULE_JOB_INFO"."UPDATE_TIME" IS '更新时间';



-- ----------------------------
-- Table structure for DS_SYNCH_JOB_LOG_INFO
-- ----------------------------
DROP TABLE "ZHFT123"."DS_SYNCH_JOB_LOG_INFO";
CREATE TABLE "ZHFT123"."DS_SYNCH_JOB_LOG_INFO" (
  "ID" NUMBER NULL ,
  "JOBID" NUMBER NULL ,
  "IS_SUCCESS" NUMBER NULL ,
  "QUERY_RESULT_SIZE" NUMBER NULL ,
  "CURRENT_PAGE_NUM" NUMBER NULL ,
  "CURRENT_PAGE_SIZE" NUMBER NULL ,
  "START_ROW" NUMBER NULL ,
  "END_ROW" NUMBER NULL ,
  "CONN_TYPE" NUMBER NULL ,
  "SUCCESS_COUNT" NUMBER NULL ,
  "FAIL_COUNT" NUMBER NULL ,
  "TOTAL_SUCCESS_COUNT" NUMBER NULL ,
  "TOTAL_FAIL_COUNT" NUMBER NULL ,
  "CREATE_TIME" DATE DEFAULT sysdate  NULL ,
  "UPDATE_TIME" DATE DEFAULT sysdate  NULL
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "ZHFT123"."DS_SYNCH_JOB_LOG_INFO"."ID" IS '唯一主键';
COMMENT ON COLUMN "ZHFT123"."DS_SYNCH_JOB_LOG_INFO"."JOBID" IS '关联任务ID';
COMMENT ON COLUMN "ZHFT123"."DS_SYNCH_JOB_LOG_INFO"."IS_SUCCESS" IS '本次是否成功';
COMMENT ON COLUMN "ZHFT123"."DS_SYNCH_JOB_LOG_INFO"."QUERY_RESULT_SIZE" IS '本次请求包大小';
COMMENT ON COLUMN "ZHFT123"."DS_SYNCH_JOB_LOG_INFO"."CURRENT_PAGE_NUM" IS '当前请求页码';
COMMENT ON COLUMN "ZHFT123"."DS_SYNCH_JOB_LOG_INFO"."CURRENT_PAGE_SIZE" IS '当前页大小';
COMMENT ON COLUMN "ZHFT123"."DS_SYNCH_JOB_LOG_INFO"."START_ROW" IS '本次请求起始行';
COMMENT ON COLUMN "ZHFT123"."DS_SYNCH_JOB_LOG_INFO"."END_ROW" IS '本次请求结束行（包含）';
COMMENT ON COLUMN "ZHFT123"."DS_SYNCH_JOB_LOG_INFO"."CONN_TYPE" IS '更新方式（0：数据库 1：接口）';
COMMENT ON COLUMN "ZHFT123"."DS_SYNCH_JOB_LOG_INFO"."SUCCESS_COUNT" IS '本次成功插入条数';
COMMENT ON COLUMN "ZHFT123"."DS_SYNCH_JOB_LOG_INFO"."FAIL_COUNT" IS '本次失败插入条数';
COMMENT ON COLUMN "ZHFT123"."DS_SYNCH_JOB_LOG_INFO"."TOTAL_SUCCESS_COUNT" IS '总成功数';
COMMENT ON COLUMN "ZHFT123"."DS_SYNCH_JOB_LOG_INFO"."TOTAL_FAIL_COUNT" IS '总失败数';
COMMENT ON COLUMN "ZHFT123"."DS_SYNCH_JOB_LOG_INFO"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "ZHFT123"."DS_SYNCH_JOB_LOG_INFO"."UPDATE_TIME" IS '更新时间';
-- ----------------------------
-- Records of DS_SYNCH_JOB_LOG_INFO
-- ----------------------------





-- ----------------------------
-- Table structure for DS_COLUMN_MAPPING_INFO
-- ----------------------------
DROP TABLE "ZHFT123"."DS_COLUMN_MAPPING_INFO";
CREATE TABLE "ZHFT123"."DS_COLUMN_MAPPING_INFO" (
  "ID" NUMBER NULL ,
  "SOURCE" VARCHAR2(255 BYTE) NULL ,
  "SRC_COLUMN_NAME" VARCHAR2(255 BYTE) NULL ,
  "TARGET_COLUMN_NAME" VARCHAR2(255 BYTE) NULL ,
  "TARGET_TABLE" VARCHAR2(255 BYTE) NULL ,
  "CREATE_TIME" DATE DEFAULT sysdate  NULL ,
  "UPDATE_TIME" DATE DEFAULT sysdate  NULL
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "ZHFT123"."DS_COLUMN_MAPPING_INFO"."ID" IS '唯一主键';
COMMENT ON COLUMN "ZHFT123"."DS_COLUMN_MAPPING_INFO"."TARGET_TABLE" IS '目标表名称';
COMMENT ON COLUMN "ZHFT123"."DS_COLUMN_MAPPING_INFO"."SOURCE" IS '源表名称/源URL';
COMMENT ON COLUMN "ZHFT123"."DS_COLUMN_MAPPING_INFO"."SRC_COLUMN_NAME" IS '源字段名称';
COMMENT ON COLUMN "ZHFT123"."DS_COLUMN_MAPPING_INFO"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "ZHFT123"."DS_COLUMN_MAPPING_INFO"."UPDATE_TIME" IS '修改时间';

