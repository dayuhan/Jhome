package com.account.common.sbUtil.service.impl;//package com.account.common.sbUtil.service.impl;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.client.HttpRequestRetryHandler;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.conn.HttpClientConnectionManager;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.FactoryBean;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * 描述：HttpClient客户端封装
// */
//@Service("httpClientManagerFactoryBen")
//@Slf4j
//public class HttpClientManagerFactoryBen implements FactoryBean<CloseableHttpClient>, InitializingBean, DisposableBean {
//
//    /**
//     * FactoryBean生成的目标对象
//     */
//    private CloseableHttpClient client;
//
//    @Autowired
//    private HttpRequestRetryHandler httpRequestRetryHandler;
//
//
//    @Autowired
//    private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;
//
//    @Autowired
//    private RequestConfig config;
//
//    private IdleConnectionMonitorThread idleConnectionMonitor = null;
//
//    @Override
//    public void destroy() throws Exception {
//        /*
//           * 调用httpClient.close()会先shut down connection manager，然后再释放该HttpClient所占用的所有资源，
//           * 关闭所有在使用或者空闲的connection包括底层socket。由于这里把它所使用的connection manager关闭了，
//           * 所以在下次还要进行http请求的时候，要重新new一个connection manager来build一个HttpClient,
//           * 也就是在需要关闭和新建Client的情况下，connection manager不能是单例的.
//           */
//        log.info("=========HttpClientManagerFactoryBen destroy");
//        if (null != this.client) {
//            this.client.close();
//        }
//        idleConnectionMonitor.shutdown();
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        /*
//         * 建议此处使用HttpClients.custom的方式来创建HttpClientBuilder，而不要使用HttpClientBuilder.create()方法来创建HttpClientBuilder
//         * 从官方文档可以得出，HttpClientBuilder是非线程安全的，但是HttpClients确实Immutable的，immutable 对象不仅能够保证对象的状态不被改变，
//         * 而且还可以不使用锁机制就能被其他线程共享
//         */
//        this.client = HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager)
//                .setRetryHandler(httpRequestRetryHandler)
//                .setDefaultRequestConfig(config)
//                .build();
//        idleConnectionMonitor = new IdleConnectionMonitorThread(poolingHttpClientConnectionManager);
//        idleConnectionMonitor.start();
//    }
//
//    @Override
//    public CloseableHttpClient getObject() throws Exception {
//        return this.client;
//    }
//
//    @Override
//    public Class<?> getObjectType() {
//        return (this.client == null ? CloseableHttpClient.class : this.client.getClass());
//    }
//
//    @Override
//    public boolean isSingleton() {
//        return true;
//    }
//
//    public static class IdleConnectionMonitorThread extends Thread {
//
//        private final HttpClientConnectionManager connMgr;
//        private volatile boolean shutdown;
//
//        private static final int MONITOR_INTERVAL_MS = 15000;
//        private static final int IDLE_ALIVE_MS = 30000;
//
//
//        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
//            super();
//            this.connMgr = connMgr;
//        }
//
//        @Override
//        public void run() {
//            try {
//                while (!shutdown) {
//                    synchronized (this) {
//                        wait(MONITOR_INTERVAL_MS);
//                        // 关闭失效的连接
//                        connMgr.closeExpiredConnections();
//                        // 可选的, 关闭30秒内不活动的连接
//                        connMgr.closeIdleConnections(IDLE_ALIVE_MS, TimeUnit.SECONDS);
//                    }
//                }
//            } catch (InterruptedException ex) {
//                // terminate
//                log.info("=========IdleConnectionMonitorThread InterruptedException");
//            }
//            log.info("=========IdleConnectionMonitorThread exit");
//        }
//
//        public void shutdown() {
//            shutdown = true;
//            synchronized (this) {
//                notifyAll();
//            }
//        }
//    }
//
//}
