package com.account;

import com.rpc.common.zk.ZkUtil;
import com.rpc.common.zk.annotation.DubboExpand;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//忽略对数据源 的配置
@EnableEurekaClient
@EnableDiscoveryClient
//@DubboExpand(serverName = "JKQ-Account",value="192.168.20.102:9101",isDiscovery = true)
//@ComponentScan({"com.account.autoconfiguration","com.account.modules.config"})
//@MapperScan({"com.account.modules.*.dao","com.account.common.sbUtil.dao"})
//@sysScan("com.account.modules.userAuthentication.controller")
public class JhomeAcccountApplication {
    public static void main(String[] args) {
       // regZookerper();
        new SpringApplicationBuilder(JhomeAcccountApplication.class).bannerMode(Banner.Mode.OFF).run(args);
        //SpringApplication.run(JhomeAcccountApplication.class, args);
    }
    public static void regZookerper()
    {
        ZkUtil.initialize();
        String parent = "/zkconfig";
        String child = "chynode";
        String path = parent + "/" + child;
        ZkUtil.initialize();
        ZkUtil.createSequentialEphemeralNode(path,"daxu");
        ZkUtil.createSequentialEphemeralNode(path,"chenlin");
        ZkUtil.createSequentialEphemeralNode(path,"hanhan");

    }
}
