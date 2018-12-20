package com.cetc.cloud.datasynch.provider.mapper;

import com.cetc.cloud.datasynch.api.model.XinFangEventModel;
import com.cetc.cloud.datasynch.api.model.XinFangPeopleModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.mapper.input
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/11/28 19:43
 * Updater:     by luolinjie
 * Update_Date: 2018/11/28
 * Update_Description: luolinjie 补充
 **/
@Mapper
public interface XinfangEventMapper {

    int addEvent(@Param("xinFangEventModel")XinFangEventModel xinFangEventModel);

    int addPerson(@Param("personModel") XinFangPeopleModel personModel);

    Set<String> getVisitCodeList();
}
