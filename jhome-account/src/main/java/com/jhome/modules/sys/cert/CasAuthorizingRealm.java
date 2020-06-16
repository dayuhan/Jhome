package com.jhome.modules.sys.cert;

import com.jhome.common.shiro.realm.BaseAuthorizingRealm;
import com.jhome.common.shiro.realm.jhomeToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
/**
 * 单点登录 认证
 * @author : Daxv
 * @date : 11:03 2020/5/12 0012
 */
public class CasAuthorizingRealm extends BaseAuthorizingRealm {


    @Override
    public boolean supports(AuthenticationToken token){
        return token != null && token instanceof jhomeToken;
    }

    @Override
    protected SimpleAuthenticationInfo Verification(jhomeToken token) {
        return null;
    }
}