package com.cetc.cloud.datasynch.provider.service.impl.alarm;

import com.cetc.cloud.datasynch.provider.core.util.SpendTimeUtil;
import com.cetc.cloud.datasynch.provider.mapper.IotAlarmTriggerMapper;
import com.cetc.cloud.datasynch.provider.mapper.entity.AlarmInformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
public class IotAlarmTriggerServiceImpl extends AlarmTriggerAbstract {

    @Autowired
    IotAlarmTriggerMapper iotAlarmTriggerMapper;

//    @Scheduled(cron = "0 0/5 * * * *")
    @Scheduled(cron = "0/30 * * * * *")
    public void schedule(){
        Thread.currentThread().setName("IotAlarm");
        long start = System.currentTimeMillis();
        log.info("iot trigger start");
        triggerAlarm();
        log.info("iot trigger finished! spend: "+ SpendTimeUtil.hhMMss(System.currentTimeMillis() - start));
    }

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

        if (device_type.equals(SMOKE)) {
            result.LV1 = "004";
            result.LV2 = "004001";

        } else if (device_type.equals(GAS)) {
            result.LV1 = "004";
            result.LV2 = "004003";

        } else if (device_type.equals(ELECTRIC)) {
            result.LV1 = "004";
            result.LV2 = "004004";

        } else if (device_type.equals(FIRE)) {
            result.LV1 = "004";
            result.LV2 = "004005";

        } else if (device_type.equals(GROUND)) {
            result.LV1 = "011";
            result.LV2 = "011001";

        } else if (device_type.equals(WATER)) {
            result.LV1 = "003";
            result.LV2 = "003003";
        } else {
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
        return iotAlarmTriggerMapper.queryIotEventByTime(data2str(begin), data2str(end));
    }

    @Override
    protected AlarmInformation row2Alarm(HashMap row) {

        AlarmInformation model = new AlarmInformation();

        final String channel = "物联网平台";
        final String originTableName = "ALARM_INFORMATION";
        final String releasePerson = "预警监测平台";
        final int disposalState = 0;    //创建预警时，默认处置状态为：0
        final int sendState = 0;    //发送状态，默认未发送：0
        final String lddm = null;   //楼栋代码为空，物联网没有楼栋代码


        model.setOriginTableName(originTableName);
        model.setReleasePerson(releasePerson);
        model.setDisposalState(disposalState);
        model.setSendState(sendState);
        model.setChannel(channel);
        model.setLddm(lddm);

        String dataValue = String.valueOf(row.get("DATA_VALUE"));
        model.setAlarmState(getAlarmStateByDataValue(dataValue));

        model.setReleaseTime((Date) row.get("CREATE_TIME"));
        String deviceType = String.valueOf(row.get("DEVICE_TYPE"));
        AlarmLevelType type = getAlarmTypeByDeviceCode(deviceType);
        model.setAlarmTypeLv1(type.LV1);
        model.setAlarmTypeLv2(type.LV2);
        model.setAlarmObject(String.valueOf(row.get("ALARM_OBJECT")));
        model.setFRowId(Integer.parseInt(String.valueOf(row.get("OBJECT_ID"))));
        model.setAlarmLevel(String.valueOf(row.get("EVENT_LEVEL")));
        model.setJd84(String.valueOf(row.get("JD84")));
        model.setWd84(String.valueOf(row.get("WD84")));
        model.setRegionCode(String.valueOf(row.get("REGION_CODE")));
        model.setStreetCode(String.valueOf(row.get("STREET_CODE")));
        model.setCommunityCode(String.valueOf(row.get("COMMUNITY_CODE")));

        model.setContents(String.valueOf(row.get("DEVICE_NAME")) + "发生报警");
        model.setSystemId(UUID.randomUUID().toString().replace("-", ""));
        model.setCancelTime(null);
        model.setObjectId(0);   //任意值，在入库时，会自动生成

        model.setYjjcCreateTime(null);
        model.setYjjcUpdateTime(null);

        //TODO 去实现数据库获取
        String address = null;
        String streetName = null;
        String communityName = null;
        model.setAddress(address);
        model.setStreetName(streetName);
        model.setCommunityName(communityName);

        return model;
    }

    @Override
    protected boolean compareAlarm(AlarmInformation m1, AlarmInformation m2) {
        //物联网恒等于true，因为不存在预警升级的情况，所有预警都是一样的
        return true;
    }


}
