package com.cetc.cloud.datasynch.provider.mapper.input;

import com.cetc.cloud.datasynch.api.model.AlarmModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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
 * @Description: //TODO
 **/
@Mapper
public interface AlarmTriggerMapper {

    /**
     * 查询物联网报警数据, 升序查询，升序操作保证幂等
     * @param start_date_str  （不包含）开始时间字符串：2018-09-11 00:00:00
     * @param end_date_str （包含）结束时间字符串
     * @return
     */
    List<HashMap> queryIotEventByTime(@Param("start_date_str") String start_date_str, @Param("end_date_str") String end_date_str);

    /**
     * 根据当前的device_code和当前时间，请求该设备前一条告警数据。
     * @param device_code
     * @param data_str
     * @return
     */
    HashMap queryDeviceTheLastIotEvent(@Param("device_code") String device_code, @Param("data_str") String data_str);


    /**
     * 查询最新一条预警信息的发布时间(所有预警)
     */
    Date queryLastAlarmDataReleaseTime();

    /**
     * 查询该主题最后一条预警信息
     * @param fRowId 主体id
     */
    AlarmModel queryLastAlarmData(int fRowId);


    /**
     * 取消预警
     * @param systemId
     * @param cancelTime
     * @return
     */
    int cancelAlarm(@Param("systemId") String systemId, @Param("cancelTime") Date cancelTime);

    /**
     * 发布预警
     * @param model
     * @return
     */
    int releaseAlarm(AlarmModel model);
}
