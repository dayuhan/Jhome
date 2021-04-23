package com.bracket.common.Http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Proxy;
import java.util.*;
import java.util.Map.Entry;

public class HttpClient {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public HttpClient() {
    }

    /**
     * Post请求
     *
     * @param url
     * @param paramMap
     * @return
     */
    public void postData(String url, Map<String, Object> paramMap, HttpDao httpDao) {
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
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        // 封装post请求参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (null != paramMap && paramMap.size() > 0) {
            // 通过map集成entrySet方法获取entity
            Set<Entry<String, Object>> entrySet = paramMap.entrySet();
            // 循环遍历，获取迭代器
            Iterator<Entry<String, Object>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Entry<String, Object> mapEntry = iterator.next();
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
            if (sbBuilder.toString().isEmpty()) {
                httpDao.requestFinish(result);
            } else {
                httpDao.requestFail(String.format("失败原因：%s", sbBuilder.toString()));
            }
        }
    }


    /**
     * post请求
     *
     * @param url
     * @param paramMap
     * @return
     */
    public String postData(String url, Map<String, Object> paramMap) {
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
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        // 封装post请求参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (null != paramMap && paramMap.size() > 0) {
            // 通过map集成entrySet方法获取entity
            Set<Entry<String, Object>> entrySet = paramMap.entrySet();
            // 循环遍历，获取迭代器
            Iterator<Entry<String, Object>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Entry<String, Object> mapEntry = iterator.next();
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
        return sbBuilder.toString().isEmpty() ? result : sbBuilder.toString();
    }

    /**
     * Get请求 代钩子方法
     *
     * @param url
     * @return
     */
    public void getData(String url, HttpDao httpDao) {
        StringBuilder sbBuilder = new StringBuilder();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 通过址默认配置创建一个httpClient实例
            httpClient = HttpClients.createDefault();
            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url);
            // 设置请求头信息，鉴权
            // httpGet.setHeader("Authorization",
            // "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 设置配置请求参数
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(35000)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)// 请求超时时间
                    .setSocketTimeout(60000)// 数据读取超时时间
                    .build();
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            sbBuilder.append("ClientProtocolException:" + e.getMessage());
        } catch (IOException e) {
            sbBuilder.append(" IOException:" + e.getMessage());
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
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
            if (sbBuilder.toString().isEmpty()) {
                httpDao.requestFinish(result);
            } else {
                httpDao.requestFail(String.format("失败原因：%s", sbBuilder.toString()));
            }


        }

    }

    /**
     * get 请求
     *
     * @param url
     * @return
     */
    public String getData(String url) {
        StringBuilder sbBuilder = new StringBuilder();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 通过址默认配置创建一个httpClient实例
            httpClient = HttpClients.createDefault();
            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url);
            // 设置请求头信息，鉴权
            // httpGet.setHeader("Authorization",
            // "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 设置配置请求参数
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(35000)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)// 请求超时时间
                    .setSocketTimeout(60000)// 数据读取超时时间
                    .build();
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            sbBuilder.append("ClientProtocolException:" + e.getMessage());
        } catch (IOException e) {
            sbBuilder.append(" IOException:" + e.getMessage());
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
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
            return sbBuilder.toString().isEmpty() ? result : sbBuilder.toString();
        }

    }

    /**
     * 通过代理访问 get方式
     *
     * @param url
     * @param
     * @return
     */
    public String doGetWithProxy(String url, HttpHost httpHost) {
        StringBuilder sbBuilder = new StringBuilder();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 通过址默认配置创建一个httpClient实例
            //httpClient = HttpClients.createDefault();
            //把代理设置到请求配置
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setProxy(httpHost)
                    .build();
            httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();

            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url);
            // 设置请求头信息，鉴权
            // httpGet.setHeader("Authorization",
            // "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 设置配置请求参数
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(35000)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)// 请求超时时间
                    .setSocketTimeout(60000)// 数据读取超时时间
                    .build();
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);
           int i=  response.getStatusLine().getStatusCode();
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            sbBuilder.append("ClientProtocolException:" + e.getMessage());
        } catch (IOException e) {
            sbBuilder.append(" IOException:" + e.getMessage());
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
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
            return sbBuilder.toString().isEmpty() ? result : sbBuilder.toString();

        }
    }

    /**
     * 执行get请求 判断代理是否可用
     * @param url
     * @param httpHost
     * @return
     */
    public boolean isGetWithProxy(String url, HttpHost httpHost) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {

            httpClient = HttpClients.createDefault();
            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(35000)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)// 请求超时时间
                    .setSocketTimeout(60000)// 数据读取超时时间
                    .setProxy(httpHost)
                    .build();
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode()==200)
            {
                return true;
            }
        } catch (ClientProtocolException e) {
            logger.error("ClientProtocolException:" + e.getMessage());
        } catch (IOException e) {
            logger.error(" IOException:" + e.getMessage());
        }
        return false;
    }
}

