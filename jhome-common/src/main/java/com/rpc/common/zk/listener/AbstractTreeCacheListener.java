package com.rpc.common.zk.listener;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * @description: 监听本节点和子目录
 * @author: Daxv
 * @create: 2020-10-10 14:30
 **/
public abstract class AbstractTreeCacheListener implements TreeCacheListener {
    private static final Logger log = LoggerFactory.getLogger(MyNodeCacheListener.class);
    @Override
    public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
        log.info("AbstractTreeCacheListener 开始调用 doChildEvent()");
        this.doChildEvent(curatorFramework, treeCacheEvent);
        log.info("AbstractTreeCacheListener 开始完毕！");

    }

    public abstract void doChildEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent);
}
