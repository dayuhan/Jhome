package com.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-05-22 10:46
 **/
@Getter
@Setter
@Component
@PropertySource("classpath:config/application.properties")
public class ConfigInfo {
    @Value("${webSocket.server.port}")
    private int webSocketPort;
    @Value("${rpc.server.port}")
    private int rpcPort;
    @Value("${rpc.minWorkerThreads}")
    private  int minWorkerThreads;
    @Value("${rpc.maxWorkerThreads}")
    private  int maxWorkerThreads;

    @Value("${redis.host}")
    private String redisHost;
    @Value("${redis.database}")
    private String redisDatabase;
    @Value("${redis.port}")
    private String redisPort;
    @Value("${redis.password}")
    private String redisPassword;
    @Value("${redis.timeout}")
    private String redisTimeout;

    @Value("${queueConfig.hostName}")
    private String hostName;
    @Value("${queueConfig.userName}")
    private String userName;
    @Value("${queueConfig.passWord}")
    private String passWord;
    @Value("${queueConfig.virtualHost}")
    private String virtualHost;


}
