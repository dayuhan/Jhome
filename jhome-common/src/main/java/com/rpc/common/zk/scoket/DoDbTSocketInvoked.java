package com.rpc.common.zk.scoket;

import com.alibaba.fastjson.JSONObject;
import com.rpc.common.zk.ZkUtil;
import com.rpc.common.zk.loadBanalce.LoadBanalceStrategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
 * @program: jhome-root
 * @description: 代理对象类
 * @author: Daxv
 * @create: 2021-03-26 09:15
 **/
public class DoDbTSocketInvoked extends DoDbTSocketAbstract implements Invoked {
    LoadBanalceStrategy loadBanalceStrategy = null;

    public DoDbTSocketInvoked(LoadBanalceStrategy loadBanalceStrategy) {
        this.loadBanalceStrategy = loadBanalceStrategy;
    }

    /**
     * 监控服务名称
     *
     * @param serverName
     */
    @Override
    public void doStart(String serverName) {
        try {
            ZkUtil.initialize();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        hostMaps.clear();
                        services = ZkUtil.getChildren("/");
                        //service转换 hasmap
                        services.stream().forEach(c -> {
                            String path = String.format("/%s", c);
                            String host = ZkUtil.getData(path);
                            addHostMap(host, c, hostMaps);
                        });
                        LOGGER.info(String.format("JhomeSocket 注册表信息：%s", JSONObject.toJSONString(services)));
                    } catch (Exception ex) {
                        LOGGER.error(String.format("连接超时%s", ex.getMessage()));
                    }
                }
            };
            //定时器线程
            ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
            service.scheduleAtFixedRate(runnable, 0, 60, TimeUnit.SECONDS);
        } catch (Exception ex) {
            LOGGER.error(String.format("连接超时%s", ex.getMessage()));
        }

    }

    protected void addHostMap(String host, String serverName, Map<String, List<String>> hostMaps) {
        serverName = serverName.substring(0, serverName.indexOf(0));
        List<String> stringList = hostMaps.get(serverName);
        if (stringList == null)
            stringList = new ArrayList<>();
        if (!stringList.stream().filter(c -> c.equals(host)).findFirst().isPresent())
            stringList.add(host);
        hostMaps.put(serverName, stringList);
    }

    @Override
    public String doLoadAlgorithm(List<String> hosts, DbTSocketExpand dbTSocketExpand) throws IOException {
        if (hosts.size() == 0)
            LOGGER.error(String.format("DubboExpand 提示：注册中心暂无服务"));
        //Expand...
        String host = loadBanalceStrategy.selectHost(hosts);
        String[] hostArr = host.split(":");
        dbTSocketExpand.setHost_(hostArr[0]);
        dbTSocketExpand.setPort_(Integer.valueOf(hostArr[1]));
        return host;
    }
}
