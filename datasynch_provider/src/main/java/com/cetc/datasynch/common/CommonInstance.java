package com.cetc.datasynch.common;

/**
 * Description：
 * Created by luolinjie on 2018/10/9.
 */
public interface CommonInstance {
    /**
     * DataX 的安装路径
     */
    public static final String DataXInstallPath = "D:\\Software\\install\\Environment\\DataX\\datax\\bin";

    /**
     * 数据接入方式：
     *      0：数据库方式
     *      1：接口方式
     */
    public static final int TYPE_DB = 0;
    public static final int TYPE_INTERFACE = 1;

    /**
     * 接口开启状态：
     *      0：关闭
     *      1：开启
     */
    public static final int DISABLED = 0;
    public static final int ENABLED = 1;

    /**
     *  默认分页大小
     */
    public static final int DefaultPageSize  = 100;


    /**
     * 接口方式分页参数名称
     */
    String PAGE_NUM_NAME = "pageSize";
    String PAGE_SIZE_NAME = "pageNum";
}
