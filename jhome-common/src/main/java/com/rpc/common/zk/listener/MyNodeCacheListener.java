package com.rpc.common.zk.listener;

import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
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
 * @description: 监听本节点
 * @author: Daxv
 * @create: 2020-10-09 16:58
 **/
public class MyNodeCacheListener extends AbstractNodeCacheListener  {

    private static final Logger log = LoggerFactory.getLogger(MyNodeCacheListener.class);

    public MyNodeCacheListener() {
        super();
    }

    public MyNodeCacheListener(NodeCache nodeCache) {
        super(nodeCache);
    }

    @Override
    public void nodeChanged() throws Exception {
        ChildData childData = this.getNodeCache().getCurrentData();
        /**
         * 创建和更新 childData 不为 null
         * 删除  childData 为 null
         */
        if(childData != null){
            log.info("Path: " + childData.getPath());
            log.info("Stat:" + childData.getStat());
            log.info("Data: "+ new String(childData.getData(),"utf-8"));
        }
    }
}
