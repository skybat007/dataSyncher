package com.cetc.cloud.datasynch.api.model;
/**********************************************************************
 *
 * Copyright (c) 2017 CETC Company                                       
 * 中电科新型智慧城市研究院有限公司版权所有                                           
 *
 * PROPRIETARY RIGHTS of CETC Company are involved in the                
 * subject matter of this material. All manufacturing, reproduction, use, 
 * and sales rights pertaining to this subject matter are governed by the 
 * license agreement. The recipient of this software implicitly accepts   
 * the terms of the license.                                              
 * 本软件文档资料是中电科新型智慧城市研究院有限公司的资产，任何人士阅读和                   
 * 使用本资料必须获得相应的书面授权，承担保密责任和接受相应的法律约束。     
 *
 *************************************************************************/

/**
 * 工程包名:   com.cetc.cloud.framework.api.model
 * 项目名称:   framework-plugin
 * 创建描述:   zhangliang 补充
 * Creator:     zhangliang
 * Create_Date: 2017/10/18
 * Updater:     zhangliang
 * Update_Date：2017/10/18
 * 更新描述:    zhangliang 补充
 **/
public class CetcCloudResult<T>
{
    /**
     * 结果执行状态码
     * */
    private int code;

    /**
     * 结果执行状态描述
     * */
    private String msg;

    /**
     * 结果执行返回对象实体
     * */
    private T data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
