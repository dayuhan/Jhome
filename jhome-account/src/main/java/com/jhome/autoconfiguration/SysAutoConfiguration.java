package com.jhome.autoconfiguration;

import com.bracket.common.BatchExcel.ExcelteEngine;
import com.bracket.common.Cache.MemcachedManager;
import com.bracket.common.Http.HttpClient;
import com.bracket.common.Queue.Bus;
import com.bracket.common.Queue.Config;
import com.bracket.common.WebSocket.WebSocket;
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
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * 系统组件
 */
@Configuration(
        proxyBeanMethods = true
)

@PropertySource(value = "classpath:application.yml", ignoreResourceNotFound = true)
@EnableTransactionManagement
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
     *
     * @return
     */
    @Bean("StringRedisTemplate")
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * 分布式锁框架
     *
     * @return
     */
    @Bean
    //@DependsOn("StringRedisTemplate")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient() {
        org.redisson.config.Config config = new org.redisson.config.Config();
        //System.out.println(String.format("Redis地址：%s", spiro.getRedissonUrl().toString()));
        config.useSingleServer()
                .setAddress(String.format("redis://%s:%s", spiro.getRedissConfig().getHost(), spiro.getRedissConfig().getPort()))
                //.setPassword(spiro.getRedissConfig().password)
                .setDatabase(spiro.getRedissConfig().getDatabase());
        //config.useSingleServer().setAddress(spiro.getRedissonUrl().toString()).setDatabase(0);
        //添加主从配置
        //config.useMasterSlaveServers().setMasterAddress("").setPassword("").addSlaveAddress(new String[]{"",""});
        return Redisson.create(config);
    }

    /**
     * 分页
     *
     * @return
     */
   /* @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        //设置为true时，将会RowBoundls 第一个参数offset当成pageNum页码使用
        properties.setProperty("offsetAsPageNum", "true");
        //设置为true时，使用RowBounds分页会进行count查询
        properties.setProperty("rowBoundsWithCount", "true");
        //启用合理化查询，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页
        //未开启时如果pageNum<1或者pageNum>pages会返回空数据
        properties.setProperty("reasonable", "true");
        //配置mysql数据库方言
        properties.setProperty("dialect", "myssql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }*/
    @Bean
    @LoadBalanced //(启用spring cloud 负载均衡 查询服务 如果单纯调外部网址，请删除)
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate() {
        RestTemplate restTemplate=new RestTemplate(new OkHttp3ClientHttpRequestFactory());
        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));//支持中文编码
        return restTemplate;
    }


    /**
     * rpc
     *
     * @return
     */
    @Bean("TTransport")
    @ConditionalOnMissingBean(TTransport.class)
    public TTransport tTransport() {
        return new TFramedTransport(new TSocket(spiro.getNettyServiceConfig().getUrl(), spiro.getNettyServiceConfig().getPort()), spiro.getNettyServiceConfig().getMaxLength());
    }

    @Bean
    @DependsOn("TTransport")
    @ConditionalOnMissingBean(socketService.Client.class)
    public socketService.Client client() {
        TProtocol tProtocol = new TCompactProtocol(tTransport());
        return new socketService.Client(tProtocol);
    }

}
