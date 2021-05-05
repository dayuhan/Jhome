package com.account.modules.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.account.autoconfiguration.SysConfigurationPropertiesBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * 多数据库源
 *
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-06-09 22:10
 **/

//@MapperScan(value = {"com.account.modules.userAuthentication.dao"}, sqlSessionFactoryRef = "SqlSessionFactory1")
////使用指定的sqlSessionFactory作为数据源
//@Configuration(
//        proxyBeanMethods = true
//)
public class MybatisConfig {
    @Autowired
    private SysConfigurationPropertiesBean spiro;

    /***
     * spring 整个 Mybatis
     * @return
     */
    @Bean("dataSource1")
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setPassword(spiro.getDatasourceconfig().getPassWord());
        dataSource.setUsername(spiro.getDatasourceconfig().getUserName());
        dataSource.setUrl(spiro.getDatasourceconfig().getUrl());
        dataSource.setDbType(spiro.getDatasourceconfig().getType());
        dataSource.setDriverClassName(spiro.getDatasourceconfig().getDrivrerClassName());
        return dataSource;
    }

    @Bean("SqlSessionFactory1")
    @ConditionalOnMissingBean(value = {SqlSessionFactory.class})
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource1") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        //动态获取sqlmapper
        PathMatchingResourcePatternResolver classPathResource = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(classPathResource.getResources("classPath:/*.mapper"));

        //配置myBasei
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setUseGeneratedKeys(true);//使用jdbc的getGeneratedKeys 获取数据库主键
        configuration.setUseColumnLabel(true);//使用别名替换 如 select user as User
        configuration.setMapUnderscoreToCamelCase(true);// 自动使用驼峰命名映射字段 如：userId user_id
        sqlSessionFactoryBean.setConfigLocation((Resource) configuration);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 配置spring 事务
     *
     * @param dataSource
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("dataSource1") DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        return dataSourceTransactionManager;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        return propertySourcesPlaceholderConfigurer;
    }

}
