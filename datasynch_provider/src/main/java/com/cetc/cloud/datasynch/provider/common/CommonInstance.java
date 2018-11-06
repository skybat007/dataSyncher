package com.cetc.cloud.datasynch.provider.common;

/**
 * Description： 全局常量定义
 * Created by luolinjie on 2018/10/9.
 */
public interface CommonInstance {
    /**
     * DataX 的安装路径
     */
    public static final String DataXInstallPath = "D:\\Software\\install\\Environment\\DataX\\datax\\bin";

    /**
     * 数据接入方式：
     * 0：数据库方式
     * 1：接口方式
     */
    public static final int TYPE_DB = 0;
    public static final int TYPE_INTERFACE = 1;

    /**
     * 接口开启状态：
     * 0：关闭
     * 1：开启
     */
    public static final int DISABLED = 0;
    public static final int ENABLED = 1;

    /**
     * 默认分页大小
     */
    public static final int DefaultPageSize = 100;


    /**
     * 接口方式分页参数名称
     */
    String PAGE_NUM_NAME = "PAR_pageSize";
    String PAGE_SIZE_NAME = "PAR_pageNum";

    /**
     * Excel默认数据起始行 --Excel中从0行开始记录
     */
    int DEFAULT_EXCEL_STARTWROW = 1;//默认需要Excel加表头

    String HTTP_RES_CODE = "code";
    String HTTP_RES_DATA = "data";

    /**
     * 是否做分页查询
     */
    int NO_PAGING = 0;
    int DO_PAGING = 1;

    /**
     * 数据库默认全局字段名称、值
     */
    String GLOBAL_COLNAME_INCRE_ID = "OBJECT_ID";
    String GLOBAL_COLNAME_CREATE_TIME = "CREATE_TIME";
    String GLOBAL_COLNAME_UPDATE_TIME = "UPDATE_TIME";
    String GLOBAL_COL_CREATE_TIME_DEFAULT_VAL = "SYSDATE";
    String GLOBAL_COL_UPDATE_TIME_DEFAULT_VAL = "SYSDATE";
}
