package com.cetc.cloud.datasynch.provider.controller;

import com.alibaba.fastjson.JSONObject;
import com.cetc.cloud.datasynch.api.model.ColumnMappingModel;
import com.cetc.cloud.datasynch.api.service.ColumnMappingRemoteService;
import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import com.cetc.cloud.datasynch.provider.service.impl.ColumnMappingService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ColumnMappingController implements ColumnMappingRemoteService {

    @Autowired
    ColumnMappingService columnMappingService;
    Logger logger = LoggerFactory.getLogger(ColumnMappingController.class);

    @Override
    public String importExcelIntoDB(MultipartFile file, String Data_StartRow_Num) {

        JSONObject res = new JSONObject();

        // 解析Excel，生成List<Model>
        List<ColumnMappingModel> modelList = new ArrayList<ColumnMappingModel>();
        int startRow;
        if (null == Data_StartRow_Num || "".equals(Data_StartRow_Num)) {
            startRow = CommonInstance.DEFAULT_EXCEL_STARTWROW;
        } else {
            startRow = Integer.valueOf(Data_StartRow_Num) - 1;
        }
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
            logger.error("error when analyzing File:" + originalFilename);
        }

        // 获取第一个sheet
        Sheet sheet = workbook.getSheetAt(0);
        // getLastRowNum，获取最后一行的行标
        logger.debug(String.valueOf(sheet.getLastRowNum()));
        for (int j = startRow; j < sheet.getLastRowNum(); j++) {
            Row row = sheet.getRow(j);
            if (null == row.getCell(0).getStringCellValue() || "".equals(row.getCell(0).getStringCellValue())) {
                break;
            }

            ColumnMappingModel model = new ColumnMappingModel();
            /**源，源字段名，目标字段名，目标表*/
            model.setSource(row.getCell(0).getStringCellValue());
            model.setSourceColumnName(row.getCell(1).getStringCellValue());
            model.setTargetColumnName(row.getCell(2).getStringCellValue());
            model.setTargetTable(row.getCell(3).getStringCellValue());

            modelList.add(model);
        }
        int i = columnMappingService.addList(modelList);
        if (i > 0) {
            res.put("success", "successfully imported file" + originalFilename);
            return res.toJSONString();
        } else {
            //入库
            res.put("faild", "faild imported file" + originalFilename);
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
    public int add(String targetTable,String source,String sourceColumnName,String targetColumnName) {
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
