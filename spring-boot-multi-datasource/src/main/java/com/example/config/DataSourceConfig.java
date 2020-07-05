package com.example.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.properties.DataBase1Properties;
import com.example.properties.DataBase2Properties;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @Author: peili.wang
 * @Date: 2020/3/22 22:00
 * @Description:
 */
@Configuration
@Slf4j
public class DataSourceConfig {
    private DataBase1Properties dataBase1Properties;
    private DataBase2Properties dataBase2Properties;

    @Autowired
    public DataSourceConfig(DataBase1Properties dataBase1Properties, DataBase2Properties dataBase2Properties) {
        this.dataBase1Properties = dataBase1Properties;
        this.dataBase2Properties = dataBase2Properties;
    }

    /**
     * 获取DataSource bean
     *
     * @return bean
     */
    @Bean(name = "dataBase1DataSource")
    @Primary
    public DataSource dataBase1DataSource() {
        log.info("==============dataBase1DataSource初始化=================");
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(dataBase1Properties.getDriverClassName());
        dataSource.setUrl(dataBase1Properties.getUrl());
        dataSource.setUsername(dataBase1Properties.getUsername());
        dataSource.setPassword(dataBase1Properties.getPassword());
        return dataSource;
    }

    @Bean(name = "dataBase2DataSource")
    public DataSource dataBase2DataSource() {
        log.info("==============dataBase2DataSource初始化=================");
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(dataBase2Properties.getDriverClassName());
        dataSource.setUrl(dataBase2Properties.getUrl());
        dataSource.setUsername(dataBase2Properties.getUsername());
        dataSource.setPassword(dataBase2Properties.getPassword());
        return dataSource;
    }

    @Bean(name = "dataBase1Factory")
    @Primary
    public SqlSessionFactoryBean dataBase1Factory(@Qualifier("dataBase1DataSource") DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }

    @Bean(name = "dataBase2Factory")
    public SqlSessionFactoryBean dataBase2Factory(@Qualifier("dataBase2DataSource") DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }
}
