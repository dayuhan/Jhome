package com.jhome.common.shiro.realm;

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

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("1111111111111111");
        //用户授权
/*        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        PermissionContext context = remoteService.getPermissions(appKey, username);
        authorizationInfo.setRoles(context.getRoles());
        authorizationInfo.setStringPermissions(context.getPermissions());*/
        return null;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用永远不会被调用");
        throw new UnsupportedOperationException("永远不会被调用");
    }


}