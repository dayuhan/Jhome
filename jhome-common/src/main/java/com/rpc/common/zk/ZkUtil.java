package com.rpc.common.zk;

import com.bracket.common.ToolKit.PropertiesUtil;
import com.rpc.common.zk.listener.AbstractNodeCacheListener;
import com.rpc.common.zk.listener.AbstractTreeCacheListener;
import com.rpc.common.zk.listener.SessionConnectionStateListener;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLBackgroundPathAndBytesable;
import org.apache.curator.framework.api.BackgroundPathAndBytesable;
import org.apache.curator.framework.api.BackgroundPathable;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

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
 * @create: 2020-10-09 16:38
 **/

/**
 * zk工具类
 */
public class ZkUtil {
    private static final Logger log = LoggerFactory.getLogger(ZkUtil.class);
    public static String NAMESPACE = "jhome";
    public static Properties pu = PropertiesUtil.loadProperties("config/zkconfig.properties");
    public static CuratorFramework client;
    public static String env = "DEV";

    public static synchronized void initialize() {
        String zkhost = null;
        int sessionTimeout = 60000;
        int connTimeout = 3000;
        try {
            log.info("read classpath:application.properties, env: " + env);
            if (pu != null) {
                zkhost = pu.getProperty(env + "_ZK_SERVER");
                sessionTimeout = Integer.valueOf(pu.getProperty("sessionTimeout", "60000")).intValue();
                connTimeout = Integer.valueOf(pu.getProperty("connTimeout", "3000")).intValue();
                client = createClient(zkhost, sessionTimeout, connTimeout, NAMESPACE);
            }
        } catch (Exception e) {
            log.error("init zkUtil error!", e);
        }
    }

    public static CuratorFramework createClient(String zkhost, int sessionTimeout, int connTimeout, String nameSpace) {
        CuratorFramework client = null;
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.builder().connectString(zkhost)
                .sessionTimeoutMs(sessionTimeout)
                .connectionTimeoutMs(connTimeout)
                .canBeReadOnly(false)
                .retryPolicy(retryPolicy)
                //会在路径前面补齐  /zkconfig/node ----> /nameSpace/zkconfig/node
                .namespace(nameSpace)
                .defaultData(null).build();
        client.getConnectionStateListenable().addListener(new SessionConnectionStateListener());
        client.start();
        return client;
    }

    /**
     * 判断节点是否存在
     *
     * @param path
     * @return
     */
    public static boolean checkExist(String path) {
        try {
            if (client.checkExists().forPath(path) == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("checkExist error! path: " + path, e);
        }
        return false;
    }

    /**
     * 判断节点是否存在并监听节点
     *
     * @param path
     * @param watcher
     * @return
     */
    public static boolean checkExist(String path, Watcher watcher) {
        try {
            if (client.checkExists().usingWatcher(watcher).forPath(path) == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("checkExist error! path: " + path, e);
        }
        return false;
    }

    /**
     * 获取存在的节点数据并监听节点变更
     *
     * @param path
     * @param watcher
     * @return
     */
    public static String getData(String path, Watcher watcher) {
        byte[] data = null;
        String res = null;
        try {
            data = (byte[]) ((BackgroundPathable) client.getData().usingWatcher(watcher)).forPath(path);
            res = new String(data, "utf-8");
        } catch (Exception e) {
            log.error("get data error! path: " + path, e);
        }
        return res;
    }

    /**
     * 获取节点数据
     *
     * @param path
     * @return
     */
    public static String getData(String path) {
        byte[] data = null;
        String res = null;
        try {
            data = (byte[]) client.getData().forPath(path);
            res = new String(data, "utf-8");
        } catch (Exception e) {
            log.error("get data error! path: " + path, e);
        }
        return res;
    }

    /* 分布式队列
    public static SimpleDistributedQueue createDisQueue(CuratorFramework client, String taskName, String name) {
        SimpleDistributedQueue queue = new SimpleDistributedQueue(client, ROOT_TASK + "/" + taskName + "/" + name);
        return queue;
    }
    */

    /**
     * 创建持久节点
     *
     * @param path
     * @return
     */
    public static boolean create(String path) {
        return create(path, "");
    }

    /**
     * 创建持久节点和数据
     *
     * @param path
     * @param data
     * @return
     */
    public static boolean create(String path, String data) {
        try {
            ((BackgroundPathAndBytesable) ((ACLBackgroundPathAndBytesable) client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)).
                    withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE))
                    .forPath(path, data.getBytes("utf-8"));
            return true;
        } catch (Exception e) {
            log.error("创建Zookeeper节点出现异常,nodePath:{0}", path, e);
        }
        return false;
    }

