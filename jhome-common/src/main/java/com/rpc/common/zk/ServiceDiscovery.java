package com.rpc.common.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * @description: 服务发现
 * @author: Daxv
 * @create: 2020-10-08 19:32
 **/
public class ServiceDiscovery {
    protected final Logger logger = LoggerFactory.getLogger(ServiceRegistryImpl.class);
    protected CuratorFramework curatorFramework;
    protected List<String> serviceRepos = new ArrayList<String>();
    protected LoadBanalceStrategy loadBanalceStrategy = new RandomLoadBalance();
    public static final String REGISTER_ROOT = "service";

    {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("192.168.58.130:2181")
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(2000, 10))
                .build();

    }

    /**
     * 初始化服务发现
     *
     * @param serviceName
     */
    public void init(String serviceName) {
        String servicePath = REGISTER_ROOT + "/" + serviceName;
        try {
            serviceRepos = curatorFramework.getChildren().forPath(servicePath);
            registerWatcher(servicePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启用监听
     *
     * @param path
     * @throws Exception
     */
    private void registerWatcher(String path) throws Exception {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, path, true);
        PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                serviceRepos = curatorFramework.getChildren().forPath(path);
            }
        };
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
        pathChildrenCache.start();
    }

    public String getServiceRepos() {
        return loadBanalceStrategy.selectHost(serviceRepos);
    }


    public static void main(String[] args) throws InterruptedException {
        ServiceDiscovery serviceDiscovery=new ServiceDiscovery();
        serviceDiscovery.init("jhome-account");
        for (int i=0;i<10;i++)
        {
            serviceDiscovery.logger.info(serviceDiscovery.getServiceRepos());
            Thread.sleep(4000);
        }
        //拿到主机后，就可以通过 gprc thrft调用  也可以通过dubbot调用
    }
}
