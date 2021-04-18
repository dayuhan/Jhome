package com.account.autoconfiguration;

import com.bracket.common.BatchExcel.ExcelteEngine;
import com.bracket.common.Cache.MemcachedManager;
import com.bracket.common.Http.HttpClient;
import com.bracket.common.Queue.Bus;
import com.bracket.common.Queue.Config;
import com.bracket.common.WebSocket.WebSocket;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 系统组件
 */
@Configuration(
        proxyBeanMethods = false
)
@PropertySource(value = "classpath:application.yml", ignoreResourceNotFound = true)
//@EnableConfigurationProperties({SysConfigurationPropertiesBean.class})
public class SysAutoConfiguration {
//        private SysConfigurationPropertiesBean spiro;
//    public SysAutoConfiguration(SysConfigurationPropertiesBean properties) {
//        this.spiro = properties;
//    }

    @Autowired
    private SysConfigurationPropertiesBean spiro;

    /**
     * Bus 消息队列框架 自定义
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean({Bus.class})
    public Bus CreateMqQueue() {
        Config config = new Config();
        try {
            config.setHostName(spiro.getQueueconfig().getHostName());
            config.setPassWord(spiro.getQueueconfig().getPassWord());
            config.setUserName(spiro.getQueueconfig().getUserName());
            config.setVirtualHost(spiro.getQueueconfig().getVirtualHost());
        } catch (Exception ex) {
            System.out.println(String.format("错误错误错误错误错误 %s", ex.getMessage()));
        }
        return new Bus(config);
    }

    /**
     * HttpClient http轻轻框架 自定义
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean({HttpClient.class})
    public HttpClient CreateHttpClient() {
        return new HttpClient();
    }

    @Bean
    @ConditionalOnMissingBean({WebSocket.class})
    public void CreatewebSocket() {
    }

    /**
     * ExcelteEngine exl导入引擎框架 自定义
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean({ExcelteEngine.class})
    public void CreateBatchExcel() {
    }

    /**
     * MemcachedManager 框架 自定义
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean({MemcachedManager.class})
    public MemcachedManager CreateMenacedManage() {
        return new MemcachedManager();
    }


    /**
     * StringRedisTemplate
     * @return
     */
    @Bean("StringRedisTemplate")
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory)
    {
        StringRedisTemplate redisTemplate=new StringRedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return  redisTemplate;
    }

    /**
     * 分布式锁框架
     *
     * @return
     */
    @Bean("RedissonClient")
    @DependsOn("StringRedisTemplate")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient() {
        org.redisson.config.Config config = new org.redisson.config.Config();
        System.out.println(String.format("Redis地址：%s", spiro.getRedissonUrl().toString()));
        config.useSingleServer().setAddress(spiro.getRedissonUrl().toString()).setDatabase(0);
        //添加主从配置
        //config.useMasterSlaveServers().setMasterAddress("").setPassword("").addSlaveAddress(new String[]{"",""});
        return  Redisson.create(config);
    }

    /**
     * 向Spring容器中定义RestTemplate对象
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }

}
