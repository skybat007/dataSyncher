package com.cetc.cloud.datasynch.provider.service.impl;

import com.cetc.cloud.datasynch.api.model.AlarmModel;
import com.cetc.cloud.datasynch.provider.mapper.input.AlarmTriggerMapper;
import com.cetc.cloud.datasynch.provider.service.AlarmTrigger;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Package: com.cetc.cloud.datasynch.provider.service.impl
 * @Project: dataSyncher
 * @Creator: huangzezhou
 * @Create_Date: 2018/12/11 15:45
 * @Updater: huangzezhou
 * @Update_Date: 2018/12/11 15:45
 * @Update_Description: huangzezhou 补充
 * @Description: 逻辑如下：
 * 查询全量的相关物联网数据->通过预警规则触发->生成预警列表->批量入口
 **/
@Service
@Slf4j
public class IotAlarmTriggerServiceImpl extends AlarmTrigger {

    @Autowired
    AlarmTriggerMapper alarmTriggerMapper;

    /**
     * 根据设备类型获取预警类型编号
     *
     * @param device_type
     * @return LV1 为一级预警 LV2 为二级预警
     */
    private AlarmLevelType getAlarmTypeByDeviceCode(String device_type) {
        final String SMOKE = "0003";//烟雾传感器
        final String GAS = "0021";//气体传感器
        final String ELECTRIC = "0024";//电器火灾检测设备
        final String FIRE = "0025";//消防栓水压传感器
        final String GROUND = "0023";//地质灾害监测设备
        final String WATER = "0026";//易涝点水位传感器

        AlarmLevelType result = new AlarmLevelType();

        switch (device_type) {
            case SMOKE:
                result.LV1 = "004";
                result.LV2 = "004001";
                break;
            case GAS:
                result.LV1 = "004";
                result.LV2 = "004003";
                break;
            case ELECTRIC:
                result.LV1 = "004";
                result.LV2 = "004004";
                break;
            case FIRE:
                result.LV1 = "004";
                result.LV2 = "004005";
                break;
            case GROUND:
                result.LV1 = "011";
                result.LV2 = "011001";
                break;
            case WATER:
                result.LV1 = "003";
                result.LV2 = "003003";
            default:
                break;
        }
        return result;
    }

    /**
     * 根据预警模型的信息判断当前预警状态，返回预警状态：1预警中，0预警结束
     * <p>
     * 执行逻辑：根据物联网数据不同，有两套逻辑。
     * 1.每次推送过来的预警信息都是监测值，相当于一直在推送新的预警。根据originTableName获取表名，然后根据object_id获取到最近一条预警信息，查看其预警状态
     * 如果预警状态是预警中，则将上一次的预警状态改为预警结束，然后新建预警。
     * 2.每次推送的不是监测值，而是预警状态，1表示预警，0表示语句结束。
     * <p>
     * 另一种解决方案（目前采取这种）：
     * 可以找到规律，除了消防水压之外的其他传感器，例如水位、烟感，都是当值为0时，就取消预警。只有消防水压取消不了。
     *
     * @param data_value 非0预警中，0预警结束
     * @return 预警状态：1预警中，0预警结束
     */
    private int getAlarmStateByDataValue(String data_value) {
        if ("0".equals(data_value)) {
            return 0;
        }
        return 1;
    }

    /**
     * 预警类型
     */
    class AlarmLevelType {
        String LV1;
        String LV2;
    }

    @Override
    protected List<HashMap> querySourceData(Date begin, Date end) {
        return alarmTriggerMapper.queryIotEventByTime(data2str(begin), data2str(end));
    }

    @Override
    protected Date queryLastAlarmDataReleaseTime() {
        return alarmTriggerMapper.queryLastAlarmDataReleaseTime();
    }

    @Override
    protected AlarmModel queryLastAlarmData(int fRowId) {
        return alarmTriggerMapper.queryLastAlarmData(fRowId);
    }


    @Override
    protected int cancelAlarm(String systemId, Date cancelTime) {
        return alarmTriggerMapper.cancelAlarm(systemId, cancelTime);
    }

    @Override
    protected int releaseAlarm(AlarmModel model) {
        return alarmTriggerMapper.releaseAlarm(model);
    }

    @Override
    protected AlarmModel row2Alarm(HashMap row) {

        final String channel = "物联网平台";
        final String originTableName = "ALARM_INFORMATION";
        final String releasePerson = "预警监测平台";
        final int disposalState = 0;    //创建预警时，默认处置状态为：0
        final int sendState = 0;    //发送状态，默认未发送：0
        final String lddm = null;   //楼栋代码为空，物联网没有楼栋代码


        String dataValue = String.valueOf(row.get("DATA_VALUE"));
        int alarmState = getAlarmStateByDataValue(dataValue);

        Date release_time = (Date) row.get("CREATE_TIME");
        String deviceType = String.valueOf(row.get("DEVICE_TYPE"));
        AlarmLevelType type = getAlarmTypeByDeviceCode(deviceType);
        String alarmTypeLv1 = type.LV1;
        String alarmTypeLv2 = type.LV2;
        String alarmObject = String.valueOf(row.get("ALARM_OBJECT"));
        int fRowId = Integer.parseInt(String.valueOf(row.get("OBJECT_ID")));

        String alarmLevel = String.valueOf(row.get("EVENT_LEVEL"));
        String jd84 = String.valueOf(row.get("JD84"));
        String wd84 = String.valueOf(row.get("WD84"));
        String regionCode = String.valueOf(row.get("REGION_CODE"));
        String streetCode = String.valueOf(row.get("STREET_CODE"));
        String communityCode = String.valueOf(row.get("COMMUNITY_CODE"));

        String contents = String.valueOf(row.get("DEVICE_NAME")) + "发生报警";

        String systemId = UUID.randomUUID().toString().replace("-","");
        Date cancelTime = null;
        int objectId = 0;   //任意值，在入库时，会自动生成

        //TODO
        String address = null;
        String streetName = null;
        String community = null;

        AlarmModel model = new AlarmModel(objectId, alarmObject, fRowId, originTableName, systemId, release_time, cancelTime,
                releasePerson, alarmState, contents, alarmLevel, alarmTypeLv1, alarmTypeLv2, disposalState, sendState,
                channel, address, streetName, community, null, null,  lddm, jd84, wd84, regionCode, streetCode, communityCode);


        log.info("iot trigger success");
        return model;
    }

    @Override
    protected boolean compareAlarm(AlarmModel m1, AlarmModel m2) {
        //物联网恒等于true，因为不存在预警升级的情况，所有预警都是一样的
        return true;
    }


}
