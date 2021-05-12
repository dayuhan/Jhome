package com.account.common.feign;

import com.account.modules.config.FeignConfig;
import com.pubInterFace.FileStorageInterface;
import org.springframework.cloud.openfeign.FeignClient;

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
 * @description: 文件服务 configuration = FeignConfig.class 解决上传组件为空的问题
 * @author: Daxv
 * @create: 2021-05-12 17:14
 **/
@FeignClient(value = "jhome-fileStore",configuration = FeignConfig.class,fallback = ServiceBFeignClientFallback.class)
public interface FileServiceRemoteInterface extends FileStorageInterface {
}
