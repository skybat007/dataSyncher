declare
	num   number;
begin
	select count(1) into num from user_tables where table_name = upper('LABOR_DISPUTE') ;
	if num > 0 then
		execute immediate 'drop table "ZHFTYJJCPT"."LABOR_DISPUTE"' ;
	end if;
end;
CREATE TABLE "ZHFTYJJCPT"."LABOR_DISPUTE" (
"ROW_ID" VARCHAR2(200 BYTE) NULL ,
"SOURCE" VARCHAR2(200 BYTE) NULL ,
"PUSH_TIME" VARCHAR2(200 BYTE) NULL ,
"QYMC" VARCHAR2(200 BYTE) NULL ,
"LDDM" VARCHAR2(200 BYTE) NULL ,
"WRITETIME" VARCHAR2(200 BYTE) NULL ,
"MONEY" VARCHAR2(200 BYTE) NULL ,
"CONTENT" CLOB NULL ,
"TYPE" VARCHAR2(200 BYTE) NULL ,
"TITLE" VARCHAR2(200 BYTE) NULL
)
PCTFREE 10
INITRANS 1
STORAGE (
	INITIAL 65536
	NEXT 1048576
	MINEXTENTS 1
	MAXEXTENTS 2147483645
	BUFFER_POOL DEFAULT
)

TABLESPACE "USERS"
LOGGING
NOCOMPRESS
NOCACHE
;
