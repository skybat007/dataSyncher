package com.cetc.cloud.datasynch.api.service;

import com.cetc.cloud.datasynch.api.model.DddColumnCommentModel;
import com.cetc.cloud.datasynch.api.model.DddTableCommentModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * PackageName:   com.cetc.cloud.datasynch.api.service
 * projectName:   dataSyncher
 * Description:   业务表注释管理服务
 * Creator:     by luolinjie
 * Create_Date: 2018/10/25 17:37
 * Updater:     by luolinjie
 * Update_Date: 2018/10/25
 * Update_Description: luolinjie 补充
 **/
@Api(description = "业务表注释管理")
public interface CommentManageRemoteService {
    @RequestMapping(value = "/commentmanage/import/tableComment", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "批量导入带有注释的Excel文档-表注释", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sheetName", value = "sheet名称", required = true, dataType = "String", paramType = "query"),
    })
    List<DddTableCommentModel> importTableCommentExcel(MultipartFile file, String sheetName);

    @RequestMapping(value = "/commentmanage/import/columnComment", produces = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "批量导入带有注释的Excel文档-字段注释", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sheetName", value = "sheet名称", required = true, dataType = "String", paramType = "query"),
    })
    List<DddColumnCommentModel> importColumnCommentExcel(MultipartFile file, String sheetName);
    //todo 通过表名获取该表字段、字段注释、字段类型

    //todo 添加一条注释

    //todo 通过主键修改注释

}
