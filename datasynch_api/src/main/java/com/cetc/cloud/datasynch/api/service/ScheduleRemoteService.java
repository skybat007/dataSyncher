package com.cetc.cloud.datasynch.api.service;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Api(description = "定时任务管理服务")
public interface ScheduleRemoteService {

    @RequestMapping(value = "/schedule/job/create", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "新增一条同步任务", notes = "新增一条同步任务", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "connType", value = "连接类型(0-数据库;1-接口)", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "source", value = "源-表名(例：QAJJ_PUCENTP_V)/源-URL(例：http://10.190.55.62:8080/GetLeadRota/v1/getLeadRotaByDate.action)", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "srcDs", value = "【数据库】数据源类别：0：readOnly；1：third", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isPagingQuery", value = "是否为分页查询（0-不分页;1.分页）", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "orderByColumnName", value = "【数据库】排序字段名,若不排序则填:rownum", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "httpParamExpression", value = "【接口】http入参表达式(例:StartDate=2018/9/24&EndDate=2018/9/30)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "httpToken", value = "【接口】http Token表达式(例:Authorization:Bearer e2d40b3d-54a7-3d57-8288-ce6e9bf95cb6)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "httpPagingType", value = "【接口】分页参数组织类型(填数字)（1:pageNum+pageSize;2:json格式表达的pageNum+pageSize;3:position【如：块数据的startPosition和maxCount】）", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "httpParamPageSize", value = "【接口】pageSize对应参数名;【httpPagingType=2:page】【httpPagingType=3:如MAXCOUNT/maxCount】", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "httpParamPageNum", value = "【接口】pageNum对应参数名;【httpPagingType=2:page】【httpPagingType=3:如STARTPOSITION/startPosition】", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "httpJsonExtractRule", value = "【接口】httpJson解析规则(例:data.resultSet.*)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "targetTableName", value = "目标表名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "needsTruncateTargetTb", value = "是否要清空目标表【0:不清空 1:清空】", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页大小【默认1000】", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "cronExpression", value = "cron表达式 例：【每30秒请求一次：0/30 \\* \\* \\* \\* ?】【每1分钟请求一次 0 0/1 \\* \\* \\* ?】", required = false, dataType = "String", paramType = "query")
    })
    HashMap createScheduleJob(int connType, String source, String srcDs, int isPagingQuery,
                              String orderByColumnName,
                              String httpParamExpression, String httpToken, String httpPagingType, String httpParamPageSize,
                              String httpParamPageNum, String httpJsonExtractRule,
                              String targetTableName, int needsTruncateTargetTb, String pageSize, String cronExpression) throws SQLException;

    @RequestMapping(value = "/schedule/job/querylist", produces = "application/json", method = RequestMethod.GET)
    @ApiOperation(value = "查询表同步任务List", notes = "查询表同步任务List", produces = "application/json")
    List<ScheduleModel> queryScheduleJobList();



    @RequestMapping(value = "/schedule/job/start/byJobId", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "根据jobID启动任务", notes = "根据jobID启动任务", produces = "application/json")
    HashMap<String, String> startScheduleJobByJobId(int jobId);

    @RequestMapping(value = "/schedule/job/trigger/byTableName", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "根据tableName触发执行单次任务", notes = "根据tableName触发执行单次任务", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName", value = "目标表名称", required = true, dataType = "String", paramType = "query")
    })
    HashMap<String, String> triggerOnceJobByTargetTableName(String tableName);

    @RequestMapping(value = "/schedule/job/start/outerJob/byJobName", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "根据jobName和cronExpression启动任务", notes = "根据jobName和cronExpression启动任务", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobName", value = "只能在这些集合中选取：[calc_trouble_sanxiao,get_today_xinfang]", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "cronExpression", value = "cron表达式(例：每30秒请求一次：0/30 \\* \\* \\* \\* ? |每1分钟请求一次 0 0/1 \\* \\* \\* ?)", required = false, dataType = "String", paramType = "query")
    })
    HashMap<String,String> startOuterScheduleJob(String jobName, CronTrigger trigger);

    @RequestMapping(value = "/schedule/job/triggerOnce/outerJob/byJobName", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "根据jobName启动单次自定义任务", notes = "\"calc_trouble_sanxiao\";\n\"get_today_xinfang\";\n\"add_chengguanevent_attach\";\n\"get_weather_alarm_info\";\n\"refresh_sanxiao_list\";\n\"generate_water_AQI_info\";", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobName", value = "只能在这些集合中选取：[calc_trouble_sanxiao,get_today_xinfang]", required = true, dataType = "int", paramType = "query"),
    })
    HashMap<String, String> triggerOnceOuterScheduleJobByJobName(String jobName);

    @RequestMapping(value = "/schedule/job/start/array", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "根据启动任务List", notes = "job1,job2,...,jobN", produces = "application/json")
    HashMap<String, String> startScheduleJobArrayByJobId(String jobs);

    @RequestMapping(value = "/schedule/job/start/allDsJobs", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "启动所有将要运行的job", notes = "", produces = "application/json")
    HashMap<String, String> startAllDSJobs();

    @RequestMapping(value = "/schedule/job/start/all", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "启动job列表中所有处于Enable状态的任务", notes = "", produces = "application/json")
    HashMap<String, String> startAllEnabledScheduleJobs();

    @RequestMapping(value = "/schedule/job/disable", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "根据jobID Disable任务（定时任务空转）", notes = "根据jobID Disable任务", produces = "application/json")
    HashMap<String, String> disableJobStatusByJobId(int jobId);

    @RequestMapping(value = "/schedule/job/enable", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "根据jobID Enable任务", notes = "根据jobID Enable任务", produces = "application/json")
    HashMap<String, String> enableJobStatusByJobId(int jobId);

    HashMap<String, String> deleteScheduleJobByJobId(int jobId);

    @RequestMapping(value = "/schedule/job/alter", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "修改一条表同步任务的更新频率--仅修改定时表达式", notes = "修改一条表同步任务的更新频率--仅修改定时表达式", produces = "application/json")
    HashMap<String, String> alterScheduleJobCron(int jobId, String cron);


    @RequestMapping(value = "/futures/get", produces = "application/json", method = RequestMethod.GET)
    @ApiOperation(value = "获取正在运行的任务列表", notes = "获取正在运行的任务列表", produces = "application/json")
    Map<String, Future> getRunningFutures();

}
