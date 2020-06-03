package com.jhome.autoconfiguration;

import com.daxu.common.BatchExcel.ExcelteEngine;
import com.daxu.common.Cache.MemcachedManager;
import com.daxu.common.Http.HttpClient;
import com.daxu.common.Queue.Bus;
import com.daxu.common.Queue.Config;
import com.daxu.common.WebSocket.WebSocket;
import com.rpc.common.thrift.socketService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 系统组件
 */
@Configuration(
       // proxyBeanMethods = false
)
//@PropertySource(value = "classpath:application.yml", ignoreResourceNotFound = true)
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
     * rpc
     * @return
     */
    @Bean("TTransport")
    @ConditionalOnMissingBean(TTransport.class)
    public TTransport tTransport()
    {
        return new TFramedTransport(new TSocket("127.0.0.1",8899),600);
    }
    @Bean
    @DependsOn("TTransport")
    @ConditionalOnMissingBean(socketService.Client.class)
    public socketService.Client client()
    {
        TProtocol tProtocol=new TCompactProtocol(tTransport());
        return  new socketService.Client(tProtocol);
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