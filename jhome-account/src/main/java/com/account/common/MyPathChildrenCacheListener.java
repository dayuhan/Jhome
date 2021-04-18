package com.account.common;/**
 * @program: account-root
 * @description
 * @author: Daxv
 * @create: 2020-10-10 17:58
 **/

import com.rpc.common.zk.listener.AbstractPathChildrenCacheListener;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
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
 * @description: 服务注册
 * @author: Daxv
 * @create: 2020-10-10 17:58
 **/
public class MyPathChildrenCacheListener extends AbstractPathChildrenCacheListener {
    private   final Logger log=  LoggerFactory.getLogger(MyPathChildrenCacheListener.class);
    @Override
    public void doChildEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) {

        switch (event.getType()) {
            case INITIALIZED:
                //必须异步模式 PathChildrenCache.StartMode.POST_INITIALIZED_EVENT
                log.info("子节点初始化成功");
                break;
            case CHILD_ADDED:
                log.info("添加子节点路径:" + event.getData().getPath());
                log.info("添加子节点数据:" + new String(event.getData().getData()));
                break;
            case CHILD_UPDATED:
                log.info("修改子节点路径:" + event.getData().getPath());
                log.info("修改子节点数据:" + new String(event.getData().getData()));
                break;
            case CHILD_REMOVED:
                log.info("删除子节点:" + event.getData().getPath());
                break;
            case CONNECTION_LOST:
                log.info("连接丢失");
                break;
            case CONNECTION_SUSPENDED:
                log.info("连接被挂起");
                break;
            case CONNECTION_RECONNECTED:
                log.info("恢复连接");
                break;
        }
    }
}
