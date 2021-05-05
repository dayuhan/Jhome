package com.registrationService;/**
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-08-02 16:09
 **/

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import javax.swing.*;

/**
 //
 //                       .::::.
 //                     .::::::::.
 //                    :::::::::::
 //                 ..:::::::::::'
 //              '::::::::::::'
 //                .::::::::::
 //           '::::::::::::::..
 //                ..::::::::::::.
 //              ``::::::::::::::::
 //               ::::``:::::::::'        .:::.
 //              ::::'   ':::::'       .::::::::.
 //            .::::'      ::::     .:::::::'::::.
 //           .:::'       :::::  .:::::::::' ':::::.
 //          .::'        :::::.:::::::::'      ':::::.
 //         .::'         ::::::::::::::'         ``::::.
 //     ...:::           ::::::::::::'              ``::.
 //    ```` ':.          ':::::::::'                  ::::..
 //                       '.:::::'                    ':'````..
 * @program: jhome-root
 * @description: JhomeRegistrationService 注册中心
 * @author: Daxv
 * @create: 2017-08-02 16:09
 **/
@EnableEurekaServer //启动EurekaServer
@SpringBootApplication
public class JhomeRegistrationService {
    public static void main(String[] args) {
        SpringApplication.run(JhomeRegistrationService.class);
        //new SpringApplicationBuilder(JhomeRegistrationService.class).bannerMode(Banner.Mode.OFF).run(args);
    }
}