    /**
     * 增加节点
     *
     * @param path
     * @param childNode
     * @return
     */
    public static boolean addNode(String path, String childNode) {
        return !checkExist(path + "/" + childNode) ? create(path + "/" + childNode) : true;
    }

    /**
     * 增加节点
     *
     * @param path
     * @param childNode
     * @param data
     * @return
     */
    public static boolean addNode(String path, String childNode, String data) {
        if (data == null) {
            data = "";
        }
        return !checkExist(path + "/" + childNode) ? create(path + "/" + childNode, data) : true;
    }

    /**
     * 删除某个子节点
     *
     * @param path
     * @param childNode
     * @return
     * @throws Exception
     */
    public static boolean deleteNode(String path, String childNode) {
        return checkExist(path + "/" + childNode) ? deleteChildrenIfNeeded(path + "/" + childNode) : true;
    }

    /**
     * 级联删除某个Zookeeper节点及其子节点
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static boolean deleteChildrenIfNeeded(String path) {
        try {
            client.delete().deletingChildrenIfNeeded().forPath(path);
            return true;
        } catch (Exception e) {
            log.error("删除Zookeeper节点出现异常,nodePath:{}", path, e);
        }
        return false;
    }

    /**
     * 新增持久节点或者更新节点数据
     *
     * @param path
     * @param data
     * @throws Exception
     */
    public static void createOrUpdateNode(String path, String data) {
        try {
            if (!checkExist(path)) {
                ((BackgroundPathAndBytesable) ((ACLBackgroundPathAndBytesable) client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)).forPath(path, data.getBytes("utf-8"));
            } else {
                client.setData().forPath(path, data.getBytes("utf-8"));
            }
        } catch (Exception e) {
            log.error("创建或更新Zookeeper节点出现异常,nodePath:{}", path, e);
        }
    }

    /**
     * 更新节点数据
     *
     * @param path
     * @param data
     * @throws Exception
     */
    public static void updateNodeData(String path, String data) {
        try {
            client.setData().forPath(path, data.getBytes("utf-8"));
        } catch (Exception e) {
            log.error("更新Zookeeper节点出现异常,nodePath:{}", path, e);
        }
    }

    /**
     * 创建永久Zookeeper节点
     *
     * @param nodePath  节点路径（如果父节点不存在则会自动创建父节点），如：/curator
     * @param nodeValue 节点数据
     * @return java.lang.String 返回创建成功的节点路径
     */
    public static String createPersistentNode(String nodePath, String nodeValue) {
        try {
            return client.create().creatingParentsIfNeeded()
                    .forPath(nodePath, nodeValue.getBytes("utf-8"));
        } catch (Exception e) {
            log.error("创建永久Zookeeper节点失败,nodePath:{},nodeValue:{}", nodePath, nodeValue, e);
        }
        return null;
    }

    /**
     * 创建永久有序Zookeeper节点
     *
     * @param nodePath  节点路径（如果父节点不存在则会自动创建父节点），如：/curator
     * @param nodeValue 节点数据
     * @return java.lang.String 返回创建成功的节点路径
     */
    public static String createSequentialPersistentNode(String nodePath, String nodeValue) {
        try {
            return client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                    .forPath(nodePath, nodeValue.getBytes("utf-8"));
        } catch (Exception e) {
            log.error("创建永久有序Zookeeper节点失败,nodePath:{},nodeValue:{}", nodePath, nodeValue, e);
        }
        return null;
    }


    /**
     * 创建临时Zookeeper节点
     *
     * @param nodePath  节点路径（如果父节点不存在则会自动创建父节点），如：/curator
     * @param nodeValue 节点数据
     * @return java.lang.String 返回创建成功的节点路径
     */
    public static String createEphemeralNode(String nodePath, String nodeValue) {
        try {
            return client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(nodePath, nodeValue.getBytes("utf-8"));
        } catch (Exception e) {
            log.error("创建临时Zookeeper节点失败,nodePath:{},nodeValue:{}", nodePath, nodeValue, e);
        }
        return null;
    }

