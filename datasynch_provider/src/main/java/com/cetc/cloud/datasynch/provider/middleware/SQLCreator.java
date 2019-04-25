package com.cetc.cloud.datasynch.provider.middleware;


/**
 * SQL 分页查询语句创建器
 * Created by llj on 2018/10/10.
 */
public class SQLCreator {

    /*
        pageNum 每次任务开始时从1开始,每执行一次自增1
        pageSize 为固定值100

        STARTROW 从1开始
        ENDROW 包含在内
     */
    public static String createSQLByTbNameAndRowParam(String tableName, int startRow, int endRow, String orderByColumn) {
        if (startRow>endRow){
            return null;
        }

        String sql = "select *\n" +
                " from (select row_.*, rownum rownum_\n" +
                "       from (select *\n" +
                "             from "+tableName+"\n" +
                "             order by " + orderByColumn + " asc) row_\n" +
                "       where rownum <= " + endRow + ")\n" +
                "where rownum_ >= " + startRow;

        return sql;
    }
}
