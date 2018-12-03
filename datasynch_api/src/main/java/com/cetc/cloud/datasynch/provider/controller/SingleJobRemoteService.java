package com.cetc.cloud.datasynch.provider.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.controller
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/11/28 16:30
 * Updater:     by luolinjie
 * Update_Date: 2018/11/28
 * Update_Description: luolinjie 补充
 **/
public interface SingleJobRemoteService {

    @RequestMapping(value = "/singleJob/truncateTable", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "清空三小场所表并重新拉取", notes = "备份+清空源表", produces = "application/json")
    void truncateTable();

    @RequestMapping(value = "/singleJob/calculateHasTroubleSanXiao", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "计算有隐患的三小场所", notes = "HasTrouble字段，有记录的设置为1", produces = "application/json")
    void calculateHasTroubleSanXiao() throws SQLException;


    @RequestMapping(value = "/singleJob/insertXinfangData", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "手动插入单日信访数据", notes = "date示例：2018-11-29", produces = "application/json")
    void insertXinfangDataToday() throws SQLException;

    @RequestMapping(value = "/singleJob/insertXinfangHistoryData", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "手动插入历史信访数据", notes = "date示例：2018-01-01  2018-12-30", produces = "application/json")
    void insertXinfangHistoryData(String min, String max) throws SQLException;
}
