package com.rpc.common.zk.loadBanalce;/**
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-10-08 19:47
 **/

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
 * @program: jhome-root
 * @description: 抽象负载均衡算法
 * @author: Daxv
 * @create: 2020-10-08 19:47
 **/
public abstract class AbstractLoadBanalce implements LoadBanalceStrategy {
    @Override
    public String selectHost(List<String> serviceRepos) {
        if (serviceRepos == null || serviceRepos.size() == 0)
            return null;
        if (serviceRepos != null && serviceRepos.size() == 1)
            return serviceRepos.get(0);
        return doSelectHose(serviceRepos);
    }

    protected abstract String doSelectHose(List<String> serviceRepos);
}
