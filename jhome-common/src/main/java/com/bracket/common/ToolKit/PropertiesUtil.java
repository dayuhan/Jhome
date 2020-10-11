package com.bracket.common.ToolKit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

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
 * @description:
 * @author: Daxv
 * @create: 2020-10-09 17:16
 **/
public class PropertiesUtil {
    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    //private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static Properties props;

    //Tomcat运行时执行
    //代码块执行顺序：静态代码块>普通代码块>构造代码块
    //构造代码块每次都执行，但是静态代码块只执行一次
//    static {
//        String fileName = "xxx.properties";
//        props = new Properties();
//        try {
//            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
//        } catch (IOException e) {
//            logger.error("配置文件读取异常",e);
//        }
//    }
    //自定义俩个get方法，方便调用工具类读取properties文件的属性
    public static String getProperty(String key){
        String value= props.getProperty(key.trim());
        if (StringUtil.isBlank(value)){
            return null;
        }
        return value.trim();
    }
    public static String getProperty(String key,String defaultValue){
        String value= props.getProperty(key.trim());
        if (StringUtil.isBlank(value)){
            value = defaultValue;
        }
        return value.trim();
    }
    public static Properties loadProperties(String s) {
        String fileName = s;
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            logger.error("配置文件读取异常",e);
        }
        return props;
    }
}
