package com.account.common.utils;/**
 * @program: account-root
 * @description
 * @author: Daxv
 * @create: 2020-07-25 15:53
 **/

import com.account.modules.userAuthority.model.response.LoginRes;
import com.bracket.common.Identity.UserUtil;
import com.bracket.common.ToolKit.StringUtil;
import com.domain.common.UserInfo;
import com.shiro.common.realm.ServerRedisSessionDao;

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
 * @description: 用户辅助类
 * @author: Daxv
 * @create: 2020-07-25 15:53
 **/

public class UserAuxiliary extends UserUtil {
    public UserAuxiliary() {
    }

    public ServerRedisSessionDao cDao;
    private LoginRes loginRes;

    /**
     * token 一般建议传值 此操作为了兼容 Pac4j 和 Shiro 两种认证方式 其他服务不需要，因为其他服务用 的是 Shiro认证，可以直接获取
     *
     * @param
     * @return
     */
    public LoginRes getLoginRes() {
        if (loginRes == null)
            loginRes = new LoginRes();
        UserInfo userInfo = super.GetUserInfo(sessionId -> {
            return cDao.readSession(sessionId);
        });
        if (userInfo == null)
            return null;
        loginRes.setUserId(Integer.valueOf(userInfo.getUserId()));
        loginRes.setLoginName(userInfo.getLoginName());
        loginRes.setRealName(userInfo.getRealName());
        loginRes.setPhone(userInfo.getPhone());
        loginRes.setPassword(userInfo.getPassword());
        loginRes.setProductId(Long.parseLong(userInfo.getProductId()));
        loginRes.setTenantId(Long.parseLong(userInfo.getTenantId()));
        loginRes.setGender(userInfo.getGender());
        loginRes.setRemotelyToken(userInfo.getRemotelyToken());
        loginRes.setJhomeToken(userInfo.getJhomeToken());
        loginRes.setSchoolName(userInfo.getSchoolName());
        loginRes.setRoleNames(userInfo.getRoleNames());
        return loginRes;
    }

    public ServerRedisSessionDao getCDao() {
        return cDao;
    }

    public void setCDao(ServerRedisSessionDao cDao) {
        this.cDao = cDao;
    }
}
