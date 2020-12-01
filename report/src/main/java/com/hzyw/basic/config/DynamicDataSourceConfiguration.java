package com.hzyw.basic.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.hzyw.basic.datasource.DataSourceKey;
import com.hzyw.basic.datasource.DynamicRoutingDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@MapperScan(basePackages = "com.hzyw.basic.dao")
@Configuration
public class DynamicDataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.unions")
    public DataSource dbUnions() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.light")
    public DataSource dbLight() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.screen")
    public DataSource dbScreen() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.camera")
    public DataSource dbCamera() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.wifi")
    public DataSource dbWifi() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.pole")
    public DataSource dbPole() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.sensor")
    public DataSource dbSensor() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.report")
    public DataSource dbReport() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.charger")
    public DataSource dbCharger() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.radar")
    public DataSource dbRadar() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public DataSource dynamicDataSource() {
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
        dataSource.setDefaultTargetDataSource(dbReport());
        Map<Object, Object> dataSourceMap = new HashMap<>(20);
        dataSourceMap.put(DataSourceKey.DB_LIGHT, dbLight());
        dataSourceMap.put(DataSourceKey.DB_SCREEN, dbScreen());
        dataSourceMap.put(DataSourceKey.DB_CAMERA, dbCamera());
        dataSourceMap.put(DataSourceKey.DB_WIFI, dbWifi());
        dataSourceMap.put(DataSourceKey.DB_UNIONS, dbUnions());
        dataSourceMap.put(DataSourceKey.DB_SENSOR, dbSensor());
        dataSourceMap.put(DataSourceKey.DB_POLE, dbPole());
        dataSourceMap.put(DataSourceKey.DB_CHARGER, dbCharger());
        dataSourceMap.put(DataSourceKey.DB_RADAR,dbRadar());
        dataSource.setTargetDataSources(dataSourceMap);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        //此处设置为了解决找不到mapper文件的问题
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }

    /**
     * 事务管理
     *
     * @return 事务管理实例
     */
    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }


}
