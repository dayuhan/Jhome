package com.bracket.common.Identity;/**
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-07-25 12:28
 **/

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.session.Session;

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
 * @description: 用户认证处理类
 * @author: Daxv
 * @create: 2020-07-25 12:28
 **/
public interface UserInfoHandler {
    Session invoke(String sessionId);
}
