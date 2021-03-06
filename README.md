# dataSyncher
数据同步集成组件，支持从Oracle数据库和接口2种方式增量以及全量更新，持续更新中。。。

# 1.	数据更新
 
图1 数据更新方案示意图
## 1.1	 更新需求
### 1.1.1	支持从华云共性能力平台发布的接口进行增量更新；
### 1.1.2	支持从华云共性能力平台发布的接口进行全量更新；
### 1.1.3	支持从信息中心前置机进行数据库级别的全量更新；
### 1.1.4	支持从信息中心前置机进行数据库级别的增量更新。
### 1.2	 需求分解
	建立从“数据接口—业务库表名”之间的映射，每个“接口对应的返回字段--业务库表的字段”之间的映射；
	建立从“前置机view-业务库表名”之间的映射，以及对应“前置机view字段--业务库字段”之间的映射。
	数据库更新通过SQL分页查询，批量更新至业务库中。
	数据插入依赖“字段映射表”，获取到的数据根据该表指向目标表的指定字段
	全量更新依赖增量更新

## 1.3	 全量更新分页拉取逻辑
### 1.	page从1开始，pagesize为固定值（默认100），每请求到一批数据，先判断获取到的内容是否等于100（若等于100则继续请求，若小于100则说明已经到了最后一页，结束请求）；
### 2.	定时发出请求，时间可以自由设定。（每个接口请求的时间不一定是同一个时间频率，因此需要为每个接口创建专用定时器）；
### 3.	每次请求完成后，需要设定一个记录值，记录当前已经到了哪一页pageIndex和哪一条数据index（相当于自增ID），下次请求的时候，直接从该页开始请求。
## 1.4	 增量更新分页拉取逻辑
### 1.	设定定时器，每天定时扫描一次接口列表和前置机，如果发现有新的数据入库，立即根据当前记录的位置信息拉取增量的数据至业务库；
### 2.	接口方式：从专用表（DP_INTERFACE）中获取当前抓取到的页码数和index数；根据当前页码数请求，Insert至数据库；
### 3.	接口方式：从专用表（DP_TB）中获取当前抓取到的页码数和index数；根据当前页码数请求，Insert至数据库。
## 1.5	 oracle无自增主键实现增量更新的方案
### 1.通过分页查询，并记录伪ROWNUM与ROWID。每查询一页，ROWNUM，ROWID都需要记录在表中作为操作日志。前提：源数据不存在delete的情况，否则会造成查询内容与之前的重复。
### 2.分页查询sql示例：
SELECT *
FROM (
	SELECT @TABLE_NAME.*, rownum rownum_
    FROM @TABLE_NAME
    WHERE rownum <= @ENDROW)
WHERE rownum_ >= @STARTROW;
注：
@STARTROW：起始行（包含该行）
@ENDROW：结束行（包含该行）

## 1.6	 数据更新定时任务
### 1.	前置机主要采取的方式是利用DataX离线同步组件：每张表建立的数据更新任务实例，指定该实例的开关状态；执行同步job；
### 2.	接口方式主要采取的方式是利用Quartz调度器进行调度，因此需要维护一个任务表，指定该任务的执行时间，支持动态调整任务规划；
## 1.7	 数据写入数据库的字段映射关系维护
从数据库和接口查询返回的数据Body，因为业务库需求对某些字段名称进行了修改，造成信息不对称，因此需要建立一张表，说明“接口-目标表”和“前置机表-目标表”之间的字段映射关系。
## 1.8	功能设计
### 1.8.1	初始化
执行初始化SQL文件，创建表、序列、触发器等
注意：触发器需要手动创建
### 1.8.2	定时任务创建与维护
	创建一个定时任务
传入任务执行实体和任务定时表达式，创建一个定时任务，并启动任务
	查询定时任务列表
将当前定时任务以列表形式返回
	删除一个定时任务
先停止该定时任务，后删除该定时任务
	修改一个定时任务（只限修改定时频率）
先停止该定时任务，后修改定时表达式，最后启动该任务
	启动一个定时任务（基础功能）
根据jobID启动一个定时任务
	停止一个定时任务（基础功能）
根据jobID停止一个定时任务
### 1.8.3	数据库方式抽取任务管理
	通过传入查询SQL以及分页参数创建数据库抽取定时任务，同时返回任务ID。
