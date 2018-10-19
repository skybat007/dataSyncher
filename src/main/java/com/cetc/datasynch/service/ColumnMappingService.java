package com.cetc.datasynch.service;

import java.util.HashMap;

/**
 * Description：
 * Created by luolinjie on 2018/10/19.
 */
public interface ColumnMappingService {
    HashMap<String,String> getColumnMappingByTableName(String targetTable);
}
