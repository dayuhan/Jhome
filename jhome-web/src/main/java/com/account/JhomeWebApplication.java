package com.account;

import com.bracket.common.Word.WordUtil;
import com.bracket.common.Word.XWPFHelper;
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

        new SpringApplicationBuilder(JhomeWebApplication.class).bannerMode(Banner.Mode.OFF).run(args);
    }
    public static void main(String[] args) throws IOException {
    {
        WordUtil xwpfHelper=new WordUtil();
        xwpfHelper.test1();

                /*
        String templatePath = "D:\\word\\template.doc";
        InputStream is = new FileInputStream(templatePath);
        HWPFDocument doc = new HWPFDocument(is);
        Range range = doc.getRange();
        //把range范围内的${reportDate}替换为当前的日期
        range.replaceText("${reportDate}", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        range.replaceText("${appleAmt}", "100.00");
        range.replaceText("${bananaAmt}", "200.00");
        range.replaceText("${totalAmt}", "300.00");
        OutputStream os = new FileOutputStream("D:\\word\\write.doc");
        //把doc输出到输出流中
        doc.write(os);
        this.closeStream(os);
        this.closeStream(is);*/
    }



    }


}
