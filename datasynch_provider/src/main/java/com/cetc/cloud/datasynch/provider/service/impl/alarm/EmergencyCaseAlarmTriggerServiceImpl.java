package com.cetc.cloud.datasynch.provider.service.impl.alarm;

import com.cetc.cloud.datasynch.provider.core.util.SpendTimeUtil;
import com.cetc.cloud.datasynch.provider.mapper.EmergencyCaseAlarmTriggerMapper;
import com.cetc.cloud.datasynch.provider.mapper.entity.AlarmInformation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @Package: com.cetc.cloud.alarm.trigger.service.impl
 * @Project: alarm-trigger
 * @Creator: huangzezhou
 * @Create_Date: 2018/12/17 16:06
 * @Updater: huangzezhou
 * @Update_Date: 2018/12/17 16:06
 * @Update_Description: huangzezhou 补充
 * @Description: //应急事件预警触发器
 **/
@Service
@Slf4j
public class EmergencyCaseAlarmTriggerServiceImpl extends AlarmTriggerAbstract {

    @Autowired
    EmergencyCaseAlarmTriggerMapper emergencyCaseAlarmTriggerMapper;

//    @Scheduled(cron = "0 0/5 * * * *")
    @Scheduled(cron = "0/30 * * * * *")
    public void schedule(){
        Thread.currentThread().setName("EmgCaseAlarm");
        long start = System.currentTimeMillis();
        log.info("emergency trigger start");
        triggerAlarm();
        log.info("emergency trigger finished! spend: " + SpendTimeUtil.hhMMss(System.currentTimeMillis() - start));
    }

    @Override
    protected List<HashMap> querySourceData(Date begin, Date end) {
        return emergencyCaseAlarmTriggerMapper.queryAllEmergencyCase();
    }

    @Override
    protected AlarmInformation row2Alarm(HashMap row) {
        if (StringUtils.isEmpty((String) row.get("ADDRESS")))
            return null;//预警对象为空，直接跳过
        AlarmInformation model = new AlarmInformation(
                0,  // 任意值
                (String)row.get("ADDRESS"),   //预警对象 为发生地址
                ((BigDecimal)row.get("OBJECT_ID")).intValue(),       //外键关联object_id
                "WEEKLY_EMERGENCY_CASE",    //来源表
                UUID.randomUUID().toString().replace("-",""),   //时间ID
                (Date)row.get("START_TIME"),  //发布时间
                null,   //取消时间，默认为空
                "预警监测平台",   //发布人
                alarmState((Date)row.get("START_TIME")),      //TODO 预警状态，1预警中，0取消预警
                String.valueOf(row.get("NAME")), //预警内容
                String.valueOf(row.get("LEVEL")),       //预警级别
                "001",  //事件预警
                "001002",   //应急突发事件预警
                0,      //处置状态，默认0，未处置
                0,      //发送状态，默认0，未发送
                "应急办",   //渠道
                //通用字段
                null,      //数据创建时间
                null,       //数据更新时间
                null,       //楼栋代码
                (String)row.get("JD84"),       //JD84
                (String)row.get("WD84"),       //WD84
                (String)row.get("REGION_CODE"), //区代码
                (String)row.get("STREET_CDDE"), //街道代码
                (String)row.get("COMMUNITY_CODE"),  //社区代码
                (String)row.get("ADDRESS"), //地址
                (String)row.get("STREET_NAME"), //街道名
                (String)row.get("COMMUNITY_NAME")   //社区名
        );
        return model;
    }

    @Override
    protected boolean compareAlarm(AlarmInformation m1, AlarmInformation m2) {
        return true;
    }

    /**
     * 判断预警状态
     *
     * 逻辑：距离该事件7天内(含)为预警中，7天以上取消预警
     * @param happenTime 应急事件发生时间
     * @return
     */
    private int alarmState(Date happenTime){
        Date now = new Date();
        Date d30 = DateUtils.addDays(now, -7);
        if (d30.getTime() > happenTime.getTime()){
            return 0;
        }
        return 1;
    }
}
