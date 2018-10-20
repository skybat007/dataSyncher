package com.cetc.datasynch.controller;

import com.cetc.datasynch.api.model.ColumnMappingModel;
import com.cetc.datasynch.api.service.ColumnMappingRemoteService;
import com.cetc.datasynch.service.impl.ColumnMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description：映射关系功能Controller层实现
 * Created by luolinjie on 2018/10/20.
 */
public class ColumnMappingController implements ColumnMappingRemoteService {

    @Autowired
    ColumnMappingService columnMappingService;


    @Override
    public String importExcelIntoDB(MultipartFile file) {
        //todo:解析Excel，生成List<Model>
        List<ColumnMappingModel> modelList = new ArrayList<ColumnMappingModel>();
        //todo 解析逻辑


        //todo：入库
        return columnMappingService.addList(modelList);
    }

    @Override
    public List<ColumnMappingModel> getListInfo(ColumnMappingModel model) {
        List<ColumnMappingModel> modelList = null;
        if (model.getSource() != null) {//源表名称或源URL
            return columnMappingService.getListInfoBySource(model.getSource());
        } else if (model.getTargetTable() != null) {//目标表
            return columnMappingService.getListInfoByTargetTable(model.getTargetTable());
        } else if (model.getTargetColumnName() != null) {//目标表字段名称
            return columnMappingService.getListInfoByTargetColumnName(model.getTargetColumnName());
        }
        return modelList;
    }

    @Override
    public Map<String, String> getMapInfo(ColumnMappingModel model) {
        return columnMappingService.getColumnMappingByTargetTableName(model.getTargetTable());
    }

    @Override
    public String deleteByid(int id) {
        return null;
    }

    @Override
    public String deleteByTargetTbName(String targetTbName) {
        return null;
    }

    @Override
    public String add(ColumnMappingModel model) {
        return null;
    }

    @Override
    public String update(ColumnMappingModel model) {
        return null;
    }
}
