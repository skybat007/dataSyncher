package com.cetc.cloud.datasynch.provider.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @Package: com.cetc.cloud.pandu.pandu.gateway.config
 * @Project: pandu-gateway
 * @Creator: huangzezhou
 * @Create_Date: 2018/12/16 11:50
 * @Updater: huangzezhou
 * @Update_Date: 2018/12/16 11:50
 * @Update_Description: huangzezhou 补充
 * @Description: //mybatis配置
 **/
@Configuration
@MapperScan("com.cetc.cloud.datasynch.provider.mapper")
public class MybatisConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean(name = "primaryJdbcTemplate")
    @Primary
    public JdbcTemplate primaryJdbcTemplate(@Qualifier("primaryDataSource")DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.master")
    @Primary
    public DataSource primaryDataSource() {
        DataSource dataSource = DataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean(name = "readOnlyJdbcTemplate")
    public JdbcTemplate readOnlyJdbcTemplate(@Qualifier("primaryDataSource")DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "readOnlyDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.readonly")
    public DataSource readOnlyDataSource() {
        DataSource dataSource = DataSourceBuilder.create().build();
        return dataSource;
    }
}
