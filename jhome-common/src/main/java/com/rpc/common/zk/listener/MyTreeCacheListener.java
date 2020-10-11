package com.rpc.common.zk.listener;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
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
 * @create: 2020-10-09 17:02
 **/
public class MyTreeCacheListener implements TreeCacheListener {
    private static final Logger log = LoggerFactory.getLogger(MyTreeCacheListener.class);
    @Override
    public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent event) throws Exception {
        ChildData childData = event.getData();
        if (childData != null) {
            //System.out.println("Path: " + childData.getPath());
            //System.out.println("Stat:" + childData.getStat());
            //System.out.println("Data: " + new String(childData.getData()));
        }
        switch (event.getType()) {
            case INITIALIZED:
                log.info("节点初始化成功");
                break;
            case NODE_ADDED:
                log.info("添加节点路径:" + event.getData().getPath());
                log.info("节点数据:" + new String(event.getData().getData()));
                break;
            case NODE_UPDATED:
                log.info("修改节点路径:" + event.getData().getPath());
                log.info("修改节点数据:" + new String(event.getData().getData()));
                break;
            case NODE_REMOVED:
                log.info("删除节点:" + event.getData().getPath());
                break;
            case CONNECTION_LOST:
                System.out.println("连接丢失");
                break;
            case CONNECTION_SUSPENDED:
                System.out.println("连接被挂起");
                break;
            case CONNECTION_RECONNECTED:
                System.out.println("恢复连接");
                break;
        }
    }
}
