package com.cetc.cloud.datasynch.provider.config;
/**********************************************************************
 *
 * Copyright (c) 2017 CETC Company                                       
 * 中电科新型智慧城市研究院有限公司版权所有                                           
 *
 * PROPRIETARY RIGHTS of CETC Company are involved in the                
 * subject matter of this material. All manufacturing, reproduction, use, 
 * and sales rights pertaining to this subject matter are governed by the 
 * license agreement. The recipient of this software implicitly accepts   
 * the terms of the license.                                              
 * 本软件文档资料是中电科新型智慧城市研究院有限公司的资产，任何人士阅读和                   
 * 使用本资料必须获得相应的书面授权，承担保密责任和接受相应的法律约束。     
 *
 *************************************************************************/

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 工程包名:    com.cetc.cloud.command.provider.config
 * 项目名称:    db-statement-plugin
 * 创建描述:    zhangliang 补充
 * Creator:     zhangliang
 * Create_Date: 2017/10/10
 * Updater:     zhangliang
 * Update_Date：2017/10/10
 * 更新描述:    zhangliang 补充
 **/
@Configuration
@MapperScan(basePackages = "com.cetc.cloud.datasynch.provider.mapper.output", sqlSessionTemplateRef  = "sqlSessionTemplateReadOnly")
public class ReadOnlyHiKariDataSourceConfig {

    @Bean(name = "readOnlyDatasource")
    @ConfigurationProperties(prefix = "spring.read-only-datasource")
    public DataSource readOnlyDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "readOnlyJdbcTemplate")
    public JdbcTemplate secondJdbcTemplate(@Qualifier("readOnlyDatasource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name="sqlSessionFactoryReadOnly")
    public SqlSessionFactory sqlSessionFactoryReadOnly() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(readOnlyDatasource());
        return factoryBean.getObject();
    }

    @Bean(name="sqlSessionTemplateReadOnly")
    public SqlSessionTemplate sqlSessionTemplateReadOnly() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryReadOnly());
        return template;
    }

    @Bean(name = "readOnlyTransactionManager")
    public DataSourceTransactionManager readOnlyTransactionManager(@Qualifier("readOnlyDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
