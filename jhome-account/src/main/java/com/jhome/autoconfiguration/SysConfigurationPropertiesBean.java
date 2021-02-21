package com.jhome.autoconfiguration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class SysConfigurationPropertiesBean extends SysProperties {
    public String adminPath;//后台路径
    public String frontPath;//前台路径
    public QueueConfig queueconfig;//队列配置
    public RedissConfig redissConfig;//缓存配置
    private long expiredTime;//缓存过期时间
    public DataSourceConfig datasourceconfig;//自定义数据库配置
    public CasConfig casConfig;//CAS单点登录配置
    public String callbackUrl;//cas单点登录回调地址
    public  NettyServiceConfig nettyServiceConfig;//netty配置信息



    /**
     * 队列服务
     */
    @Getter
    @Setter
    public static class QueueConfig {
        public String hostName;
        public String userName;
        public String passWord;
        public String virtualHost;

        @Override
        public String toString() {
            return "QueueConfig{" +
                    "hostName='" + hostName + '\'' +
                    ", userName='" + userName + '\'' +
                    ", passWord='" + passWord + '\'' +
                    ", virtualHost='" + virtualHost + '\'' +
                    '}';
        }
    }

    @Getter
    @Setter
    public static class RedissConfig {
        public String host;
        public Integer database;
        public String port;
        public String password;
        public Integer timeout;
    }

    /**
     * 自定义数据源
     */
    @Getter
    @Setter
    public static class DataSourceConfig {
        public String userName;
        public String passWord;
        public String url;
        public String drivrerClassName;
        public String type;

    }
    @Getter
    @Setter
    public static class CasConfig {
        public String clientName;
        public String serverUrl;
        public String projectUrl;
        public String isEnable;
        public String redirectUrl;
    }

    @Getter
    @Setter
    public static class NettyServiceConfig
    {
        public String url;
        public Integer port;
        public Integer maxLength;

    }



}