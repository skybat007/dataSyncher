package com.cetc.cloud.datasynch.provider.service.impl;

import com.cetc.cloud.datasynch.api.model.ValueDictModel;
import com.cetc.cloud.datasynch.provider.mapper.input.ValueDictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.service.impl
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/12/5 15:55
 * Updater:     by luolinjie
 * Update_Date: 2018/12/5
 * Update_Description: luolinjie 补充
 **/
@Service("valueDictService")
public class ValueDictService {
    @Autowired
    ValueDictMapper valueDictMapper;

    public int addList(List<ValueDictModel> modelList){
       return valueDictMapper.addList(modelList);
    }
}
