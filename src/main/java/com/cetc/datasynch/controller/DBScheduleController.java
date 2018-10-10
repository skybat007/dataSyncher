package com.cetc.datasynch.controller;

import com.cetc.datasynch.core.util.UuIdGeneratorUtil;
import com.cetc.datasynch.model.DBScheduleModel;
import com.cetc.datasynch.middleware.MySQLRunnable;
import com.cetc.datasynch.service.DBScheduleService;
import com.cetc.datasynch.service.impl.DbOperateService;
import com.cetc.datasynch.service.impl.ScheduleManageService;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 查询表同步任务List
     */

    public List<DBScheduleModel> queryScheduleJobList(){
        List<DBScheduleModel> list = dbScheduleService.queryScheduleJobList();
        return list;
    }

    /**
     * 新增一条表同步任务
     */
    public String createScheduleJob(String SQL,String cron) throws SQLException {

        MySQLRunnable mySQLRunnable = new MySQLRunnable(SQL);
        String uuid = UuIdGeneratorUtil.getCetcCloudUuid("cetc");
        String res = scheduleManageService.startJob(uuid, cron, mySQLRunnable);
        return res;
    }
    /**
     * 删除一条表同步任务
     */
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
     * todo:修改一条表同步任务
     */

    /**
     * todo:查询一条表同步任务-通过表名
     */



}
