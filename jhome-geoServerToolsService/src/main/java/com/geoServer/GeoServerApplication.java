package com.geoServer;

import com.geoServer.autoconfiguration.SysConfigurationPropertiesBean;
import com.geoServer.conmmon.FileIOUtil;
import com.geoServer.conmmon.SpringBeanFactoryUtils;
import com.geoServer.conmmon.ThreadProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
 * @description: 启动GEO地图服务
 * @author: Daxv
 * @create: 2021-01-09 11:39
 **/

@SpringBootApplication
//@EnableEurekaClient
//@EnableDiscoveryClient
public class GeoServerApplication {
    protected static Logger logger = LoggerFactory.getLogger(GeoServerApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(GeoServerApplication.class, args);
        SysConfigurationPropertiesBean sysConfigurationPropertiesBean = SpringBeanFactoryUtils.getBean(SysConfigurationPropertiesBean.class);
        RedisTemplate redisTemplate = SpringBeanFactoryUtils.getBean(StringRedisTemplate.class);
        StringBuilder sb=new StringBuilder();

        //从redis中读取  处理数组后需要10秒
/*        long startTime = System.currentTimeMillis();//获取开始时间
        sb.append(redisTemplate.opsForValue().get("Map250000")) ;
        String[] st=  sb.toString().split(",");
        long endTime = System.currentTimeMillis();//获取结束时间
        logger.info("耗时" + (endTime - startTime) + "毫秒】");*/

        //读取文件 产生数组 8s
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        String directoryPath = sysConfigurationPropertiesBean.getDirectoryPath();
        ArrayList<String> fileNames = new ArrayList<>();
        FileIOUtil.getAllFileName(directoryPath, fileNames);
        fileNames.forEach(c -> {
            try {
                ThreadProcess threadProcess = new ThreadProcess();
                threadProcess.setPath(directoryPath + c);
                threadProcess.setRedisTemplate(redisTemplate);
                executorService.execute(threadProcess);
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }



    public static void main2(String[] args)
    {
        double longitude = Double.parseDouble(String.valueOf(108.999421));
        double latitude = Double.parseDouble(String.valueOf(34.308953));
        double dis = Double.parseDouble(String.valueOf(5.0));
        //先计算查询点的经纬度范围
        double r = 6371;//地球半径千米
        double dlng =  2*Math.asin(Math.sin(dis/(2*r))/Math.cos(latitude*Math.PI/180));
        dlng = dlng*180/Math.PI;//角度转为弧度
        double dlat = dis/r;
        dlat = dlat*180/Math.PI;
        double minlat =latitude-dlat;
        double maxlat = latitude+dlat;
        double minlon= longitude -dlng;
        double maxlon= longitude + dlng;
        System.out.println("左上角经纬度："+minlon+"  "+maxlat);
        System.out.println("右下角经纬度："+maxlon+"  "+minlat);
    }
}



