package com.ppdai.monitor.dao.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Author haijiang
 * @Description
 * @Date 2018/09/11
 **/
@Configuration
@tk.mybatis.spring.annotation.MapperScan(basePackages = DataSourceMaLSMonitorConfig.PACKAGE, sqlSessionFactoryRef = "malsmonitorSqlSessionFactory")
public class DataSourceMaLSMonitorConfig {

    static final String ALIAS_NAME = "malsmonitor";
    static final String PACKAGE = "com.ppdai.monitor.dao." + ALIAS_NAME;

    @Bean(ALIAS_NAME)
    @Qualifier(ALIAS_NAME)
    @ConfigurationProperties(prefix = "spring.datasource." + ALIAS_NAME)
    public DataSource createDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = ALIAS_NAME + "TransactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(createDataSource());
    }

    @Bean(name = ALIAS_NAME + "SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier(ALIAS_NAME) DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        return sessionFactory.getObject();
    }

}
