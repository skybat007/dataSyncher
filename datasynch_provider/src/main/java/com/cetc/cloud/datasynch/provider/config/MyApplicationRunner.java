package com.cetc.cloud.datasynch.provider.config;


import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import com.cetc.cloud.datasynch.provider.controller.ScheduleController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by llj on 2018/7/18.
 */
@Component
@Slf4j
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    ScheduleController scheduleController;

    @Override
    public void run(ApplicationArguments var1) throws Exception{
//        /*启动所有的内部schedule任务*/
//        scheduleController.startAllEnabledScheduleJobs();
//        /*启动任务--比对更新三小场所列表*/
//        scheduleController.startOuterScheduleJob(CommonInstance.JOB_refresh_sanxiao_list, "00 00 8 * * ?");
//        /*启动任务--计算隐患三小场所*/
//        scheduleController.startOuterScheduleJob(CommonInstance.JOB_calc_trouble_sanxiao, "00 30 8 * * ?");
//        /*启动任务--获取今日信访数据*/
//        scheduleController.startOuterScheduleJob(CommonInstance.JOB_get_today_xinfang,"30 40 17 * * ?");
//        /*启动任务--获取时间附件信息*/
//        scheduleController.startOuterScheduleJob(CommonInstance.JOB_add_chengguanevent_attach,"00 0/30 * * * ?");
//        /*启动任务--获取天气预警信息*/
//        scheduleController.startOuterScheduleJob(CommonInstance.JOB_get_weather_alarm_info,"05 0/5 * * * ?");
//        /*启动任务--获取空气AQI信息*/
//        scheduleController.startOuterScheduleJob(CommonInstance.JOB_generate_water_AQI_info,"19 00 * * * ?");
    }
}