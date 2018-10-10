package com.cetc.datasynch.middleware;


/**
 * SQL 分页查询语句创建器
 * Created by llj on 2018/10/10.
 */
public class SQLCreator {

    /*
        pageNum 从1开始
        pageSize 为固定值100

        STARTROW 从1开始
        ENDROW 包含在内

        pageNum=3 pageSize=100 时
        StartRow = 101  = 1+pagesize*(pageNum-1)
        EndRow = 200   = StartRow+PageSize-1 = pagesize*pageNum
     */
    public static String createSQLByTbNameAndRowParam(String tableName, int pageNum, int pageSize) {

        String originSQL = "SELECT *\n" +
                "FROM (\n" +
                " SELECT @TABLE_NAME.*, rownum rownum_\n" +
                " FROM @TABLE_NAME\n" +
                " WHERE rownum <= @ENDROW)\n" +
                "WHERE rownum_ >= @STARTROW";

        if (pageNum < 1 || pageSize < 1) {
            return "param error！pageNum and pageSize is not acceptable！";
        }

        //通过pagenum和pagesize计算StartRow和EndRow
        int startRow = 1 + pageSize * (pageNum - 1);
        int endRow = pageSize * pageNum;

        String SQL = originSQL.replaceAll("@TABLE_NAME", tableName);
        String SQL1 = SQL.replaceAll("@STARTROW", String.valueOf(startRow));
        String SQL2 = SQL1.replaceAll("@ENDROW", String.valueOf(endRow));

        return SQL2;
    }
}
