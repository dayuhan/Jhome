package com.position.modules.upPosition.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.api.dubbo.entity.TestInfo;
import com.api.dubbo.service.TestDubboRemoteInterface;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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
 * @description: 上传坐标 消费者
 * @author: Daxv
 * @create: 2020-10-16 14:11
 **/
@RestController
public class testController {
    @Reference
    private TestDubboRemoteInterface testDubboRemoteInterface;

    @GetMapping(value = "/test/{name}",produces= MediaType.APPLICATION_JSON_VALUE)
    public TestInfo getTestInfo(@PathVariable("name") String name)
    {
        TestInfo testInfo=new TestInfo();
        testInfo.setUserName(name);
        testInfo.setUserId(UUID.randomUUID().toString());
        return testDubboRemoteInterface.GetTestInfo(testInfo);
    }
}
