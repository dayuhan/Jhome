package com.rpc.common.zk;

import com.alibaba.fastjson.JSONObject;
import com.rpc.common.zk.listener.MyNodeCacheListener;
import com.rpc.common.zk.listener.MyPathChildrenCacheListener;
import com.rpc.common.zk.listener.MyTreeCacheListener;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * @description:
 * @author: Daxv
 * @create: 2020-10-09 17:23
 **/
public class testCreate extends TestCase {
    protected final Logger log= LoggerFactory.getLogger(this.getClass());

  /*  public    void testCreate(){
        ZkUtil.initialize();
        String parent = "/zkconfig";
        String child = "chynode";
        String path = parent + "/" + child;
        NodeWatcher nodeWatcher = new NodeWatcher(ZkUtil.client, path);

        //开启监听
        ZkUtil.checkExist("/zkconfig/chynode", nodeWatcher);

        //如果不存在节点增加节点
        try {
            if (!ZkUtil.checkExist(path)) {
                ZkUtil.addNode(parent, child, "chy");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //如果存在节点获取节点信息
        if (ZkUtil.checkExist(path)) {
            log.info("{}:{}", path, ZkUtil.getData("/zkconfig/chynode"));
        }
    }

    public   void testDelete(){
        ZkUtil.initialize();
        String parent = "/zkconfig";
        String child = "chynode";
        String path = parent + "/" + child;
        NodeWatcher nodeWatcher = new NodeWatcher(ZkUtil.client, path);

        //如果不存在节点增加节点
        try {
            if (!ZkUtil.checkExist(path)) {
                ZkUtil.addNode(parent, child, "chy");
                ZkUtil.addNode(parent, "chytemp", "chy");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> paths = ZkUtil.getChildren(parent);

        log.info("paths:{}", JSONObject.toJSONString(paths));

        //删除子节点
        ZkUtil.deleteNode(parent, child);

        paths = ZkUtil.getChildren(parent);

        log.info("paths:{}", JSONObject.toJSONString(paths));

        //删除当前节点以及子节点
        ZkUtil.deleteChildrenIfNeeded(parent);

        paths = ZkUtil.getChildren(parent);

        log.info("paths:{}", JSONObject.toJSONString(paths));

    }*/


    public     void testSequentialEphemeralNode(){
        ZkUtil.initialize();
        String parent = "/zkconfig";
        String child = "chynode";
        String path = parent + "/" + child;

        ZkUtil.deleteChildrenIfNeeded(parent);

        log.info(ZkUtil.createSequentialEphemeralNode(path,"192.168.1.10:8100"));
       log.info(ZkUtil.createSequentialEphemeralNode(path,"192.168.1.10:8200"));
       log.info(ZkUtil.createSequentialEphemeralNode(path,"192.168.1.10:8300"));

        List<String> paths = ZkUtil.getChildren(parent);

        log.info("paths:{}", JSONObject.toJSONString(paths));
    }

    public   void testSequentialPersistentNode(){
        ZkUtil.initialize();
        String parent = "/zkconfig";
        String child = "chynode";
        String path = parent + "/" + child;

        ZkUtil.deleteChildrenIfNeeded(parent);

        log.info(ZkUtil.createSequentialPersistentNode(path,"001"));
        log.info(ZkUtil.createSequentialPersistentNode(path,"002"));
        log.info(ZkUtil.createSequentialPersistentNode(path,"003"));

        List<String> paths = ZkUtil.getChildren(parent);

        log.info("paths:{}", JSONObject.toJSONString(paths));
    }

    public   void testPersistentNode(){
        ZkUtil.initialize();
        String parent = "/zkconfig";
        String child = "chynode";
        String path = parent + "/" + child;

        ZkUtil.deleteChildrenIfNeeded(parent);

        log.info(ZkUtil.createEphemeralNode(path,"001"));

        List<String> paths = ZkUtil.getChildren(parent);

        log.info("paths:{}", JSONObject.toJSONString(paths));
    }


    public  void testNodeCacheListener() throws InterruptedException {
        ZkUtil.initialize();
        String parent = "/zkconfig";
        String child = "chynode";
        String path = parent + "/" + child;

        MyNodeCacheListener myNodeCacheListener=new MyNodeCacheListener();

        //注册当前节点监听器
        ZkUtil.registerNodeCacheListener(path,myNodeCacheListener);

        //如果不存在节点增加节点
        try {
            if (!ZkUtil.checkExist(path)) {
                ZkUtil.addNode(parent, child, "chy");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(10000);

    }

    public  void testPathChildrenCacheListener() throws InterruptedException {
        ZkUtil.initialize();
        String parent = "/zkconfig";
        String child = "chynode";
        String path = parent + "/" + child;

        MyPathChildrenCacheListener myPathChildrenCacheListener=new MyPathChildrenCacheListener();

        //子节点监听器
        ZkUtil.registerPathChildListener(parent,myPathChildrenCacheListener);

        //如果不存在节点增加节点
        try {
            if (!ZkUtil.checkExist(path)) {
                ZkUtil.addNode(parent, child, "chy");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(10000);
    }

    public  void testTreeCacheListener(){
        ZkUtil.initialize();
        String parent = "/zkconfig";
        String child = "chynode";
        String path = parent + "/" + child;

        MyTreeCacheListener treeCacheListener = new MyTreeCacheListener();

        // 0 只监听 /zkconfig
        //ZkUtil.registerTreeCacheListener(parent, 0, treeCacheListener);

        // 1 监听 /zkconfig 和 下一级节点
        ZkUtil.registerTreeCacheListener(parent, 1, treeCacheListener);

        //如果不存在节点增加节点
        try {
            if (!ZkUtil.checkExist(path)) {
                ZkUtil.addNode(parent, child, "chy");
            }
            if (ZkUtil.checkExist(parent)) {
                ZkUtil.updateNodeData(parent, "root");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
