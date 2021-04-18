package com.account.modules.userAuthentication.cert;

import com.domain.common.UserInfo;
import com.shiro.common.token.DeviceType;
import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jPrincipal;
import io.buji.pac4j.token.Pac4jToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 单点登录 认证
 * @author : Daxv
 * @date : 11:03 2020/5/12 0012
 */
public class CasRealm extends Pac4jRealm {
    private String clientName;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        try {
            if (authenticationToken instanceof Pac4jToken) {
                final Pac4jToken pac4jToken = (Pac4jToken) authenticationToken;
                final List<CommonProfile> commonProfileList = pac4jToken.getProfiles();
                final CommonProfile commonProfile = commonProfileList.get(0);
                System.out.println("单点登录返回的信息" + commonProfile.toString());
                //todo
                final Pac4jPrincipal principal = new Pac4jPrincipal(commonProfileList, getPrincipalNameAttribute());
                String loginName = principal.getProfile().getId();
                Session session = SecurityUtils.getSubject().getSession();
                //session.setAttribute("userSessionId", loginName);
                UserInfo userInfo=new UserInfo();
                userInfo.setUserId(loginName);
                userInfo.setDeviceType(DeviceType.CAS.toString());
                final PrincipalCollection principalCollection = new SimplePrincipalCollection(principal, getName());
                return new SimpleAuthenticationInfo(principalCollection, commonProfileList.hashCode());
                //return new SimpleAuthenticationInfo(JSONUtils.beanToJson(userInfo), "", this.getName());
            }
        }
        catch (Exception ex)
        {
            logger.info(String.format("单点登陆报错：%s", ex.getMessage().toString()));
        }
        return null;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        List<String> permissions = new ArrayList<>(10);
//        permissions.add("user:info");
//        simpleAuthorizationInfo.addStringPermissions(permissions);
//        Object user = super.getAvailablePrincipal(principals);
//        return simpleAuthorizationInfo;
        SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
        authInfo.addStringPermission("user");
        return authInfo;
        //return super.doGetAuthorizationInfo(principals);
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && token instanceof Pac4jToken;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
