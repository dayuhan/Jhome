package com.nettyService.server;

import com.nettyService.common.MyPathChildrenCacheListener;
import com.nettyService.config.ConfigInfo;
import com.rpc.common.zk.ZkUtil;
import org.apache.thrift.server.THsHaServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : Daxv
 * @date : 23:18 2020/5/19 0019
 */

@Component
public class AppContext {
    @Autowired
    public ConfigInfo configInfo;
    @Autowired
    private WebSocketServer webSocketServer;
    @Autowired
    private NotificationServer notificationServer;
    @Autowired
    private THsHaServer tHsHaServer;
    private final Logger logger = LoggerFactory.getLogger(AppContext.class);
    public AppContext() {
    }

    /**
     * 启动服务器
     */
    public void run() {
        try {
            logger.info(
                    "" + "" +
                            "  _                                                                       _    _____                 _          \n" +
                            " | |                       /\\                                           | |  / ____|               (_)         \n" +
                            " | |    _   ___  ________ /  \\   __ _ _ __ ___  ___ _ __ ___   ___ _ __ | |_| (___   ___ _ ____   ___  ___ ___ \n" +
                            " | |   | | | \\ \\/ /______/ /\\ \\ / _` | '__/ _ \\/ _ \\ '_ ` _ \\ / _ \\ '_ \\| __|\\___ \\ / _ \\ '__\\ \\ / / |/ __/ _ \\\n" +
                            " | |___| |_| |>  <      / ____ \\ (_| | | |  __/  __/ | | | | |  __/ | | | |_ ____) |  __/ |   \\ V /| | (_|  __/\n" +
                            " |______\\__,_/_/\\_\\    /_/    \\_\\__, |_|  \\___|\\___|_| |_| |_|\\___|_| |_|\\__|_____/ \\___|_|    \\_/ |_|\\___\\___|\n" +
                            "                                 __/ |                                                                         \n" +
                            "                                |___/                                                                          " +
                            "" + "Spring 3.0 " +
                            "" +
                            "");

            startZookeeper();
            Thread.sleep(2000);
            logger.info("启动消息预警推送服务...");
            new  Thread(notificationServer).start();
            logger.info("启动WebSocket服务器...");
            new Thread(webSocketServer.InitWebSocketServer(configInfo.getWebSocketPort())).start();
            logger.info("WebSocket启动完毕...");
            logger.info("启动RPC...");
            Thread.sleep(4000);
            tHsHaServer.serve();
            logger.info("RPC启动完毕！...");
        } catch (Exception ex) {
            logger.info(String.format("启动服务器失败：s%", ex.getMessage().toString()));
            this.close();
        }
    }

    /**
     * 关闭服务器
     */
    public void close() {
        logger.info("关闭启动Netty WebSocket服务器！...");
        webSocketServer.close();
        logger.info("关闭RPC服务器！...");
        tHsHaServer.stop();
    }

    public static void startZookeeper()
    {
        ZkUtil.initialize();
        String parent = "/zkconfig";
        String child = "chynode";
        String path = parent + "/" + child;
        ZkUtil.registerPathChildListener(parent,new MyPathChildrenCacheListener());
    }
}
