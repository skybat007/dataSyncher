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
    public static String createSQLByTbNameAndRowParam(String tableName,int startRow, int endRow, String OrderByColumn) {


        String originSQL = "SELECT *\n" +
                "FROM (\n" +
                " SELECT \"@TABLE_NAME\".*, rownum rownum_\n" +
                " FROM \"@TABLE_NAME\"\n" +
                " WHERE rownum <= @ENDROW\n" +
                " ORDER BY \"" + OrderByColumn + "\" ASC\n" +
                " )WHERE rownum_ >= @STARTROW";



        String SQL = originSQL.replaceAll("@TABLE_NAME", tableName);
        String SQL1 = SQL.replaceAll("@STARTROW", String.valueOf(startRow));
        String SQL2 = SQL1.replaceAll("@ENDROW", String.valueOf(endRow));

        return SQL2;
    }
}
