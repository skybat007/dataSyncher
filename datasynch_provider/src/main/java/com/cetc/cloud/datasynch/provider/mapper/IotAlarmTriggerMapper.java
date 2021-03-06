package com.cetc.cloud.datasynch.provider.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @Package: com.cetc.cloud.datasynch.provider.mapper.input
 * @Project: dataSyncher
 * @Creator: huangzezhou
 * @Create_Date: 2018/12/12 9:26
 * @Updater: huangzezhou
 * @Update_Date: 2018/12/12 9:26
 * @Update_Description: huangzezhou 补充
 * @Description: AlarmTriggerMapper
 **/
@Mapper
public interface IotAlarmTriggerMapper{

    /**
     * 查询物联网报警数据, 升序查询，升序操作保证幂等
     * @param start_date_str  （不包含）开始时间字符串：2018-09-11 00:00:00
     * @param end_date_str （包含）结束时间字符串
     * @return
     */
    List<HashMap> queryIotEventByTime(@Param("start_date_str") String start_date_str, @Param("end_date_str") String end_date_str);

}
