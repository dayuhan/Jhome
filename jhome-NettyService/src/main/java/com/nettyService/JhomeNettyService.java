package com.nettyService;

import com.nettyService.common.MyPathChildrenCacheListener;
import com.nettyService.config.EnableSpring;
import com.nettyService.server.AppContext;
import com.rpc.common.zk.ZkUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


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
 * @description: JhomeNettyService  消息中心
 * @author: Daxv
 * @create: 2017-08-02 13:51
 **/
@EnableSpring
public class JhomeNettyService {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(JhomeNettyService.class);
        AppContext appContext = applicationContext.getBean(AppContext.class);
        try {
            appContext.run();
        } catch (Exception ex) {
            appContext.close();
        }
    }

}
