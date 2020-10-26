package com.appPush;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.swing.*;

/**
 * //
 * //                       .::::.
 * //                     .::::::::.
 * //                    :::::::::::
 * //                 ..:::::::::::'
 * //              '::::::::::::'
 * //                .::::::::::
 * //           '::::::::::::::..
 * //                ..::::::::::::.
 * //              ``::::::::::::::::
 * //               ::::``:::::::::'        .:::.
 * //              ::::'   ':::::'       .::::::::.
 * //            .::::'      ::::     .:::::::'::::.
 * //           .:::'       :::::  .:::::::::' ':::::.
 * //          .::'        :::::.:::::::::'      ':::::.
 * //         .::'         ::::::::::::::'         ``::::.
 * //     ...:::           ::::::::::::'              ``::.
 * //    ```` ':.          ':::::::::'                  ::::..
 * //                       '.:::::'                    ':'````..
 *
 * @program: jhome-root
 * @description: APP订单服务  生产者
 * @author: Daxv
 * @create: 2020-10-16 14:01
 **/

@SpringBootApplication
//@EnableDubboConfiguration
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//忽略对数据源 的配置
//@DubboComponentScan("com.dubbo.provider.service") // 提供服务的应用必须配置此项
public class JhomeAppPushApplication {
    public static void main(String[] args) {
        SpringApplication.run(JhomeAppPushApplication.class, args);
    }
}
