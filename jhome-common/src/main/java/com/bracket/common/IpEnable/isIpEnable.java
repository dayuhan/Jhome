package com.bracket.common.IpEnable;

import com.bracket.common.Http.HttpClient;
import com.bracket.common.Translate.ProxyUtil;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

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
 * @description: 检查ip是否可用
 * @author: Daxv
 * @create: 2021-04-23 09:49
 **/
public class isIpEnable {
    protected static Logger LOGGER = LoggerFactory.getLogger(ProxyUtil.class);

    /**
     * 检测http 连接是否可用
     * @param url
     * @return
     */
    public static boolean checkUrlIsValid(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(5000)
                .build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;

        boolean isValid = false;

        try {
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == 200) {
                isValid = true;
            }
        } catch (Exception e) {

        } finally {
            if(response != null) {
                try {
                    response.close();
                } catch (IOException e) {

                }
            }
        }
        return isValid;
    }
    /**
     * 判断端口是否可用
     *
     * @param server
     * @param port
     * @return
     */
    protected boolean doIsConnect(String server, int port) throws IOException {
        InetAddress address = InetAddress.getByName(server);
        boolean state = address.isReachable(1000);
        if (state) {
            LOGGER.error("************DubboExpand提示：服务不可用！************");
            return false;
        }
        Socket socket = new Socket();
        socket.setReceiveBufferSize(port);
        socket.setSoTimeout(1000);
        SocketAddress socketAddress = new InetSocketAddress(server, port);
        try {
            socket.connect(socketAddress, 1000);
            return true;
        } catch (Exception ex) {
            LOGGER.error("************DubboExpand提示：新建一个Socket server连接失败！************");

        }
        return false;
    }


    /**
     * 批量代理IP有效检测
     *
     *  @param  proxyIpMap
     *  @param  reqUrl
     */
  /*  public  static  void  checkProxyIp(Map<String, Integer> proxyIpMap, String reqUrl) {

        for  (String proxyHost : proxyIpMap.keySet()) {
            Integer proxyPort = proxyIpMap.get(proxyHost);

            int  statusCode = 0;
            try  {
                HttpClient httpClient =  new  HttpClient();
                httpClient.getHostConfiguration().setProxy(proxyHost, proxyPort);

                // 连接超时时间（默认10秒 10000ms） 单位毫秒（ms）
                int  connectionTimeout = 10000;
                // 读取数据超时时间（默认30秒 30000ms） 单位毫秒（ms）
                int  soTimeout = 30000;
                httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
                httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);

                HttpMethod method =  new  GetMethod(reqUrl);

                statusCode = httpClient.executeMethod(method);
            }  catch  (Exception e) {
                //  TODO  Auto-generated catch block
                e.printStackTrace();
            }
            System. out .format( "%s:%s-->%s\n" , proxyHost, proxyPort, statusCode);
        }
    }*/


}
