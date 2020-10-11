package com.rpc.common.zk.listener;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
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
 * @description:
 * @author: Daxv
 * @create: 2020-10-09 16:56
 **/

/**
 * 监听Session连接状态
 */
public class SessionConnectionStateListener implements ConnectionStateListener {
    private static final Logger log = LoggerFactory.getLogger(SessionConnectionStateListener.class);
    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
        if (connectionState == ConnectionState.LOST)
        {
            log.info("开始连接: lost");
            try
            {
                for (;;)
                {
                    if (curatorFramework.getZookeeperClient().blockUntilConnectedOrTimedOut())
                    {
                        log.info("连接成功");
                        break;
                    }
                }
                return;
            }
            catch (InterruptedException e)
            {
                log.info("连接失败");
            }
            catch (Exception e)
            {
                log.info("连接失败");
            }
        }
    }
}
