package com.cetc.cloud.datasynch.provider.controller;

import com.cetc.cloud.datasynch.api.model.DddColumnCommentModel;
import com.cetc.cloud.datasynch.api.model.DddTableCommentModel;
import com.cetc.cloud.datasynch.api.service.CommentManageRemoteService;
import com.cetc.cloud.datasynch.provider.common.CommonInstance;
import com.cetc.cloud.datasynch.provider.service.impl.CommentManageService;
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

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.controller
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/11/6 20:21
 * Updater:     by luolinjie
 * Update_Date: 2018/11/6
 * Update_Description: luolinjie 补充
 **/
@RestController
@Slf4j
public class CommentManageController implements CommentManageRemoteService {

    @Autowired
    CommentManageService commentManageService;


    @Override
    public List<DddTableCommentModel> importTableCommentExcel(MultipartFile file, String sheetName) {

        // 解析Excel，生成List<Model>
        List<DddTableCommentModel> modelList = new ArrayList<DddTableCommentModel>();
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

        // 根据sheet名称获取sheet
        Sheet sheet = workbook.getSheet(sheetName);
        // getLastRowNum，获取最后一行的行标
        log.debug(String.valueOf(sheet.getLastRowNum()));
        for (int j = startRow; j <= sheet.getLastRowNum(); j++) {
            Row row = sheet.getRow(j);
            String col1 = "";
            String col2 = "";
            if (null != row.getCell(0) || "".equals(row.getCell(0))) {
                col1 = row.getCell(0).getStringCellValue();
            }
            if (null != row.getCell(1) || "".equals(row.getCell(1))) {
                col2 = row.getCell(1).getStringCellValue();
            }

            DddTableCommentModel model = new DddTableCommentModel();
            /**源，源字段名，目标字段名，目标表*/
            model.setTableName(col1);
            model.setTableComment(col2);

            modelList.add(model);
        }
        List<DddTableCommentModel> successList = commentManageService.addTableCommentList(modelList);
        return successList;
    }

    @Override
    public List<DddColumnCommentModel> importColumnCommentExcel(MultipartFile file, String sheetName) {

        // 解析Excel，生成List<Model>
        List<DddColumnCommentModel> modelList = new ArrayList<DddColumnCommentModel>();
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

        // 根据sheet名称获取sheet
        Sheet sheet = workbook.getSheet(sheetName);
        // getLastRowNum，获取最后一行的行标
        log.debug(String.valueOf(sheet.getLastRowNum()));
        for (int j = startRow; j <= sheet.getLastRowNum(); j++) {
            Row row = sheet.getRow(j);
            String tableName = "";
            String columnName = "";
            String columnComment = "";
            if (null != row.getCell(0) || "".equals(row.getCell(0))) {
                tableName = row.getCell(0).getStringCellValue();
            }
            if (null != row.getCell(1) || "".equals(row.getCell(1))) {
                columnName = row.getCell(1).getStringCellValue();
            }

            if (null != row.getCell(2) || "".equals(row.getCell(2))) {
                columnComment = row.getCell(2).getStringCellValue();
            }

            DddColumnCommentModel model = new DddColumnCommentModel();
            /**源，源字段名，目标字段名，目标表*/
            model.setTableName(tableName);
            model.setColumnName(columnName);
            model.setColumnComment(columnComment);

            modelList.add(model);
        }
        List<DddColumnCommentModel> failedList = commentManageService.addColumnCommentList(modelList);
        return failedList;
    }
}
