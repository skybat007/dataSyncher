package com.cetc.cloud.datasynch.provider.template;

import com.cetc.cloud.datasynch.provider.controller.RePullTableController;
import com.cetc.cloud.datasynch.provider.service.impl.DbOperateService;
import com.cetc.cloud.datasynch.provider.service.impl.DbQueryService;
import com.cetc.cloud.datasynch.provider.service.impl.HttpOperateService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;

/**
 * Description：创建定时 执行计算 实例
 * Created by luolinjie on 2018/10/10.
 */
@Slf4j
@NoArgsConstructor
public class RepullSanxiaoListRunnable implements OuterJobRunnableTemplate {

    private RePullTableController rePullTableController;

    public RepullSanxiaoListRunnable( RePullTableController rePullTableController) {
        this.rePullTableController = rePullTableController;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("repullSanxiao");
        //todo 执行计算,输出匹配的三小场所id
        log.info("clearAndPullAgainTableByTableName:BLK_SANXIAO_PLACE");
        rePullTableController.clearAndPullAgainTableByTableName("BLK_SANXIAO_PLACE");
    }


}