执行体run()方法逻辑：
在日志表中通过jobID查询最近一条执行成功的数据同步日志，当不存在时表明当前为新开启的一个同步任务，则将分页参数设置为默认值（默认值应该在common类中指定），然后启动任务；当查询到最近一条同步任务时，根据当前的分页参数，计算出新的分页参数，然后启动任务。
注：任务创建时，必须在SQL查询语句中预留页码自增功能，当成功执行当前任务并开始执行下次查询任务前，需要重新计算分页参数。
	通过传入定时任务ID，启动/停止该任务。
### 1.8.4	接口方式抽取任务管理
	分页数据获取
通过调取HTTP在线数据抓取组件，创建定时任务。
注：任务创建时，必须在HTTP请求的URL中预留页码自增功能，当成功执行当前任务并开始执行下次查询任务前，需要重新计算分页参数。
	将获取到的分页数据包解析并入库：
查询“字段映射表”，获取字段的映射关系
生成SQL 插入语句，调用SQL中间件执行插入操作
	Json解析规则管理：
使用.properties文件维护解析规则，在程序初始化时将该文件加载进来。
### 1.8.5	映射关系维护
	将Excel人工维护的字段映射表，srcTable-targetTable映射表导入数据库进行维护
列字段顺序：
0-Source
1-SourceColumnName
2-TargetColumnName
3-TargetTable
	根据源表名称或源URL、目标表查询该表的映射关系列表
	删除一条映射关系
	删除单张表的所有映射关系
	增加一条映射关系
	修改一条映射关系
### 1.8.6	中间件-HTTP在线数据抓取组件
	HTTP在线获取分页数据包：
		传入URL与分页参数，在线获取数据包。
### 1.8.7	中间件-数据库SQL执行组件
	SQL查询：
传入SQL语句，执行并返回结果集List。
	SQL插入：
传入SQL语句，执行并返回插入结果

注意：
	在构造insert语句时,NUMBER类型的数据是带分号的；
Date类型的数据，需要将String转化为to_date（*）
如果该字段数据不存在，则使用null进行填充；

## 1.9	表结构设计
### 1.9.1	定时任务表结构设计
	任务表的用途在于，能够对每张表进行总体控制，包括使能控制、定时任务的控制，
并且定时任务支持动态修改。
表1：接口定时任务表: DS_SCHEDULE_JOB_INFO
字段	字段释义	字段类型
ID	任务唯一主键	NUMBER
CONN_TYPE	更新方式（0：数据库 1：接口）	NUMBER
SOURCE	源(请求URL路径/源表或视图的名称)	VARCAHR2
PARAM_EXPRESSION	token@入参表达式	VARCAHR2
PAGE_SIZE	页大小	NUMBER
TB_NAME	目标入库表名	VARCAHR2
SCHEDULE_EXPRESSION	定时表达式	VARCAHR2
IS_ENABLED	是否开启（0：关闭 1：开启）	NUMBER
CREATE_TIME	创建时间	DATE
UPDATE_TIME	更新时间	DATE

注：
PARAM_EXPRESSION：包含入参表达式以及pageSize、pageNum映射规则
例如：
token@customer_code=ftqw&pgsz=20&pgno=1&starttime=2018-07-20#[pageNum=pgno][pageSize=pgsz]
将在组装成URL之后将变为：
http://URL?customer_code=ftqw&pgsz=*&pgno=*&starttime=2018-07-20
### 1.9.2	更新状态日志记录表结构设计
	设计该表的目的是为了记录当前数据抓取进程的状态，对每张表做一个日志记录，便于进程下次启动的时候，从该表能够直接获取到需要接续抓取的分页参数，继续进行数据抓取。
表2：更新状态日志表: DS_SYNCH_JOB_LOG_INFO
字段	字段释义	字段类型
ID	唯一主键	NUMBER
JOBID	关联任务ID	NUMBER
CURRENT_PAGE	当前请求页码	NUMBER
CURRENT_ROWNUM	当前页最后一条数据的分页查询伪ID	NUMBER
PAGE_SIZE	页大小	NUMBER
CONN_TYPE	更新方式（0：数据库 1：接口）	NUMBER
CREATE_TIME	创建时间	DATE
UPDATE_TIME	更新时间	DATE
### 1.9.3	字段映射关系表结构设计
	该表用来记录将从前置机(或者接口)获取到的数据包的字段名称对应的业务表中的字段名称之间的关系。
