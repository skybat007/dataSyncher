package com.cetc.cloud.datasynch.provider.controller;

import com.alibaba.fastjson.JSONObject;
import com.cetc.cloud.datasynch.api.model.ColumnMappingModel;
import com.cetc.cloud.datasynch.api.service.ColumnMappingRemoteService;
import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import com.cetc.cloud.datasynch.provider.service.impl.ColumnMappingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description：映射关系功能Controller层实现
 * Created by luolinjie on 2018/10/20.
 */
@RestController
@Slf4j
public class ColumnMappingController implements ColumnMappingRemoteService {

    @Autowired
    ColumnMappingService columnMappingService;

    @Override
    public String importExcelIntoDB(MultipartFile file, String sheetName) {

        JSONObject res = new JSONObject();


        // 解析Excel，生成List<Model>
        List<ColumnMappingModel> modelList = new ArrayList<ColumnMappingModel>();
        int startRow = CommonInstance.DEFAULT_EXCEL_STARTWROW;
        Workbook workbook = null;
        String originalFilename = file.getOriginalFilename();
        try {
            if (originalFilename.endsWith(".xls")) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else if (originalFilename.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("error when analyzing File:" + originalFilename);
        }

        // 获取第一个sheet
        Sheet sheet = workbook.getSheet(sheetName);
        // getLastRowNum，获取最后一行的行标
        log.debug(String.valueOf(sheet.getLastRowNum()));
        for (int j = startRow; j < sheet.getLastRowNum() + 1; j++) {
            Row row = sheet.getRow(j);
            String col1 = "";
            String col2 = "";
            String col3 = "";
            String col4 = "";
            if (null != row.getCell(0) && !"".equals(row.getCell(0))) {
                col1 = row.getCell(0).getStringCellValue();
            }
            if (null != row.getCell(1) && !"".equals(row.getCell(1))) {
                col2 = row.getCell(1).getStringCellValue();
            }
            if (null != row.getCell(2) && !"".equals(row.getCell(2))) {
                col3 = row.getCell(2).getStringCellValue();
            }
            if (null != row.getCell(3) && !"".equals(row.getCell(3))) {
                col4 = row.getCell(3).getStringCellValue();
            }

            ColumnMappingModel model = new ColumnMappingModel();
            /**源，源字段名，目标字段名，目标表*/
            if ("".equals(col1)){
                res.put("result", 0);
                res.put("msg", "failed importing Excel,cause: colum 源 is Empty!");
                return res.toJSONString();
            }
            model.setSource(col1);
            if ("".equals(col2)){
                res.put("result", 0);
                res.put("msg", "failed importing Excel,cause: colum 源字段名 is Empty!");
                return res.toJSONString();
            }
            model.setSourceColumnName(col2);
            if ("".equals(col3)){
                res.put("result", 0);
                res.put("msg", "failed importing Excel,cause: colum 目标字段名 is Empty!");
                return res.toJSONString();
            }
            model.setTargetColumnName(col3);
            if ("".equals(col4)){
                res.put("result",0);
                res.put("msg","failed importing Excel,cause: colum 目标表 is Empty!");
                return res.toJSONString();
            }
            List<ColumnMappingModel> listInfoByTargetTableName = getListInfoByTargetTableName(col4);
            if (listInfoByTargetTableName.size()>0){
                res.put("result",0);
                res.put("msg","failed importing Excel,cause: table:"+col4+" already imported,please delete first!");
                return res.toJSONString();
            }

            model.setTargetTable(col4);

            modelList.add(model);
        }
        if (modelList.size() == 0) {
            log.error("data content is null!");
            res.put("faild", "faild imported file" + originalFilename + ":data content is null!");
            return res.toJSONString();
        }
        int i = columnMappingService.addList(modelList);
        if (i > 0) {
            res.put("success", "successfully imported file" + originalFilename);
            res.put("count", modelList.size());
            return res.toJSONString();
        } else {
            //入库
            res.put("faild", "faild imported file" + originalFilename);
            res.put("count", modelList.size());
            return res.toJSONString();
        }
    }

    @Override
    public List<ColumnMappingModel> getListInfoByTargetTableName(String targetTbName) {
        return columnMappingService.getListInfoByTargetTableName(targetTbName);
    }

    @Override
    public List<ColumnMappingModel> getListInfoBySourceName(String sourceName) {
        return columnMappingService.getListInfoBySource(sourceName);
    }

    @Override
    public List<ColumnMappingModel> getListInfoByTargetColumnName(String targetColumnName) {
        return columnMappingService.getListInfoByTargetColumnName(targetColumnName);
    }

    @Override
    public Map<String, String> getMapInfoByTargetTableName(String targetTableName) {
        return columnMappingService.getColumnMappingByTargetTableName(targetTableName);
    }

    @Override
    public int deleteById(int id) {
        return columnMappingService.deleteById(id);
    }

    @Override
    public int deleteByTargetTbName(String targetTbName) {
        return columnMappingService.deleteByTargetTbName(targetTbName);
    }

    @Override
    public int add(String targetTable, String source, String sourceColumnName, String targetColumnName) {
        ColumnMappingModel columnMappingModel = new ColumnMappingModel();
        columnMappingModel.setTargetTable(targetTable);
        columnMappingModel.setSource(source);
        columnMappingModel.setSourceColumnName(sourceColumnName);
        columnMappingModel.setTargetColumnName(targetColumnName);
        return columnMappingService.add(columnMappingModel);
    }

    @Override
    public int updateById(int id, String targetTable, String source, String srcColumnName, String targetColumnName) {
        return columnMappingService.updateById(id, targetTable, source, srcColumnName, targetColumnName);
    }
}
