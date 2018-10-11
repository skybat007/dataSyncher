package com.cetc.datasynch.controller;

import com.cetc.datasynch.core.util.UuIdGeneratorUtil;
import com.cetc.datasynch.middleware.SQLCreator;
import com.cetc.datasynch.model.DBScheduleModel;
import com.cetc.datasynch.model.MySQLRunnable;
import com.cetc.datasynch.service.impl.DBLogInfoService;
import com.cetc.datasynch.service.impl.DBScheduleService;
import com.cetc.datasynch.service.impl.DbOperateService;
import com.cetc.datasynch.service.impl.ScheduleManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Description：数据库（前置机）定时任务管理
 * Created by luolinjie on 2018/10/9.
 */
@RestController
public class DBScheduleController {

    @Autowired
    DbOperateService dbOperateService;

    @Autowired
    ScheduleManageService scheduleManageService;

    @Autowired
    DBScheduleService dbScheduleService;

    @Autowired
    DBLogInfoService dbLogInfoService;

    /**
     * 初始化建表
     */
    @RequestMapping(value = "/schedule/init", produces = "application/json", method = RequestMethod.GET)
    public void initSQL() throws SQLException {
        dbOperateService.oracleBatchSqlFile("/dataSynch.sql");
    }

    /**
     * 查询表同步任务List
     */
    @RequestMapping(value = "/schedule/dbjob/querylist", produces = "application/json", method = RequestMethod.GET)
    public List<DBScheduleModel> queryScheduleJobList(){
        List<DBScheduleModel> list = dbScheduleService.queryScheduleJobList();
        return list;
    }

    /**
     * 新增一条表同步任务
     */
    @RequestMapping(value = "/schedule/dbjob/create", produces = "application/json", method = RequestMethod.GET)
    public String createScheduleJob(String tableName,String cron) throws SQLException {

        MySQLRunnable mySQLRunnable = new MySQLRunnable(tableName);
        String res = scheduleManageService.startJob(cron, mySQLRunnable);
        return res;
    }
    /**
     * 删除一条表同步任务
     */
    @RequestMapping(value = "/schedule/dbjob/delete", produces = "application/json", method = RequestMethod.GET)
    public HashMap<String, String> deleteScheduleJob(String jobID){

        HashMap result = new HashMap<String,String>();
        //停止当前任务
        String s = scheduleManageService.stopJob(jobID);
        //从列表中删除当前定时任务
        int res = dbScheduleService.deleteJobByJobId(jobID);

        if (res==1){
            result.put("delete success","job:"+jobID);
            return result;
        }else {
            result.put("delete fail","job:"+jobID);
            return result;
        }
    }
    /**
     * 修改一条表同步任务的更新频率--仅修改定时表达式
     */
    @RequestMapping(value = "/schedule/dbjob/alter", produces = "application/json", method = RequestMethod.GET)
    public HashMap<String, String> alterScheduleJob(String jobID,String cron){

        HashMap result = new HashMap<String,String>();
        //停止当前任务
        String s = scheduleManageService.stopJob(jobID);


        //
        int res = dbScheduleService.updateCronByJobId(jobID, cron);

        //重新启动当前任务--通过JobID（前提是必须之前有这个Job）
        boolean restartRes = restartJobByJobId(jobID);
        if (restartRes==false){
            //更改当前任务状态为Disabled
            dbScheduleService.disableStatusByJobId(jobID);
            result.put( "job:"+jobID,"disableStatusByJobId failed!");
            return result;
        }

        boolean s1 = restartJobByJobId(jobID);
        if (s1){
            result.put("job:"+jobID,"update cron success!");
            return result;
        }else {
            result.put("job:"+jobID,"update cron failed!");
            return result;
        }
    }

    /**
     * 重新启动任务
     * @param jobID
     * @return
     */
    public boolean restartJobByJobId(String jobID) {
        //查询tableName，通过jobID查询当前操作的tableName
        String tableName = dbScheduleService.queryTableNameByJobId(jobID);
        //通过JobId查询当前job最近一次成功请求到的分页状态记录值
        List<Integer> params = dbLogInfoService.queryLatestPageParamsByJobID(jobID);
        int pageNum = params.get(0);
        int pageSize = params.get(1);
        String SQL = SQLCreator.createSQLByTbNameAndRowParam(tableName,pageNum,pageSize);

        //创建runnable任务

        //启动该任务



        return false;
    }



}
