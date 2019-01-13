package com.cetc.cloud.datasynch.provider.service.impl.alarm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cetc.cloud.datasynch.provider.mapper.AlarmInformationMapper;
import com.cetc.cloud.datasynch.provider.mapper.entity.AlarmInformation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Package: com.cetc.cloud.alarm.trigger.service
 * @Project: alarm-trigger
 * @Creator: huangzezhou
 * @Create_Date: 2018/12/17 17:54
 * @Updater: huangzezhou
 * @Update_Date: 2018/12/17 17:54
 * @Update_Description: huangzezhou 补充
 * @Description: //TODO
 **/
@Service
public class BaseAlarmTrigger {

    @Autowired
    protected AlarmInformationMapper alarmInformationMapper;

    /**
     * 查询最新一条预警信息的发布时间(所有预警)
     */
    protected Date queryLastAlarmDataReleaseTime() {
        Page<AlarmInformation> page = new Page<AlarmInformation>(1,1);
        List<AlarmInformation> result = alarmInformationMapper.selectPage(page,
                new QueryWrapper<AlarmInformation>().orderBy(true, false, "RELEASE_TIME")).getRecords();
        return result.size() == 0 ? new Date(0) : result.get(0).getReleaseTime();
    }

    /**
     * 查询该主题最后一条预警信息
     */
    protected AlarmInformation queryLastAlarmData(int fRowId) {
        Page<AlarmInformation> page = new Page<AlarmInformation>(1,1);
        List<AlarmInformation> result = alarmInformationMapper.selectPage(page,
                new QueryWrapper<AlarmInformation>().eq("F_ROW_ID", fRowId).orderBy(true, false, "RELEASE_TIME")).getRecords();
        return CollectionUtils.isEmpty(result) ? null : result.get(0);
    }

    /**
     * 取消预警
     * @param systemId  事件编号
     * @param cancelTime  取消发布时间
     * @return
     */
    protected int cancelAlarm(String systemId, Date cancelTime) {

        UpdateWrapper<AlarmInformation> updateWrapper = new UpdateWrapper<AlarmInformation>();
        updateWrapper.set("ALARM_STATE", 0);
        updateWrapper.set("CANCEL_TIME", cancelTime);
        AlarmInformation alarmInformation = new AlarmInformation();
        return alarmInformationMapper.update(alarmInformation, updateWrapper.eq("SYSTEM_ID", systemId));
    }

    /**
     * 发布一条新预警
     * 1.需要先存放至数据库，
     * 2.然后推送，修改推送时间。推送是其他模块实现。
     * @param model
     * @return
     */
    protected int releaseAlarm(AlarmInformation model) {
        Page<AlarmInformation> page = new Page<AlarmInformation>(1,1);
        List<AlarmInformation> result = alarmInformationMapper.selectPage(page,
                new QueryWrapper<AlarmInformation>().orderBy(true, false, "OBJECT_ID")).getRecords();
        int object_id = CollectionUtils.isEmpty(result) ? 1 : result.get(0).getObjectId()+1;
        model.setObjectId(object_id);
        return alarmInformationMapper.insert(model);
    }


}
