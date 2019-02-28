package com.cetc.cloud.datasynch.provider.template;

import com.cetc.cloud.datasynch.api.model.SanxiaoModel;
import com.cetc.cloud.datasynch.provider.util.ListUtil;
import com.cetc.cloud.datasynch.provider.service.impl.DbOperateService;
import com.cetc.cloud.datasynch.provider.service.impl.DbThirdOperateService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.*;

/**
 * 三小场所数据特性：每天只是少量的三小场所数量进行状态变更、新增等
 * 新增：old ID集合中不包含 ----> insert操作
 * 变更：old ID集合中包含newId，但是status状态不一致 ----> update操作
 * <p>
 * Description：创建定时 执行计算 实例
 * Created by luolinjie on 2018/10/10.
 */
@Slf4j
@NoArgsConstructor
public class RefreshSanxiaoListRunnable implements OuterJobRunnableTemplate {

    private DbThirdOperateService dbThirdOperateService;
    private DbOperateService dbOperateService;

    public RefreshSanxiaoListRunnable(DbThirdOperateService dbOperateService_zhft, DbOperateService dbOperateService) {
        this.dbThirdOperateService = dbOperateService_zhft;
        this.dbOperateService = dbOperateService;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("refreshSanxiao");
        //1.通过数据库读取三小场所数据_new
//        String sql = "select ID,STATUS from T_KZ_SMALL_PLACE";
        String sql = "select ID,STATUS,LDDM,YHCOUNT from FBPT.T_KZ_SMALL_PLACE";
        List<List> sanxiaoList_new = null;
        try {
            sanxiaoList_new = dbThirdOperateService.oracleQueryList_4member(sql);
            if (sanxiaoList_new.size() == 0) {
                log.error("Empty: sanxiaoList_new");
                return;
            } else {
                log.info("\n>> received:sanxiaoList_new.Size():" + sanxiaoList_new.size());
            }
        } catch (SQLException e) {
            log.error("Error SQL:" + sql);
            e.printStackTrace();
        }
        //2.1 组成newModels集合
        HashSet<String> sanxiaoIds_new = new HashSet<>();
        HashSet<SanxiaoModel> sanxiaoModelset_new = new HashSet<>();//容器存储

        Iterator<List> iterator2 = sanxiaoList_new.iterator();
        while (iterator2.hasNext()) {
            List next = iterator2.next();
            SanxiaoModel sanxiaoModel = new SanxiaoModel((String) next.get(0), (String) next.get(1), (String) next.get(2), (String) next.get(3));
            sanxiaoModelset_new.add(sanxiaoModel);
            sanxiaoIds_new.add((String) next.get(0));
        }

        //2.获取 ---old集合
        String sql0 = "select ID,STATUS,LDDM,YHCOUNT from BLK_SANXIAO_PLACE";
        List<List> sanxiaoList_old = null;
        try {
            sanxiaoList_old = dbOperateService.oracleQueryList_4member(sql0);
            if (sanxiaoList_old.size() == 0) {
                log.info("Empty: sanxiaoList_old");
            }
        } catch (SQLException e) {
            log.error("Error SQL:" + sql0);
            e.printStackTrace();
        }

        //2.1 组成oldModels集合
        HashSet<SanxiaoModel> sanxiaoModelset_old = new HashSet<>();//容器存储
        HashSet<String> sanxiaoIds_old = new HashSet<>();
        Iterator<List> iterator1 = sanxiaoList_old.iterator();
        while (iterator1.hasNext()) {
            List next = iterator1.next();
            SanxiaoModel sanxiaoModel = new SanxiaoModel((String) next.get(0), (String) next.get(1), (String) next.get(2), (String) next.get(3));
            sanxiaoModelset_old.add(sanxiaoModel);
            sanxiaoIds_old.add((String) next.get(0));
        }

        //3.遍历，insert/update/delete 三小场所中的字段
        //3.1 筛选出需要insert的id
        HashSet<String> idsToInsert = new HashSet<>();
        idsToInsert.clear();
        idsToInsert.addAll(sanxiaoIds_new);
        idsToInsert.removeAll(sanxiaoIds_old);
        Integer[] insertCount = insertSanxiaoByIds(idsToInsert);
        if (insertCount[0] > 0) {
            log.info("Inserted new sanxiaoPlace:" + insertCount[0]);
        }
        //3.2.重新获取 --- old集合
        List<List> sanxiaoList_old2 = null;
        try {
            sanxiaoList_old2 = dbOperateService.oracleQueryList_4member(sql0);
            if (sanxiaoList_old2.size() == 0) {
                log.info("Empty: sanxiaoList_old");
            } else {
                log.info("Get SanxiaoPlace.Size():" + sanxiaoList_old2.size());
            }
        } catch (SQLException e) {
            log.error("Error SQL:" + sql0);
            e.printStackTrace();
        }
        HashSet<SanxiaoModel> sanxiaoModelset_old2 = new HashSet<>();//容器存储
        HashSet<String> sanxiaoIds_old2 = new HashSet<>();
        Iterator<List> iterator3 = sanxiaoList_old2.iterator();
        while (iterator3.hasNext()) {
            List next = iterator3.next();
            SanxiaoModel sanxiaoModel = new SanxiaoModel((String) next.get(0), (String) next.get(1), (String) next.get(2), (String) next.get(3));
            sanxiaoModelset_old2.add(sanxiaoModel);
            sanxiaoIds_old2.add((String) next.get(0));
        }
        //3.3.对比，获取需要更新的ID集合SanxiaoModel
        List<SanxiaoModel> listToUpdate = new ArrayList<SanxiaoModel>();
        for (SanxiaoModel newModel : sanxiaoModelset_new) {
            if (!sanxiaoModelset_old2.contains(newModel)) {//新的未包含在old集合中，进行更新
                listToUpdate.add(newModel);
            }
        }
        //3.4 update
        //拼接batchSQL
        List<String> sanxiaoStatusBatchSQL = getSanxiaoStatusBatchUpdateSQL(listToUpdate);
        int[] updateRes = dbOperateService.oracleBatchSql(sanxiaoStatusBatchSQL);
        log.info("\n>> Finished Update BLK_SANXIAO_PLACE.STATUS success count:" + updateRes[0] + ",fail count:" + updateRes[1]);
        //4.3 筛选出需要delete的id
        List<String> idsToDelete = new ArrayList<>();
        idsToDelete.clear();
        idsToDelete.addAll(sanxiaoIds_old2);
        idsToDelete.removeAll(sanxiaoIds_new);
        List<String> sanxiaoBatchDeleteSQL = getSanxiaoStatusBatchDeleteSQL(idsToDelete);
        int[] deleteRes = dbOperateService.oracleBatchSql(sanxiaoBatchDeleteSQL);
        log.info("\n>> Finished Delete Old data:BLK_SANXIAO_PLACE\n success count:" + deleteRes[0] + ",fail count:" + deleteRes[1]);
        log.info("\n>> Finished ! RefreshSanXiaoListRunnable()\nTotal："+sanxiaoList_new.size()+"\nUpdated new:"+listToUpdate.size()+"\nDeleted old:"+idsToDelete.size());
    }

