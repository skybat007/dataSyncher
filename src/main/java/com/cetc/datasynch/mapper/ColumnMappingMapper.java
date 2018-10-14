package com.cetc.datasynch.mapper;

import com.cetc.datasynch.model.ColumnMappingModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * Created by llj on 2018/10/14.
 */
@Mapper
public class ColumnMappingMapper {

    public List<ColumnMappingModel> getMappingByTableName(String targetTable) {

        return null;
    }
}
