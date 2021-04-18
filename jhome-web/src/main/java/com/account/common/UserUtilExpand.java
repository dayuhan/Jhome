package com.account.common;

import com.bracket.common.Identity.UserUtil;
import com.domain.common.UserInfo;
import com.shiro.common.session.ClientSessionDAO;

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
 * @program: account-root
 * @description: 获取用户信息辅助类
 * @author: Daxv
 * @create: 2020-08-28 11:54
 **/
public class UserUtilExpand {
    public ClientSessionDAO clientSessionDAO;
    public UserInfo userInfo;

    public UserUtilExpand() {
    }

    public ClientSessionDAO getClientSessionDAO() {
        return clientSessionDAO;
    }

    public void setClientSessionDAO(ClientSessionDAO clientSessionDAO) {
        this.clientSessionDAO = clientSessionDAO;
    }

    public UserInfo getUserInfo() {
        return UserUtil.GetUserInfo(sessionId -> {
            return clientSessionDAO.readSession(sessionId);
        });
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
