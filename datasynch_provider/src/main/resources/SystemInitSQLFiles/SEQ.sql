--创建序列
--DROP sequence  SEQ_DS_SCHEDULE_JOB_INFO;
create sequence SEQ_DS_SCHEDULE_JOB_INFO
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
cache 50;

--DROP sequence  SEQ_DS_SYNCH_JOB_LOG_INFO;
create sequence SEQ_DS_SYNCH_JOB_LOG_INFO
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
cache 50;

--DROP sequence  SEQ_DS_COLUMN_MAPPING_INFO;
create sequence SEQ_DS_COLUMN_MAPPING_INFO
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
cache 50;
