package com.cetc.cloud.datasynch.provider.service.impl;

import com.cetc.cloud.datasynch.api.model.DddColumnCommentModel;
import com.cetc.cloud.datasynch.api.model.DddTableCommentModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * PackageName:   com.cetc.cloud.datasynch.provider.service.impl
 * projectName:   dataSyncher
 * Description:   luolinjie 补充
 * Creator:     by luolinjie
 * Create_Date: 2018/11/6 20:28
 * Updater:     by luolinjie
 * Update_Date: 2018/11/6
 * Update_Description: luolinjie 补充
 **/
@Service
@Slf4j
public class CommentManageService {
    @Autowired
    @Qualifier("readOnlyJdbcTemplate")
    private JdbcTemplate primaryJdbcTemplate;

    @Value("${spring.read-only-datasource.username}")
    private String userName;

    public List<DddTableCommentModel> addTableCommentList(List<DddTableCommentModel> modelList) {
        List<DddTableCommentModel> resList = new ArrayList<DddTableCommentModel>();
        int successCount = 0;
        int failCount = 0;
        for (DddTableCommentModel model : modelList) {
            String SQL = "COMMENT ON TABLE \"" + userName + "\".\"" + model.getTableName() + "\" " +
                    "IS\t'" + model.getTableComment() + "'";
            try {
                primaryJdbcTemplate.update(SQL);
            } catch (Exception e) {
                log.error(model.toString());
                resList.add(model);
                failCount++;
            }
            successCount++;
        }
        log.info("\nFinished! \nsuccessCount:" + successCount + "\nfailCount:" + failCount);
        return resList;
    }

    public List<DddColumnCommentModel> addColumnCommentList(List<DddColumnCommentModel> modelList) {

        List<DddColumnCommentModel> resList = new ArrayList<DddColumnCommentModel>();
        int successCount = 0;
        int failCount = 0;
        for (DddColumnCommentModel model : modelList) {

            String SQL = "COMMENT ON COLUMN \"" + userName + "\".\"" + model.getTableName() + "\".\"" + model.getColumnName() + "\" " +
                    "IS '" + model.getColumnComment() + "'";

            try {
                primaryJdbcTemplate.update(SQL);
            } catch (Exception e) {
                log.error(model.toString());
                e.printStackTrace();
                resList.add(model);
                failCount++;
            }
            successCount++;
        }
        return resList;
    }
}
