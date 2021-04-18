package com.account.common;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 后台登录数据Realm
 * 系统安全认证实现类
 */
public class ClientRealm extends AuthorizingRealm {

    //通过RPC调用
    @Autowired
    public UserRemoteServiceInterface remoteService;
    public String appKey;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //用户授权
        /*
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        PermissionContext context = remoteService.getPermissions(appKey, username);
        authorizationInfo.setRoles(context.getRoles());
        authorizationInfo.setStringPermissions(context.getPermissions());*/
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用");
        return null;
    }
}
