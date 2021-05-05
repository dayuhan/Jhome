package com.account.common.multipledatasources;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-06-13 18:46
 * 配置两套数据源可以实现多数据源 ， 该方案是实现动态数据源 通过注解解析
 **/

//@Configuration
//@MapperScan(basePackages="datasourc.demo.mapper", sqlSessionFactoryRef="sessionFactory")
public class DadaSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.remote")
    public DataSource remoteDateSource(){

        return DataSourceBuilder.create().build();
    }
    @Bean
    @ConfigurationProperties("spring.datasource.local")
    public DataSource localDateSource(){

        return DataSourceBuilder.create().build();
    }
    @Bean
    public DataSource dynamicDataSource(@Qualifier("remoteDateSource")DataSource remoteDateSource, @Qualifier("localDateSource")DataSource localDateSource){
        Map<Object,Object> datasourceMap = new HashMap<>();
        datasourceMap.put(DataEnum.LOCAL,localDateSource);
        datasourceMap.put(DataEnum.REMOTE,remoteDateSource);
        DynamicDataSource dynamicDataSource = new DynamicDataSource(remoteDateSource, datasourceMap);
        return dynamicDataSource;
    }
    @Bean(name="sessionFactory")
    public SqlSessionFactory sessionFactory(@Qualifier("dynamicDataSource")DataSource dataSource) throws Exception{
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*"));    //*Mapper.xml位置
        return sessionFactoryBean.getObject();
    }

}