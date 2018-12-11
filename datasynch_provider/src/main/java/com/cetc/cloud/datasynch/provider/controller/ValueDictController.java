package com.cetc.cloud.datasynch.provider.controller;

import com.alibaba.fastjson.JSONObject;
import com.cetc.cloud.datasynch.api.model.ValueDictModel;
import com.cetc.cloud.datasynch.api.service.ValueDictRemoteService;
import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import com.cetc.cloud.datasynch.provider.service.impl.ValueDictService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.controller
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/12/5 15:22
 * Updater:     by luolinjie
 * Update_Date: 2018/12/5
 * Update_Description: luolinjie 补充
 **/
@RestController
public class ValueDictController implements ValueDictRemoteService {

    Logger logger = LoggerFactory.getLogger(ValueDictController.class);

    @Autowired
    ValueDictService valueDictService;
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate primaryJdbcTemplate;


    @Override
    public String importExcel(MultipartFile file, String sheetName) {

        JSONObject res = new JSONObject();

        // 解析Excel，生成List<Model>
        List<ValueDictModel> modelList = new ArrayList<ValueDictModel>();
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
            logger.error("error when analyzing File:" + originalFilename);
        }

        // 获取第一个sheet
        Sheet sheet = workbook.getSheet(sheetName);
        // getLastRowNum，获取最后一行的行标
        logger.debug(String.valueOf(sheet.getLastRowNum()));
        for (int j = startRow; j < sheet.getLastRowNum() + 1; j++) {
            Row row = sheet.getRow(j);
            String col1 = "";
            String col2 = "";
            String col3 = "";
            String col4 = "";
            String col5 = "";
            String col6 = "";
            if (null != row.getCell(0) || "".equals(row.getCell(0))) {
                col1 = row.getCell(0).getStringCellValue();
            }
            if (null != row.getCell(1) || "".equals(row.getCell(1))) {
//                col2 = row.getCell(1).getStringCellValue();
                Cell cell1 = row.getCell(1);
                cell1.setCellType(Cell.CELL_TYPE_STRING);
                col2 = cell1.getStringCellValue();
            }
            if (null != row.getCell(2) || "".equals(row.getCell(2))) {
                int cellType = row.getCell(2).getCellType();
                if (cellType == Cell.CELL_TYPE_STRING) {
                    col3 = row.getCell(2).getStringCellValue();
                } else if (cellType == Cell.CELL_TYPE_NUMERIC) {
                    col3 = row.getCell(2).getNumericCellValue() + "";
                    if (col3.contains(".")) {
                        col3 = col3.split("\\.")[0];
                    }
                }
            }
            if (null != row.getCell(3) || "".equals(row.getCell(3))) {
                int cellType = row.getCell(3).getCellType();
                Cell cell3 = row.getCell(3);
                cell3.setCellType(Cell.CELL_TYPE_STRING);
                col4 = cell3.getStringCellValue();
            }
            if (null != row.getCell(4) || "".equals(row.getCell(3))) {
                col5 = row.getCell(4).getStringCellValue();
            }
            if (null != row.getCell(5) || "".equals(row.getCell(3))) {
                col6 = row.getCell(5).getStringCellValue();
            }

            ValueDictModel model = new ValueDictModel();
            /**源，源字段名，目标字段名，目标表*/
            model.setTableName(col1);
            model.setColumnName(col2);
            model.setCode(col3);
            model.setCodeInChinese(col4);
            model.setTbInChinese(col5);
            model.setColInChinese(col6);

            modelList.add(model);
        }
        if (modelList.size() == 0) {
            logger.error("data content is null!");
            res.put("faild", "faild imported file" + originalFilename + ":data content is null!");
            return res.toJSONString();
        }
        int i = valueDictService.addList(modelList);
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

    //todo 2.在futian项目中提供一个工具，在这里先做测试
    @Override
    public String getDictValue(String tableName, String columnName, String code) {

        String SQL = "select CODE_IN_CHINESE from DS_VALUE_DICT " +
                "where TABLE_NAME='" + tableName.toUpperCase() + "' " +
                "and COLUMN_NAME='" + columnName.toUpperCase() + "' " +
                "and CODE='" + code + "'";
        SqlRowSet rowSet = primaryJdbcTemplate.queryForRowSet(SQL);
        String codeInChinese = "";
        while (rowSet.next()){
            codeInChinese = rowSet.getString(1);
        }
        if (!"".equals(codeInChinese )) {
            return codeInChinese;
        } else {
            return null;
        }
    }

}
