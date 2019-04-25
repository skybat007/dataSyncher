package com.cetc.cloud.datasynch.provider.config;


import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import com.cetc.cloud.datasynch.provider.controller.ScheduleController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.support.CronTrigger;
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
    public void run(ApplicationArguments var1) throws Exception {
        String[] sourceArgs = var1.getSourceArgs();
        boolean value = false;
        for (String arg : sourceArgs) {
            if (arg.contains("-stopAllJobs")) {
                String[] split = arg.split("=");
                if (split[1] != null) {
                    value = Boolean.valueOf(split[1]);
                    log.info("withInputParam：:stopAllJobs-- " + value);
                }
            }
        }
        if (value == false) {
            startAllJobs();
        }else {
            log.info("input args:stopAllJobs=true");
        }
    }
    /** 启动所有的schedule任务 */
    public void startAllJobs(){
        scheduleController.startAllEnabledScheduleJobs();
        /*启动任务--比对更新三小场所列表*/
        String cron_JOB_refresh_sanxiao_list = "00 00 8 * * ?";
        CronTrigger trigger_cron_JOB_refresh_sanxiao_list = new CronTrigger(cron_JOB_refresh_sanxiao_list);
        scheduleController.startOuterScheduleJob(CommonInstance.JOB_refresh_sanxiao_list, trigger_cron_JOB_refresh_sanxiao_list);
        /*启动任务--计算隐患三小场所*/
        String cron_JOB_calc_trouble_sanxiao = "00 30 8 * * ?";
        CronTrigger trigger_cron_JOB_calc_trouble_sanxiao = new CronTrigger(cron_JOB_calc_trouble_sanxiao);
        scheduleController.startOuterScheduleJob(CommonInstance.JOB_calc_trouble_sanxiao, trigger_cron_JOB_calc_trouble_sanxiao);
        /*启动任务--获取今日信访数据*/
        String cron_JOB_get_today_xinfang = "30 40 17 * * ?";
        CronTrigger trigger_cron_JOB_get_today_xinfang = new CronTrigger(cron_JOB_get_today_xinfang);
        scheduleController.startOuterScheduleJob(CommonInstance.JOB_get_today_xinfang, trigger_cron_JOB_get_today_xinfang);
        /*启动任务--获取时间附件信息*/
        String cron_JOB_add_chengguanevent_attach = "00 0/30 * * * ?";
        CronTrigger trigger_cron_JOB_add_chengguanevent_attach = new CronTrigger(cron_JOB_add_chengguanevent_attach);
        scheduleController.startOuterScheduleJob(CommonInstance.JOB_add_chengguanevent_attach, trigger_cron_JOB_add_chengguanevent_attach);
        /*启动任务--获取天气预警信息*/
        String cron_JOB_get_weather_alarm_info = "05 0/5 * * * ?";
        CronTrigger trigger_cron_JOB_get_weather_alarm_info = new CronTrigger(cron_JOB_get_weather_alarm_info);
        scheduleController.startOuterScheduleJob(CommonInstance.JOB_get_weather_alarm_info, trigger_cron_JOB_get_weather_alarm_info);
        /*启动任务--获取空气AQI信息*/
        String cron_JOB_generate_water_AQI_info = "19 00 * * * ?";
        CronTrigger trigger_cron_JOB_generate_water_AQI_info = new CronTrigger(cron_JOB_generate_water_AQI_info);
        scheduleController.startOuterScheduleJob(CommonInstance.JOB_generate_water_AQI_info, trigger_cron_JOB_generate_water_AQI_info);
        /*启动任务--获取空气AQI信息*/
        String cron_JOB_refresh_video = "35 09 2 1 * ?";
        CronTrigger trigger_cron_JOB_refresh_video = new CronTrigger(cron_JOB_refresh_video);
        scheduleController.startOuterScheduleJob(CommonInstance.JOB_refresh_video, trigger_cron_JOB_refresh_video);
    }
}