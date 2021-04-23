package com.account;

import com.bracket.common.Translate.GoogleApi;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import org.omg.CORBA.NameValuePair;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//忽略对数据源 的配置
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
/*@EnableHystrix*/
//@EnableDiscoveryClient
//@ComponentScan({"com.account.autoconfiguration"})
//@MapperScan("")
public class JhomeWebApplication {
    public static void main1(String[] args) throws IOException {
        tt();
        new SpringApplicationBuilder(JhomeWebApplication.class).bannerMode(Banner.Mode.OFF).run(args);
    }


    public static void tt()
    {
        GoogleApi googleApi = new GoogleApi();
        //GoogleApi googleApi = new GoogleApi("122.224.227.202", 3128);
        String result = googleApi.translate("Many applications within the enterprise domain ", "", "zh");
        System.out.println("-------------------------------------------");
        System.out.println(result);

    }


    public static void main(String[] args) throws Exception {
        // Set the HTTP referrer to your website address.
        Translate.setHttpReferrer("https://www.hao123.com/");
        String translatedText = Translate.execute("You", Language.ENGLISH, Language.ENGLISH);

        System.out.println(translatedText);

        try{
   /*         String translatedText=Translate.execute("你好",Language.CHINESE,Language.ENGLISH);
            System.out.println(translatedText);*/
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
}
