package com.cetc.cloud.datasynch.api.service;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.SQLException;


/**
 * Description：初始化，用于初次启动前的准备工作
 * Created by luolinjie on 2018/10/20.
 */
public interface InitialRemoteService {

    /**
     * 全局初始化，创建表、触发器、序列等一系列操作
     * @return  success-成功
     *          fail-失败
     */
    @RequestMapping(value = "/global/init", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "executeDBInit", notes = "初次启动程序,必须执行数据库初始化", produces = "application/json")
    String executeDBInit() throws SQLException;

}
