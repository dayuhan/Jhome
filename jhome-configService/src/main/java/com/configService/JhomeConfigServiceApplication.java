package com.configService;
import com.bracket.common.register.ProductCode;
import com.bracket.common.register.Register;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
 * @program: account-root
 * @description: spring cloud config  配置中心
 * @author: Daxv
 * @create: 2017-08-02 13:51
 **/
@SpringBootApplication
@EnableConfigServer
public class JhomeConfigServiceApplication {
//    public static void main(String[] args) {
//        //new SpringApplicationBuilder(JhomeConfigServiceApplication.class).bannerMode(Banner.Mode.OFF).run(args);
//        SpringApplication.run(JhomeConfigServiceApplication.class);
//    }

    protected static Logger logger = LoggerFactory.getLogger(JhomeConfigServiceApplication.class);
    private static final int intervalMin = 5; // 定时器间隔时间
    protected static RedisTemplate redisTemplate;
    static {
//        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(LuxConfigServiceApplication.class);
//        redisTemplate= (RedisTemplate) applicationContext.getBean("StringRedisTemplate");
    }

    public static void main(String[] args) {
        logger.info("#####开始监测注册##########");
        //VerifyRegistration();
        logger.info("#####开始启动服务##########");
        SpringApplication.run(JhomeConfigServiceApplication.class);
    }

    private static void VerifyRegistration() {
        try {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        // 执行授权验证方法并复制静态变量
                        boolean isResult= Register.Check(ProductCode.PRODUCT_CODE.toString());
                        //通过redis 修改注册状态
                        //redisTemplate.opsForValue().set(ProductCode.PRODUCT_CODE.toString(),isResult);
                        System.out.println("==========执行定时器=====返回："
                                + isResult);
                    } catch (Throwable e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            };
            // 定时器线程池
            ScheduledExecutorService service = Executors
                    .newSingleThreadScheduledExecutor();
            // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
            service.scheduleAtFixedRate(runnable, intervalMin, intervalMin,
                    TimeUnit.MINUTES); // 每五分钟执行一次run方法
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
