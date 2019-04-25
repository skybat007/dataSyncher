package com.cetc.cloud.datasynch.api.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据字典-字段注释表:DDD_COLUMN_COMMENT
 * Created by llj on 2018/10/8.
 */
@Data
public class DddColumnCommentModel implements Serializable{
    private String tableName;
    private String columnName;
    private String columnComment;

}
