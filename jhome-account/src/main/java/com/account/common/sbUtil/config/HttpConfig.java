package com.account.common.sbUtil.config;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * 描述：HttpClient的重试处理机制
 */
@Configuration
public class HttpConfig {
    /**
     * 重试次数
     */
    private int retryTime = 3;

    /**
     * 连接池最大连接数
     */
    private int connMaxTotal = 500;

    /**
     *
     */
    private int maxPerRoute = 100;

    /**
     * 连接存活时间，单位为s
     *     
     */
    private int timeToLive = 60;

    /**
     * 连接超时
     */
    private int connectTimeout = 2000;
    /**
     * 连接请求超时
     */
    private int connectRequestTimeout = 2000;
    /**
     *
     */
    private int socketTimeout = 2000;

    @Bean
    public HttpRequestRetryHandler httpRequestRetryHandler() {
        // 请求重试
        final int retryTime = this.retryTime;
        return new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                // Do not retry if over max retry count,如果重试次数超过了retryTime,则不再重试请求
                if (executionCount >= retryTime) {
                    return false;
                }
                // 服务端断掉客户端的连接异常
                if (exception instanceof NoHttpResponseException) {
                    return true;
                }
                // time out 超时重试
                if (exception instanceof InterruptedIOException) {
                    return true;
                }
                // Unknown host
                if (exception instanceof UnknownHostException) {
                    return false;
                }
                // Connection refused
                if (exception instanceof ConnectTimeoutException) {
                    return false;
                }
                // SSL handshake exception
                if (exception instanceof SSLException) {
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };
    }

    @Bean
    public PoolingHttpClientConnectionManager poolingClientConnectionManager(){
        PoolingHttpClientConnectionManager poolHttpcConnManager = new PoolingHttpClientConnectionManager(60, TimeUnit.SECONDS);
        // 最大连接数
        poolHttpcConnManager.setMaxTotal(this.connMaxTotal);
        // 路由基数
        poolHttpcConnManager.setDefaultMaxPerRoute(this.maxPerRoute);
        return poolHttpcConnManager;
    }

    @Bean
    public RequestConfig config(){
        return RequestConfig.custom()
                .setConnectionRequestTimeout(this.connectRequestTimeout)
                .setConnectTimeout(this.connectTimeout)
                .setSocketTimeout(this.socketTimeout)
                .build();
    }


}
