package com.cetc.datasynch.api.service;

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
    String initSQL() throws SQLException;

}
