package com.bracket.common.Translate;

import com.bracket.common.ToolKit.StringUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * @description: 翻译请求
 * @author: Daxv
 * @create: 2021-04-21 09:28
 **/
public class TranslateHttpUtil {
    protected  Logger logger = LoggerFactory.getLogger(this.getClass());


    public static CloseableHttpClient getHttpClient(String qlb) {
        HttpHost proxy = new HttpHost(qlb, 80, "http");
        //把代理设置到请求配置
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setProxy(proxy)
                .build();
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        return httpclient;
    }



    public static String postData(String url, Map<String, Object> paramMap, String regEx, HttpHost proxy) {
        StringBuilder sbBuilder = new StringBuilder();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        RequestConfig requestConfig=null;
        String result = "";
        //httpClient = HttpClients.createDefault();
        //执行https
        httpClient = getHttpsClient(proxy);
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);

        // 根据proxyIp 判断是否要创建代理服务器 创建httpClient实例
        if (proxy==null) {
            // 配置请求参数实例
            requestConfig = RequestConfig.custom()
                    .setConnectTimeout(65000)// 设置连接主机服务超时时间
                    .setConnectionRequestTimeout(65000)// 设置连接请求超时时间
                    .setSocketTimeout(60000)// 设置读取数据连接超时时间
                    .build();
        } else {
            // 配置请求参数实例
            requestConfig = RequestConfig.custom()
                    .setConnectTimeout(65000)// 设置连接主机服务超时时间
                    .setConnectionRequestTimeout(65000)// 设置连接请求超时时间
                    .setSocketTimeout(60000)// 设置读取数据连接超时时间
                   // .setProxy(proxy)
                    .build();
        }
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        // 设置请求头
        //httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpPost.addHeader("content-type", "application/x-www-form-urlencoded;charset=UTF-8");
        httpPost.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36");

        // 封装post请求参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (null != paramMap && paramMap.size() > 0) {
            // 通过map集成entrySet方法获取entity
            Set<Map.Entry<String, Object>> entrySet = paramMap.entrySet();
            // 循环遍历，获取迭代器
            Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> mapEntry = iterator.next();
                nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry
                        .getValue().toString()));
            }
        }
        try {
            // 为httpPost设置封装好的请求参数
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
            System.out.println(result);
        } catch (ClientProtocolException e) {
            sbBuilder.append("ClientProtocolException:" + e.getMessage());
        } catch (IOException e) {
            sbBuilder.append(" IOException:" + e.getMessage());

        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    sbBuilder.append(" IOException:" + e.getMessage());
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    sbBuilder.append(" IOException:" + e.getMessage());

                }
            }
        }
        //String regEx="[\\u4e00-\\u9fa5]+[^,~！@#￥%……&*()——+{}][\\u4e00-\\u9fa5]+";
        if (StringUtil.isBlank(regEx))
            return result;

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(result);
        List<String> tos = new ArrayList<>();
        String to = "";
        //取翻译长度最大值
//        if (matcher.find()) {
//            for (int i = 0; i <= matcher.groupCount(); i++) {
//                tos.add(matcher.group(i));
//            }
//            to = getMax(tos).get(0);
//        }
        //取所有翻译后拼接
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while (matcher.find()) {
            System.out.println(matcher.group());
            if (!sb.toString().contains(matcher.group()))
            {
                sb.append(matcher.group());
                sb.append(",");
                i++; //字符串出现次数
            }
        }
        //返回接口结果
        if (StringUtil.isBlank(sb.toString()))
            return result;
        //返回正则表达式结果
        to = sb.substring(0,sb.length()-1);
        return to;
    }
    //public abstract String regExMatcher(String regEx,String result);

    public static List<String> getMax(List<String> str) {
        int maxLength = Integer.MIN_VALUE;
        List<String> maxList = new ArrayList<>();
        for (String s : str) {
            if (s.length() > maxLength) {
                maxLength = s.length();
                maxList.clear();
                maxList.add(s);
            } else if (s.length() == maxLength) {
                maxList.add(s);
            }
        }
        return maxList;
    }

    public static List<String> getMin(String[] str) {
        int minLength = Integer.MAX_VALUE;
        List<String> minList = new ArrayList<>();
        for (String s : str) {
            if (s.length() < minLength) {
                minLength = s.length();
                minList.clear();
                minList.add(s);
            } else if (s.length() == minLength) {
                minList.add(s);
            }
        }
        return minList;
    }


    public static CloseableHttpClient getHttpClientByP(String qlb) {
        HttpHost proxy = new HttpHost(qlb, 80, "http");
        //把代理设置到请求配置
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setProxy(proxy)
                .build();
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        return httpclient;
    }

    /**
     * 对证书不做检查
     * @param qlb
     * @return
     */
    public static CloseableHttpClient getHttpsClient(HttpHost proxy ) {
        //这里设置客户端不检测服务器ssl证书
        try {
            X509TrustManager x509mgr = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs, String string) {
                }
                public void checkServerTrusted(X509Certificate[] xcs, String string) {
                }
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] { x509mgr }, null);
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            HttpHost proxy1 = new HttpHost(proxy.getHostName(), proxy.getPort(), "https");
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setProxy(proxy1)
                    .build();
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(defaultRequestConfig).build();
            return httpclient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
