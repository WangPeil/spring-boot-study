package com.example.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Author: peili.wang
 * @Date: 2020/3/22 22:00
 * @Description:
 */
@Configuration
public class DataSourceConfig {


    /**
     * 配置bean
     * @return
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.database1")
    public DataSourceProperties dataSources1() {
        return new DataSourceProperties();
    }

    @Bean("dataSource1")
    @Primary
    public DataSource dataSource1(@Qualifier("dataSources1")DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }
    /**
     * 配置bean
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.database2")
    public DataSourceProperties dataSources2() {
        return new DataSourceProperties();
    }

    @Bean("dataSource2")
    public DataSource dataSource2(@Qualifier("dataSources2")DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }


    @Bean("sqlSessionFactory1")
    @Primary
    public SqlSessionFactory sqlSessionFactory1(@Qualifier("dataSource1")DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResource("classpath:mapper/*.xml"));
        return sqlSessionFactory.getObject();
    }

    @Bean
    @Primary
    public DataSourceTransactionManager dataSourceTransactionManager1(@Qualifier("dataSource1")DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Bean("sqlSessionFactory2")
    public SqlSessionFactory sqlSessionFactory2(@Qualifier("dataSource2") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResource("classpath:mapper/*.xml"));
        return sqlSessionFactory.getObject();
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager2(@Qualifier("dataSource2") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}