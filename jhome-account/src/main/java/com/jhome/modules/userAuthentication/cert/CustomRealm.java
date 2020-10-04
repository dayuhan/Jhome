package com.jhome.modules.userAuthentication.cert;

import com.bracket.common.Identity.UserUtil;
import com.domain.common.UserInfo;
import com.shiro.common.realm.ServerBaseAuthorizingRealm;
import com.shiro.common.token.DeviceType;
import com.shiro.common.token.jhomeToken;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 后台登录数据Realm
 *
 * @author : Daxv
 * @date : 11:03 2020/5/12 0012
 */
public class CustomRealm extends ServerBaseAuthorizingRealm {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && token instanceof jhomeToken;
    }

    @Override
    protected SimpleAuthenticationInfo Verification(jhomeToken token) {
        try {
            String userName= (String) token.getPrincipal();
            userName = token.getUsername();
            String deviceType=token.getDeviceType();
            String pws = new String(((char[]) token.getCredentials()));
            //业务认证....
            UserInfo userInfo = this.resolveUser(userName);
            return new SimpleAuthenticationInfo(userInfo.toString(), userInfo.getPassword(), this.getName());
        } catch (Exception ex) {
            logger.info("认证报错：%s",ex.getMessage());
        }
        return null;
    }

    /**
     * 根据用户传递过来的用户名从数据库读取本地用户信息
     * @param userName
     * @return
     */
    public UserInfo resolveUser(String userName)
    {
        //获取本地用户
        //...
        //更新公共用户类
        UserInfo userInfo=new UserInfo();
        String jhomeToken= (String) UserUtil.getSubject().getSession().getId();
        userInfo.setJhomeToken(jhomeToken);
        userInfo.setDeviceType(DeviceType.PC.toString());
        //补充其他相关参数...
        return userInfo;
    }

    @Override
    protected SimpleAuthorizationInfo GetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