    /**
     * 拼接更新三小场所的SQL
     *
     * @param modelList
     * @return
     */
    private List<String> getSanxiaoStatusBatchUpdateSQL(List<SanxiaoModel> modelList) {

        Iterator<SanxiaoModel> iterator = modelList.iterator();

        List<String> sqlList = new ArrayList<String>();
        while (iterator.hasNext()) {
            SanxiaoModel next = iterator.next();
            String sql = "update BLK_SANXIAO_PLACE set "
                    + "STATUS='" + next.getStatus() + "',"
                    + "LDDM='" + next.getLddm()+ "',"
                    + "YHCOUNT='" + next.getYhCount()+ "'\n"
                    + " where id = '" + next.getId() + "'";
            sqlList.add(sql);
        }
        return sqlList;
    }

    private List<String> getSanxiaoStatusBatchDeleteSQL(List<String> modelList) {

        Iterator<String> iterator = modelList.iterator();

        List<String> sqlList = new ArrayList<String>();
        while (iterator.hasNext()) {
            String next = iterator.next();
            String sql = "delete from BLK_SANXIAO_PLACE where id = '" + next + "'";
            sqlList.add(sql);
        }
        return sqlList;
    }

    /**
     * 优化：对insert做批处理
     *
     * @param idsToInsert
     * @return
     */
    private Integer[] insertSanxiaoByIds(HashSet<String> idsToInsert) {
        //1.拼接sql id
        int successCount = 0;
        int failCount = 0;
        HashSet<String> ids2Insert = new HashSet<>();
        for (String id : idsToInsert) {
            if (ids2Insert.size() != 0 && (ids2Insert.size() % 1000 == 0)) {
                List<HashMap> hashMaps = batchQuery(ids2Insert);
                int resCount[] = dbOperateService.batchInsertIntoTargetTableByTableName(hashMaps, "BLK_SANXIAO_PLACE");
                successCount += resCount[0];
                failCount += resCount[1];
                ids2Insert.clear();//完成一批insert后清空
            }
            ids2Insert.add(id);
        }
        //执行剩余的集合
        List<HashMap> hashMaps = batchQuery(ids2Insert);
        int resCount[] = dbOperateService.batchInsertIntoTargetTableByTableName(hashMaps, "BLK_SANXIAO_PLACE");
        successCount += resCount[0];
        failCount += resCount[1];
        return new Integer[]{successCount, failCount};
    }

    public List<HashMap> batchQuery(Collection<String> targets) {
        if (targets.size() == 0) {
            return null;
        }
        String ids = ListUtil.toStringNoBracketWithSingleQuot(targets);
//        String querySql = "select * from T_KZ_SMALL_PLACE where ID IN(" + ids + ")";
        String querySql = "select * from FBPT.T_KZ_SMALL_PLACE where ID IN(" + ids + ")";
        log.info("ids.size():" + targets.size());
        List<HashMap> hashMaps = null;
        try {
            hashMaps = dbThirdOperateService.oracleQuerySql(querySql);
        } catch (SQLException e) {
            log.error("SQL ERROR: " + querySql);
            e.printStackTrace();
        }
        return hashMaps;
    }

}


