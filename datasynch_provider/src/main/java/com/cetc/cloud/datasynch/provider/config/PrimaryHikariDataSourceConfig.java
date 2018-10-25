package com.cetc.cloud.datasynch.provider.config;
/**********************************************************************
 * Copyright (c) 2018 CETC Company
 * 中电科新型智慧城市研究院有限公司版权所有
 * <p>
 * PROPRIETARY RIGHTS of CETC Company are involved in the
 * subject matter of this material. All manufacturing, reproduction, use,
 * and sales rights pertaining to this subject matter are governed by the
 * license agreement. The recipient of this software implicitly accepts
 * the terms of the license.
 * 本软件文档资料是中电科新型智慧城市研究院有限公司的资产，任何人士阅读和
 * 使用本资料必须获得相应的书面授权，承担保密责任和接受相应的法律约束。
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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Package: com.cetc.cloud.db_operate.provider.config
 * @Project: chart_data
 * @Description:
 * @Creator: huangzezhou
 * @Create_Date: 2018/7/12 15:35
 * @Updater: huangzezhou
 * @Update_Date：2018/7/12 15:35
 * @Update_Description: huangzezhou 补充
 **/

@Configuration
@MapperScan(basePackages = "com.cetc.cloud.datasynch.provider.mapper", sqlSessionTemplateRef  = "sqlSessionTemplatePrimary")
public class PrimaryHikariDataSourceConfig {



    @Bean(name = "primaryDataSource")
    @ConfigurationProperties(prefix = "spring.primary-datasource")
    @Primary
    public DataSource primaryDataSource() {
        DataSource dataSource = DataSourceBuilder.create().build();
        System.out.println(dataSource);
        return dataSource;
    }

    @Bean(name="sqlSessionFactoryPrimary")
    public SqlSessionFactory sqlSessionFactoryPrimary() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(primaryDataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath:com/cetc/cloud/datasynch/provider/mapper/*Mapper.xml"));
        return factoryBean.getObject();
    }


    @Bean(name="sqlSessionTemplatePrimary")
    @Primary
    public SqlSessionTemplate sqlSessionTemplatePrimary() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryPrimary());
        return template;
    }


    @Bean(name = "primaryTransactionManager")
    @Primary
    public DataSourceTransactionManager primaryTransactionManager(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
