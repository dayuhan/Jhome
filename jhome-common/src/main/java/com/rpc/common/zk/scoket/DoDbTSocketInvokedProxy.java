package com.rpc.common.zk.scoket;

import com.rpc.common.zk.loadBanalce.LoadBanalceStrategy;

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
 * @description:代理对象
 * @author: Daxv
 * @create: 2021-03-26 10:40
 **/
public class DoDbTSocketInvokedProxy implements Invoked {
    LoadBanalceStrategy loadBanalceStrategy = null;

    public DoDbTSocketInvokedProxy(LoadBanalceStrategy loadBanalceStrategy) {
        this.loadBanalceStrategy = loadBanalceStrategy;
    }
    @Override
    public void doStart(String serviceName) {
        DoDbTSocketInvoked dbTSocketInvoked = new DoDbTSocketInvoked(loadBanalceStrategy);
        dbTSocketInvoked.doStart(serviceName);
    }
}
