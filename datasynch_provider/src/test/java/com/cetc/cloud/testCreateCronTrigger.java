package com.cetc.cloud;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.scheduling.support.CronTrigger;

/**
 * PackageName:   com.cetc.cloud
 * projectName:   dataSyncher
 * Description:   LuoLinjie 补充
 * Creator:     by LuoLinjie
 * Create_Date: 2019/3/1 15:37
 * Updater:     by Administrator
 * Update_Date: 2019/3/1 15:37
 * Update_Description: Administrator 补充
 */
@Slf4j
public class testCreateCronTrigger {
    @Test
    public void testCreateCronTrigger(){
        JSONObject res = new JSONObject();
        String cronExpression = "0 0 0 2 * ?";
        try {
            CronTrigger trigger = new CronTrigger(cronExpression);
        }catch (Exception e){
            log.error("cron trigger syntax error! please check and try again!");
            res.put("result", "failed");
            res.put("msg","cron trigger syntax error! please check and try again!");
        }
    }
}
