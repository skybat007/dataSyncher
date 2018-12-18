package com.cetc.cloud.datasynch.api.service;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.controller
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/12/5 10:25
 * Updater:     by luolinjie
 * Update_Date: 2018/12/5
 * Update_Description: luolinjie 补充
 **/
public interface SequenceManagerRemoteService {

    @RequestMapping(value = "/sequences/getList", produces = "application/json", method = RequestMethod.GET)
    @ApiOperation(value = "获取所有序列名称", notes = "", produces = "application/json")
    List getAllSequenceNameList();

    @RequestMapping(value = "/sequences/exactAll", produces = "application/json", method = RequestMethod.GET)
    @ApiOperation(value = "校准所有序列名称", notes = "", produces = "application/json")
    String exactAllSequences() throws IOException, SQLException, InterruptedException;

    @RequestMapping(value = "/sequences/exactSequence/byTableName", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "校准单个表名对应的序列值", notes = "", produces = "application/json")
    boolean exactSequenceByTbName(String tableName) throws IOException, SQLException, InterruptedException;


    @RequestMapping(value = "/sequences/reset/bySeqName", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "重置单个序列的值", notes = "", produces = "application/json")
    boolean resetSequenceBySequenceName(String seqName) throws IOException, SQLException, InterruptedException;
}
