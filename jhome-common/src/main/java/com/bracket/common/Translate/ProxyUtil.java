package com.bracket.common.Translate;

import com.alibaba.fastjson.JSONObject;
import com.bracket.common.Http.HttpClient;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
 * @description: 代理服务
 * @author: Daxv
 * @create: 2021-04-22 10:58
 **/
public class ProxyUtil {
    protected static Logger LOGGER = LoggerFactory.getLogger(ProxyUtil.class);

    /**
     * 根据 exl获取代理
     *
     * @return
     */
    protected static void getProxyIpsByServer() {
        LOGGER.info("开始启动代理");
        ProxyServerThread proxyServerThread=new ProxyServerThread();
        Thread thread = new Thread(proxyServerThread);
        thread.start();

    }


    /**
     * 根据API获取代理
     *
     * @param proxyUrl
     * @return
     */
    protected static List<ProxyModel> getProxyIpsByHttp(String proxyUrl) {
        List<ProxyModel> proxyList = new ArrayList<>();
        try {
            HttpClient httpClient = new HttpClient();
            String json = httpClient.getData(proxyUrl);
            proxyList = JSONObject.parseObject(json, (Type) ProxyModel.class);
            return proxyList;
        } catch (Exception ex) {
            LOGGER.error(String.format("获取代理服务报错：%s", ex.getMessage()));
        }
        return null;
    }

    /**
     * 判断端口是否可用
     *
     * @param proxyHost
     * @param proxyPort
     * @return
     */
    protected static boolean doIsConnect(String proxyHost, int proxyPort) throws IOException {
        HttpClient httpClient = new HttpClient();
        HttpHost proey=new HttpHost(proxyHost,proxyPort);
        List<String> urls=new ArrayList<>();
        urls.add("https://www.hao123.com/");
        urls.add("https://www.baidu.com/");
        urls.add("https://www.qq.com/");
        urls.add("https://www.163.com/");
        urls.add("https://www.ifeng.com/");
        urls.add("https://www.sohu.com/");
        urls.add("https://www.sina.com.cn/");
        urls.add("https://www.vip.com/");
        urls.add("https://www.jd.com/");
        urls.add("https://www.zhipin.com/");
        Random random = new Random();
        int n = random.nextInt(urls.size());
        return httpClient.isGetWithProxy(urls.get(n),proey);
    }
}
