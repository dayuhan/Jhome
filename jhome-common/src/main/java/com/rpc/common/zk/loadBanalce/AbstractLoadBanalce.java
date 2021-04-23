package com.rpc.common.zk.loadBanalce;
import com.rpc.common.zk.scoket.DoDbTSocketInvoked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
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
 * @program: account-root
 * @description: 抽象负载均衡算法
 * @author: Daxv
 * @create: 2020-10-08 19:47
 **/
public abstract class AbstractLoadBanalce implements LoadBanalceStrategy {
    protected final Logger LOGGER = LoggerFactory.getLogger(DoDbTSocketInvoked.class.getName());

    @Override
    public String selectHost(List<String> serviceRepos) throws IOException {
        if (serviceRepos == null || serviceRepos.size() == 0)
            return null;
        if (serviceRepos != null && serviceRepos.size() == 1)
            return serviceRepos.get(0);
        String host = doSelectHose(serviceRepos);
        //判断服务是否可用
        if (isAvailable(host))
            return host;
        else
            //不可用递归
            selectHost(serviceRepos);
        return null;
    }

    //验证服务是否可用
    protected boolean isAvailable(String host) throws IOException {
        String[] hostArr = host.split(":");
        return doIsConnect(hostArr[0], Integer.valueOf(hostArr[1]));
    }

    /**
     * 判断端口是否可用
     *
     * @param server
     * @param port
     * @return
     */
    protected boolean doIsConnect(String server, int port) throws IOException {
        InetAddress address = InetAddress.getByName(server);
        boolean state = address.isReachable(1000);
        if (state) {
            LOGGER.error("************DubboExpand提示：服务不可用！************");
            return false;
        }
        Socket socket = new Socket();
        socket.setReceiveBufferSize(port);
        socket.setSoTimeout(1000);
        SocketAddress socketAddress = new InetSocketAddress(server, port);
        try {
            socket.connect(socketAddress, 1000);
            return true;
        } catch (Exception ex) {
            LOGGER.error("************DubboExpand提示：新建一个Socket server连接失败！************");

        }
        return false;
    }

    protected abstract String doSelectHose(List<String> serviceRepos);
}
