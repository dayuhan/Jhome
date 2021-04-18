package com.rpc.common.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 //
 //                       .::::.
 //                     .::::::::.
 //                    :::::::::::
 //                 ..:::::::::::'
 //              '::::::::::::'
 //                .::::::::::
 //           '::::::::::::::..
 //                ..::::::::::::.
 //              ``::::::::::::::::
 //               ::::``:::::::::'        .:::.
 //              ::::'   ':::::'       .::::::::.
 //            .::::'      ::::     .:::::::'::::.
 //           .:::'       :::::  .:::::::::' ':::::.
 //          .::'        :::::.:::::::::'      ':::::.
 //         .::'         ::::::::::::::'         ``::::.
 //     ...:::           ::::::::::::'              ``::.
 //    ```` ':.          ':::::::::'                  ::::..
 //                       '.:::::'                    ':'````..
 * @program: account-root
 * @description: 实现服务注册
 * @author: Daxv
 * @create: 2020-10-08 19:14
 **/
public class ServiceRegistryImpl implements ServiceRegistry {
    protected final Logger logger= LoggerFactory.getLogger(ServiceRegistryImpl.class);
    protected CuratorFramework curatorFramework;
    public static final String REGISTER_ROOT="service";
    {
        curatorFramework=  CuratorFrameworkFactory.builder()
                .connectString("192.168.58.130:2181")
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(2000,10))
                .build();

    }

    @Override
    public void register(String serviceName, String serviceAddress) throws Exception {
        String servicePath=REGISTER_ROOT+"/"+serviceName;
        if (curatorFramework.checkExists().forPath(servicePath)==null)
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(servicePath,"0".getBytes());
        String addressPath=servicePath+serviceAddress;
        String none=curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(addressPath,"0".getBytes());
        logger.info("节点创建成功");
    }

    public static void main(String[] args) throws Exception {
        ServiceRegistryImpl serviceRegistry=new ServiceRegistryImpl();
        serviceRegistry.register("account-account","127.0.0.1:8100");
        serviceRegistry.register("account-fileStore","127.0.0.1:8300");

    }

}
