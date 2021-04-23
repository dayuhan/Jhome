package com.translate.conmmon;

import com.bracket.common.Identity.UserUtil;
import com.domain.common.UserInfo;
import com.shiro.common.session.ClientSessionDAO;

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
 * @description: 获取用户信息
 * @author: Daxv
 * @create: 2020-08-25 15:56
 **/
public class UserAuxiliary {
    public ClientSessionDAO clientSessionDAO;
    public UserInfo userInfo;

    public UserAuxiliary() {
    }

    public UserInfo getUserInfo() {
        return UserUtil.GetUserInfo(sessionId -> {
            return clientSessionDAO.readSession(sessionId);
        });
    }

    public ClientSessionDAO getClientSessionDAO() {
        return clientSessionDAO;
    }

    public void setClientSessionDAO(ClientSessionDAO clientSessionDAO) {
        this.clientSessionDAO = clientSessionDAO;
    }
}
