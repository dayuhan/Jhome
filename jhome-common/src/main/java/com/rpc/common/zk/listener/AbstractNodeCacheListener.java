package com.rpc.common.zk.listener;

import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
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
 * @description:  对一个节点进行监听，监听事件包括指定路径的增删改操作
 * @author: Daxv
 * @create: 2020-10-09 16:57
 **/
public class AbstractNodeCacheListener implements NodeCacheListener {
    private static final Logger log = LoggerFactory.getLogger(AbstractNodeCacheListener.class);

    private NodeCache nodeCache;

    public AbstractNodeCacheListener(){

    }

    public AbstractNodeCacheListener(NodeCache nodeCache) {
        this.nodeCache = nodeCache;
    }

    public NodeCache getNodeCache() {
        return nodeCache;
    }

    public void setNodeCache(NodeCache nodeCache) {
        this.nodeCache = nodeCache;
    }
    @Override
    public void nodeChanged() throws Exception {

    }
}
