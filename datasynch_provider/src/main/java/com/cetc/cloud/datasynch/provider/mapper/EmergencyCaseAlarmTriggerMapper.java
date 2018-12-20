package com.cetc.cloud.datasynch.provider.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * @Package: com.cetc.cloud.alarm.trigger.mapper
 * @Project: alarm-trigger
 * @Creator: huangzezhou
 * @Create_Date: 2018/12/17 20:05
 * @Updater: huangzezhou
 * @Update_Date: 2018/12/17 20:05
 * @Update_Description: huangzezhou 补充
 * @Description: //TODO
 **/
@Mapper
public interface EmergencyCaseAlarmTriggerMapper {

    @Select("select * from WEEKLY_EMERGENCY_CASE ORDER BY START_TIME desc")
    List<HashMap> queryAllEmergencyCase();

}
