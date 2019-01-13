package com.cetc.cloud.datasynch.provider.mapper;

import com.cetc.cloud.datasynch.api.model.ValueDictModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.mapper.input
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/12/5 17:03
 * Updater:     by luolinjie
 * Update_Date: 2018/12/5
 * Update_Description: luolinjie 补充
 **/
@Mapper
public interface ValueDictMapper {
    int addList(List<ValueDictModel> modelList);
}
