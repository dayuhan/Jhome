package com.account.modules.userAuthentication.service;

import com.domain.common.PermissionContext;
import com.domain.common.UserInfo;
import com.shiro.common.realm.ServerRedisSessionDao;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 远程服务器暴露权限接口
 */
@Service
public class RemoteService {

    @Autowired
    public ServerRedisSessionDao rsDao;//对外暴露服务
    @Autowired
    public AuthorizationService authorizationService;//获取用户角色

    public Session getSession(Serializable sessionId) {
        return rsDao.readSession(sessionId);
    }

    public Serializable createSession(Session session) {
        return rsDao.create(session);
    }

    public void updateSession(Session session) {
        rsDao.update(session);
    }

    public void deleteSession(Session session) {
        rsDao.delete(session);
    }

    public PermissionContext getPermissions(String username) {
        PermissionContext permissionContext = new PermissionContext();
   /*     permissionContext.setRoles(authorizationService.findRoles(appKey, username));
        permissionContext.setPermissions(authorizationService.findPermissions(appKey, username));*/
        return permissionContext;
    }

    public Serializable doCreateByUserInfo(Session session, UserInfo userInfo) {
        return rsDao.doCreateByUserInfo(session, userInfo);
    }

}
