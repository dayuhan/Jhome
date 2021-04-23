package com.bracket.common.Translate.motherVersion;

import com.bracket.common.Http.HttpClient;
import org.apache.http.HttpHost;

import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.InetSocketAddress;
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
 * @description:
 * @author: Daxv
 * @create: 2021-04-19 17:11
 **/
public class Browser {

    public HttpHost httpHost;
    public String url;
    public int port;


    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPort(int port) {
        this.port=port;
    }
    public HttpHost getHttpHost() {
        return this.httpHost;
    }

    public void setHttpHost(String ip, Integer port) {

        this.httpHost=new HttpHost(this.url,port);
    }

    public String executeGet()
            throws Exception {
        HttpClient client=new HttpClient();
        String result;
        if (this.httpHost != null)
            result =client.doGetWithProxy(this.url,new HttpHost(this.url,this.port)) ;
        else {
            result=client.getData(this.url);
            //result = HttpClientUtil.doGet(this.url);
        }

        return result;
    }
}
