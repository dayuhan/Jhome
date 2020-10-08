package com.rpc.common.zk;/**
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-10-08 19:52
 **/

import java.util.List;
import java.util.Random;

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
 * @description: 随机选取主机
 * @author: Daxv
 * @create: 2020-10-08 19:52
 **/
public class RandomLoadBalance extends AbstractLoadBanalce {
    @Override
    protected String doSelectHose(List<String> serviceRepos) {
        int lenght = serviceRepos.size();
        Random random = new Random();
        return serviceRepos.get(random.nextInt(lenght));
    }
}
