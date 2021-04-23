package com.rpc.common.zk.scoket;

import com.rpc.common.thrift.socketService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
 * @description: 模板类 生产 socketService.Client 对象
 * @author: Daxv
 * @create: 2021-03-26 09:27
 **/
public abstract class DoDbTSocketAbstract {
    protected static final Logger LOGGER = LoggerFactory.getLogger(DoDbTSocketInvoked.class.getName());
    protected volatile static List<String> services = new ArrayList<>();//负载服务器节点
    protected static Map<String, List<String>> hostMaps = new HashMap<>();//可用服务器
    protected static Socket socket = new Socket();
    protected static DbTSocketExpand dbTSocketExpand;

    static {
        try {
            dbTSocketExpand = new DbTSocketExpand(socket);
        } catch (TTransportException e) {
            e.printStackTrace();
            LOGGER.error(String.format("创建dbTSocketExpand对象失败！失败原因：%s", e.getMessage()));
        }
    }

    /**
     * 获取客户端请求
     *
     * @param serviceName
     */
    public socketService.Client GetClient(String serviceName) {
        TProtocol tProtocol = null;
        try {
            List<String> hosts = doGetHost(serviceName, hostMaps);
            if (hosts.size() == 0) {
                LOGGER.error("************** 暂无提供可用的服务 **************");
                return null;
            }
            //执行负载均衡算法 生产目标对象
            doLoadAlgorithm(hosts, dbTSocketExpand);
            TFramedTransport tFramedTransport = new TFramedTransport(dbTSocketExpand);
            tProtocol = new TCompactProtocol(tFramedTransport);
        } catch (Exception ex) {
            LOGGER.error(String.format("实例化DbTSocket报错，报错信息：%s", ex.getMessage()));
        }
        return new socketService.Client(tProtocol);
    }

    /**
     * 获取服务负载IP
     *
     * @param serviceName
     * @param hostMaps
     * @return 返回目标ip
     */
    public List<String> doGetHost(String serviceName, Map<String, List<String>> hostMaps) {
        if (hostMaps.size() == 0) {
            LOGGER.error("********************** 注册中心暂无服务 ********************** ");
        }
        return hostMaps.get(serviceName);
    }

    /**
     * 调用负载算法 请求目标服务器
     *
     * @param hosts
     * @param dbTSocketExpand
     * @return
     */
    public abstract String doLoadAlgorithm(List<String> hosts, DbTSocketExpand dbTSocketExpand) throws IOException;


}