表3：字段映射关系表: DS_COLUMN_MAPPING_INFO
字段	字段释义	字段类型
ID	唯一主键	NUMBER
TARGET_TABLE	目标表名称	VARCHAR2
SOURCE	源表名称/源URL	VARCHAR2
SRC_COLUMN_NAME	源字段名称	VARCHAR2
TARGET_COLUMN_NAME	目标字段名称	VARCHAR2
CREATE_TIME	创建时间	DATE
UPDATE_TIME	修改时间	DATE
### 1.9.4	业务库表结构统一字段约定
表4：关系表: DS_COLUMN_MAPPING_INFO
字段	字段释义	字段类型
INDEX_ID	唯一主键	NUMBER
DDD_CREATE_TIME	创建时间	DATE
DDD_UPDATE_TIME	修改时间	DATE
<REGION_CODE>	区代码<可选，落图表必选>	VARCHAR2
<STREET_CODE>	街道代码<可选，落图表必选>	VARCHAR2
<COMMUNITY_CODE>	社区代码<可选，落图表必选>	VARCHAR2

## 1.10	其他配置
### 1.10.1	建表SQL脚本
执行该组件之前，需要初始化该数据库，创建表、序列、触发器等以支持该组件功能：
	创建表、序列：
 
	创建自增主键触发器：
 

### 1.10.2	数据库连接配置
本地数据库配置：
	IP：10.190.55.65
账号：zhftyjjcpt（待创建）
密码：ToKreDi*nJ
监听服务名称：orcl
前置机数据库配置：
账号：zhftyjjcpt
密码：ToKreDi*nJ
监听服务名称：coreora
# 2.	数据字典建立与维护
对于每张表，都必须有数据字典(或者数据编目)进行对应，否则将造成信息缺失，因此必须编写工具，实现数据字典的集中维护。
## 2.1	 功能设计
1.	查询当前所有业务表，包括数据来源，是否包含经纬度字段，是否是落图表等等标签;
2.	根据业务表的数据来源、是否为落图表、是否包含经纬度字段来进行多条件筛选;
3.	查询单张表的字段名称、字段类型、字段释义;
4.	导出当前业务库的数据字典为Excel;
5.	导出单张表的数据字典为Excel;
6.	V2.0支持前端方式操作;
## 2.2	 前期维护方式切换至现有方式：
1.通过代码加载之前创建的.properties文件；
2.将.properties文件中对应的内容，按照表名、字段自上而下出现的顺序进行读取，并向专用表（DDD_*，Data Dictionary Definition）进行写入。

1.	数据字典分为3类：
a.每个Table与Column对应的comment ;
b.每个column对应的数据类型;
c.字段值的代码表示的真实含义（如0-male  1-female）。
2.	专用表结构设计：
表3：数据字典-表名注释表: DDD_TABLE_COMMENT 
字段	字段释义
TB_NAME	表名
TABLE_COMMENT	表注释

表4：数据字典-字段注释表:DDD_COLUMN_COMMENT
字段	字段释义
TB_NAME	表名
COLUMN_NAME	字段名
COLUMN_COMMENT	字段注释

表5：数据字典-值映射表:DDD_VALUE_ MAPPING 
字段	字段释义
TB_NAME	表名
COLUMN_NAME	字段名
COLUMN_CODE_VALUE	字段代码值
CODE_MEANING	代码含义

# 3.	数据字典维护
## 3.1	 两种维护方式对比
1.通过.properties文件进行维护(优点：直观 缺点：文件太大时不便于查找统计，容易漏掉)
2.通过数据库中的一张专用表直接维护（优点：便于持久化，不需要每次通过文件同步；缺点：展示体验差）

解决方案：
a.写一个从.properties文件到数据库的导入工具；(一次性的，导入到数据库后就不用再维护这个文件，以后统一在数据库中维护)；
b.写一个java web程序，界面化展示，作为数据字典维护专用工具。

## 3.2	 java web维护工具所具备的功能拆解：
1. 查漏功能：对目前还未添加comment的表注释、字段注释进行查询，指导DBA进行补全；
2. 对比功能：对比数据库中现有数据，与DDD_*表中对应的数据字段数量、字段名称及其comment是否一致，若不一致，则用列表展示出来。
3. 新增comment功能：支持对单字段、单表手动录入comment，并更新至oracle数据库；
4. 新增code_mapping功能：支持对字段值的code增加含义解释；



