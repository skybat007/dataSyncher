package com.cetc.cloud.datasynch.provider.config;


import com.cetc.cloud.datasynch.provider.controller.ScheduleController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by llj on 2018/7/18.
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(MyApplicationRunner.class);

    @Autowired
    ScheduleController scheduleController;

    @Override
    public void run(ApplicationArguments var1) throws Exception{
        /*启动所有的schedule任务*/
//        scheduleController.startAllEnabledScheduleJobs();
    }
}