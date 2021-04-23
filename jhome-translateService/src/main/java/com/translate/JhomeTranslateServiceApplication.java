package com.translate;

import com.bracket.common.Translate.GoogleTranslate;
import com.bracket.common.Translate.TranslateCommander;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@EnableAsync
@EnableFeignClients
@EnableDiscoveryClient
public class JhomeTranslateServiceApplication {
    public static void main1(String[] args) {
        new SpringApplicationBuilder(JhomeTranslateServiceApplication.class).bannerMode(Banner.Mode.OFF).run(args);
    }
    public static void main(String[] args) throws InterruptedException {
        TranslateCommander translateCommander = new TranslateCommander(new GoogleTranslate());
        //translateCommander.TranslateInvoke("난 당신이 너무 좋아", "", "",null);
        //Thread.sleep(1000);
        translateCommander.TranslateInvoke("Jianke Building", "en", "",null);

    }
}
