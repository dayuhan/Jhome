package com.account.modules.userAuthentication.cert;

import com.shiro.common.realm.ServerBaseAuthorizingRealm;
import com.shiro.common.token.jhomeToken;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 移动端 认证
 * @author : Daxv
 * @date : 11:03 2020/5/12 0012
 */
public class AppShiroRealm extends ServerBaseAuthorizingRealm {
    @Override
    public boolean supports(AuthenticationToken token){
        return token != null && token instanceof jhomeToken;
    }

    @Override
    protected SimpleAuthenticationInfo Verification(jhomeToken token) {
        return null;
    }

    @Override
    protected SimpleAuthorizationInfo GetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
