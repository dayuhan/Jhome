package com.appPush.modules.position.service;
import com.alibaba.dubbo.config.annotation.Service;
import com.api.dubbo.entity.TestInfo;
import com.api.dubbo.service.TestDubboRemoteInterface;
import org.springframework.stereotype.Component;

/**
 //
 //                       .::::.
 //                     .::::::::.
 //                    :::::::::::
 //                 ..:::::::::::'
 //              '::::::::::::'
 //                .::::::::::
 //           '::::::::::::::..
 //                ..::::::::::::.
 //              ``::::::::::::::::
 //               ::::``:::::::::'        .:::.
 //              ::::'   ':::::'       .::::::::.
 //            .::::'      ::::     .:::::::'::::.
 //           .:::'       :::::  .:::::::::' ':::::.
 //          .::'        :::::.:::::::::'      ':::::.
 //         .::'         ::::::::::::::'         ``::::.
 //     ...:::           ::::::::::::'              ``::.
 //    ```` ':.          ':::::::::'                  ::::..
 //                       '.:::::'                    ':'````..
 * @program: jhome-root
 * @description: 实现测试类
 * @author: Daxv
 * @create: 2020-10-16 13:59
 **/
@Component
// 该service是org.apache.dubbo.config.annotation.Service
@Service
public class TestDubboRemoteImpl implements TestDubboRemoteInterface {
    @Override
    public TestInfo GetTestInfo(TestInfo testInfo) {
        TestInfo testInfo1=new TestInfo();
        testInfo1.setUserName(testInfo.getUserName());
        return testInfo1;
    }
}
