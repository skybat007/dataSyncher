package com.cetc.cloud.datasynch.provider.core.tools;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.core.util.tools
 * projectName:   dataSyncher
 * Description:   Excel的导出功能
 * Creator:     by luolinjie
 * Create_Date: 2018/11/14 14:35
 * Updater:     by luolinjie
 * Update_Date: 2018/11/14
 * Update_Description: luolinjie 补充
 **/
@Slf4j
public class ExcelTool {

    private static String exportFilePath = "C:\\Users\\Administrator\\Desktop\\31project_tableStructure\\";

    public static HashMap<String, String> exportExcel(List<List<String>> data, List<String> excelHead, String sheetName) throws Exception {
        HashMap<String, String> res = new HashMap<String, String>();
        String formatTimeStr = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        formatTimeStr = format.format(Calendar.getInstance().getTime());
        exportFilePath = "C:\\Users\\Administrator\\Desktop\\31project_tableStructure\\" + sheetName + formatTimeStr;
        File folder = new File(exportFilePath);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                log.info("create folder ： " + exportFilePath + " success!");
            }
        }

        //创建一个xls文件
        File file = new File(exportFilePath + "/" + sheetName + formatTimeStr + ".xls");
        if (!file.exists()) {
            if (file.createNewFile()){
                log.info("created file:"+file.getAbsolutePath());
            }
        } else {
            log.error("file already exists!");
            res.put("code", "-1");
            res.put("msg", "file already exists!");
            return res;
        }

        //设置Excel默认输出样式
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        sheet.setDefaultColumnWidth(15);
        int rowCounter = 0;
        Row rows0 = sheet.createRow(rowCounter++);
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        short o = 200;
        font.setFontHeight(o);
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);

        for (int i = 0; i < excelHead.size(); i++) {
            Cell cell0 = rows0.createCell(i);
            cell0.setCellStyle(cellStyle);
            cell0.setCellValue(excelHead.get(i));
        }

        try {
            for (List<String> rowData : data) {
                Row row = sheet.createRow(rowCounter++);
                for (int j = 0; j < rowData.size(); j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(rowData.get(j));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileOutputStream xlsStream = new FileOutputStream(file);
            workbook.write(xlsStream);
            xlsStream.close();
        }
        res.put("code", "0");
        res.put("msg", "success,file path is:" + file.getAbsolutePath());
        return res;
    }
}