    /**
     * 创建临时有序Zookeeper节点
     *
     * @param nodePath  节点路径（如果父节点不存在则会自动创建父节点），如：/curator
     * @param nodeValue 节点数据
     * @return java.lang.String 返回创建成功的节点路径
     */
    public static String createSequentialEphemeralNode(String nodePath, String nodeValue) {
        try {
            return client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(nodePath, nodeValue.getBytes("utf-8"));
        } catch (Exception e) {
            log.error("创建临时有序Zookeeper节点失败,nodePath:{},nodeValue:{}", nodePath, nodeValue, e);
        }
        return null;
    }

    /**
     * 获取某个Zookeeper节点的所有子节点
     *
     * @param nodePath 节点路径
     * @return java.util.List<java.lang.String> 返回所有子节点的节点名
     */
    public static List<String> getChildren(String nodePath) {
        try {
            return client.getChildren().forPath(nodePath);
        } catch (Exception e) {
            log.error("获取某个Zookeeper节点的所有子节点出现异常,nodePath:{}", nodePath, e);
        }
        return null;
    }

    /**
     * 注册节点监听器
     * NodeCache: 对一个节点进行监听，监听事件包括指定路径的增删改操作
     *
     * @param nodePath 节点路径
     * @param listener 监控事件的回调接口
     * @return void
     */
    public static NodeCache registerNodeCacheListener(String nodePath, AbstractNodeCacheListener listener) {
        try {
            //1. 创建一个NodeCache
            NodeCache nodeCache = new NodeCache(client, nodePath);

            //2. 缓存NodeCache
            listener.setNodeCache(nodeCache);

            //3. 添加节点监听器
            nodeCache.getListenable().addListener(listener);

            //4. 启动监听器
            nodeCache.start();

            //5. 返回NodeCache
            return nodeCache;
        } catch (Exception e) {
            log.error("注册节点监听器出现异常,nodePath:{}", nodePath, e);
        }
        return null;
    }

    /**
     * 注册目录中子目录监听器
     * PathChildrenCache：对指定路径节点的一级子目录监听，不对该节点的操作监听，对其子目录的增删改操作监听
     * 备注: 当节点存在时，添加监听事件后会触发 NODE_ADDED
     *
     * @param nodePath 节点路径
     * @param listener 监控事件的回调接口
     * @return org.apache.curator.framework.recipes.cache.PathChildrenCache
     */
    public static PathChildrenCache registerPathChildListener(String nodePath, PathChildrenCacheListener listener) {
        try {
            //1. 创建一个PathChildrenCache
            PathChildrenCache pathChildrenCache = new PathChildrenCache(client, nodePath, true);

            //2. 添加目录监听器
            pathChildrenCache.getListenable().addListener(listener);

            //3. 启动监听器
            pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);

            //4. 返回PathChildrenCache
            return pathChildrenCache;
        } catch (Exception e) {
            log.error("注册子目录监听器出现异常,nodePath:{}", nodePath, e);
        }
        return null;
    }

    /**
     * 注册目录监听器
     * TreeCache：综合NodeCache和PathChildrenCahce的特性，可以对整个目录进行监听，同时还可以设置监听深度
     * 备注: 当节点存在时，添加监听事件后会触发 NODE_ADDED
     *
     * @param nodePath 节点路径
     * @param maxDepth 自定义监控深度
     * @param listener 监控事件的回调接口
     * @return org.apache.curator.framework.recipes.cache.TreeCache
     */
    public static TreeCache registerTreeCacheListener(String nodePath, int maxDepth, TreeCacheListener listener) {
        try {
            //1. 创建一个TreeCache
            TreeCache treeCache = TreeCache.newBuilder(client, nodePath)
                    .setCacheData(true)
                    .setMaxDepth(maxDepth)
                    .build();

            //2. 添加目录监听器
            treeCache.getListenable().addListener(listener);

            //3. 启动监听器
            treeCache.start();

            //4. 返回TreeCache
            return treeCache;
        } catch (Exception e) {
            log.error("注册目录监听器出现异常,nodePath:{},maxDepth:{}", nodePath, e);
        }
        return null;
    }

}