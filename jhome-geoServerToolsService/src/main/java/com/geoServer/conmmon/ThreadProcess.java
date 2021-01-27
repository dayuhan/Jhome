package com.geoServer.conmmon;

import com.alibaba.fastjson.JSONObject;
import com.geoServer.GeoServerApplication;
import com.geoServer.modules.geoServer.model.vo.ElevationDataVO;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.ws.rs.GET;
import java.util.ArrayList;
import java.util.List;

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
 * @description: 处理线程
 * @author: Daxv
 * @create: 2021-01-13 17:15
 **/
@Getter
@Setter
public class ThreadProcess implements Runnable {
    protected String path;
    protected RedisTemplate redisTemplate;
    protected Logger logger = LoggerFactory.getLogger(GeoServerApplication.class);
    public ThreadProcess() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ThreadProcess(String path) {
        super();
        this.path = path;
    }
    public void run() {
        try {
            logger.info("开始读取文件："+path);
            GDALUtil gdalUtil=new GDALUtil();
            //ArrayList arrayList = gdalUtil.GetMapRasterizedData(this.path);
            ArrayList arrayList = gdalUtil.GetMapRasterizedRangeData(109.054593,34.38693,109.071459,34.376766,this.path);
            String[] temp = path.split("\\\\");
            String fileName = temp[temp.length-1].substring(0,temp[temp.length-1].lastIndexOf("."));
            //String json=stringBuilder.toString();
            logger.info("读取文件大小："+String.valueOf(arrayList.size()));
            //redisTemplate.opsForValue().set(fileName, arrayList.toString());
            //logger.info(String.valueOf(arrayList.size()));
        }
        catch (Exception ex)
        {
            logger.info(ex.getMessage());
        }
    }
    /*
     * 卸载
     */
    public void disconnect() {
    }
}
