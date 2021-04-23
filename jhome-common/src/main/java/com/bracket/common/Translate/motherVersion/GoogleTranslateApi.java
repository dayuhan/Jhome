package com.bracket.common.Translate.motherVersion;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
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
 * @description: google翻译Api
 * @author: Daxv
 * @create: 2021-04-20 14:20
 **/
public   class GoogleTranslateApi {
    public static Map<String, Object> paramMap;
    public static String url = "https://translate.google.cn/_/TranslateWebserverUi/data/batchexecute?rpcids=MkEWBc&f.sid=6739914524248906935&bl=boq_translate-webserver_20210418.17_p0&hl=zh-CN&soc-app=1&soc-platform=1&soc-device=1&_reqid=258052&rt=c";
    public static String paramStr="[[[\"MkEWBc\",\"[[\\\"%s\\\",\\\"auto\\\",\\\"zh-CN\\\",true],[null]]\",null,\"generic\"]]]";

    static {
        if (paramMap == null)
            paramMap = new HashMap<>();
//        String str="[[[\"MkEWBc\",\"[[\\\"%s\\\",\\\"auto\\\",\\\"zh-CN\\\",true],[null]]\",null,\"generic\"]]]";
//        String st= String.format(str,"สวัสดีฉันชอบคุณมาก?");
//        paramMap.put("f.req",st );
    }

    public static void main(String[] args) {
        //GoogleApi googleApi = new GoogleApi("122.224.227.202", 3128);
        String result = GoogleTranslateApi.translate("안녕하세요 이름이 뭐예요", "", "zh");
        System.out.println("-----------------翻译结果--------------------------");
        System.out.println(result);
    }


    public static String translate(String word, String from, String to) {
        paramMap.clear();
        paramMap.put("f.req",String.format(paramStr,word));
       return postData(url,paramMap);
    }
    public static String postData(String url, Map<String, Object> paramMap) {
        StringBuilder sbBuilder = new StringBuilder();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(35000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                .setSocketTimeout(60000)// 设置读取数据连接超时时间
                .build();
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
        String regEx="[\\u4e00-\\u9fa5]+[^,~！@#￥%……&*()——+{}][\\u4e00-\\u9fa5]+";

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(result);

        List<String> tos=new ArrayList<>();
        String to="";
        if(matcher.find())
        {

            int it=  matcher.groupCount();
            for (int i=0;i<=matcher.groupCount();i++)
            {
                tos.add(matcher.group(i));
            }
            to=getMax(tos).get(0);
            System.out.println(matcher.group(0));

        }
        return to;
    }

    public static Map<String, Object> getParamMap() {
        return paramMap;
    }

    public static void setParamMap(Map<String, Object> paramMap) {
        GoogleTranslateApi.paramMap = paramMap;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        GoogleTranslateApi.url = url;
    }

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
}
