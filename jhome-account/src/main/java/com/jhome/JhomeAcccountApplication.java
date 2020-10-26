package com.jhome;

import com.rpc.common.zk.ZkUtil;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//忽略对数据源 的配置
@EnableEurekaClient
@EnableDiscoveryClient
//@ComponentScan({"com.jhome.autoconfiguration"})
//@MapperScan("")
//@sysScan("com.jhome.modules.userAuthentication.web")
public class JhomeAcccountApplication {
    public static void main(String[] args) {
        regZookerper();
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
