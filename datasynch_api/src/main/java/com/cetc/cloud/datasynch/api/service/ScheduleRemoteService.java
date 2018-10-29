package com.cetc.cloud.datasynch.api.service;

import com.cetc.cloud.datasynch.api.model.ScheduleModel;
import com.cetc.cloud.datasynch.api.model.SynchJobLogInfoModel;
import com.cetc.cloud.datasynch.api.model.Token;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "createScheduleJob", notes = "新增一条同步任务", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "connType", value = "连接类型(0-数据库;1-接口)", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "source", value = "源-表名(例：QAJJ_PUCENTP_V)/源-URL(例：http://10.190.55.62:8080/GetLeadRota/v1/getLeadRotaByDate.action)", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "orderByColumnName", value = "排序字段名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "httpParamExpression", value = "http入参表达式(例:StartDate=2018/9/24&EndDate=2018/9/30)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "httpToken", value = "http Token表达式(例:Authorization=Bearer e2d40b3d-54a7-3d57-8288-ce6e9bf95cb6)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "httpParamPageSize", value = "pageSize对应参数名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "httpParamPageNum", value = "pageNum对应参数名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "httpJsonExtractRule", value = "httpJson解析规则(例:data.resultSet)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "httpTotalExtractRule", value = "httpTotal解析规则(例:total)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "targetTableName", value = "目标表名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "cronExpression", value = "cron表达式(例：0 0/1 \\* \\* \\* ?)", required = false, dataType = "String", paramType = "query")
    })
    HashMap createScheduleJob(int connType, String source,
                              String orderByColumnName,
                              String httpParamExpression, String httpToken, String httpParamPageSize,
                              String httpParamPageNum, String httpJsonExtractRule, String httpTotalExtractRule,
                              String targetTableName, String pageSize, String cronExpression) throws SQLException;
    @RequestMapping(value = "/schedule/job/querylist", produces = "application/json", method = RequestMethod.GET)
    @ApiOperation(value = "queryScheduleJobList", notes = "查询表同步任务List", produces = "application/json")
    List<ScheduleModel> queryScheduleJobList();




    @RequestMapping(value = "/schedule/job/start", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "startScheduleJobByJobId", notes = "根据jobID启动任务", produces = "application/json")
    HashMap<String, String> startScheduleJobByJobId(int jobId);

    @RequestMapping(value = "/schedule/job/stop", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "stopScheduleJobByJobId", notes = "根据jobID停止任务", produces = "application/json")
    HashMap<String, String> stopScheduleJobByJobId(int jobId);

    @RequestMapping(value = "/schedule/job/delete", produces = "application/json", method = RequestMethod.DELETE)
    @ApiOperation(value = "deleteScheduleJobByJobId", notes = "删除一条同步任务", produces = "application/json")
    HashMap<String, String> deleteScheduleJobByJobId(int jobId);

    @RequestMapping(value = "/schedule/job/alter", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "alterScheduleJobCron", notes = "修改一条表同步任务的更新频率--仅修改定时表达式", produces = "application/json")
    HashMap<String, String> alterScheduleJobCron(int jobId, String cron);


    @RequestMapping(value = "/futures/get", produces = "application/json", method = RequestMethod.GET)
    @ApiOperation(value = "getRunningFutures", notes = "获取正在运行的任务列表", produces = "application/json")
    Map<String, Future> getRunningFutures();

}
