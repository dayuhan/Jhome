package com.rpc.common.zk.listener;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
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
 * @description: 监听注册目录中子目录，监听器
 * @author: Daxv
 * @create: 2020-10-10 14:32
 **/
public abstract class AbstractPathChildrenCacheListener implements PathChildrenCacheListener {
    private static final Logger log = LoggerFactory.getLogger(MyNodeCacheListener.class);

    @Override
    public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
        log.info("AbstractPathChildrenCacheListener 开始调用 doChildEvent()");
        this.doChildEvent(curatorFramework, event);
        log.info("AbstractPathChildrenCacheListener 开始完毕！");

    }

    public abstract void doChildEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent);
}