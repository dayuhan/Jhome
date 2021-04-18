package com.account;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//忽略对数据源 的配置
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
/*@EnableHystrix*/
//@EnableDiscoveryClient
//@ComponentScan({"com.account.autoconfiguration"})
//@MapperScan("")
public class JhomeWebApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(JhomeWebApplication.class).bannerMode(Banner.Mode.OFF).run(args);

    }


}
