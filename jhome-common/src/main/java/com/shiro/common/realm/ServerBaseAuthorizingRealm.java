package com.shiro.common.realm;

import com.shiro.common.token.DeviceType;
import com.shiro.common.token.jhomeToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统安全认证实现类
 *
 * @author : Daxv
 * @date : 11:03 2020/5/12 0012
 */
public abstract class ServerBaseAuthorizingRealm extends AuthorizingRealm {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //设备判断
        Subject subject = SecurityUtils.getSubject();
        String deviceType = (String) subject.getSession().getAttribute(
                SessionCons.DEVICE_TYPE);
        if (deviceType.equals(DeviceType.APP.toString())) {//true则往下执行

        }
        System.out.println("授权");
        SimpleAuthorizationInfo simpleAuthorizationInfo=this.GetAuthorizationInfo(principalCollection);
        return simpleAuthorizationInfo;
        //return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1.向下转型
        jhomeToken upToken;
        // TODO Auto-generated method stub
        try {
            //Token合法性验证
            if (!(authenticationToken instanceof jhomeToken))
                return null;
            upToken = (jhomeToken) authenticationToken;
            SimpleAuthenticationInfo simpleAuthenticationInfo = this.Verification(upToken);
            //用户验证
            return simpleAuthenticationInfo;
        } catch (Exception ex) {
            logger.info(ex.getMessage().toString());
        }
        return null;
    }

    /**
     * 实现验证 acc系统实现数据认证 远程系统通过客户端sessionDAO 调用远程ServerSessionDAO 实现session共享
     * @param token
     * @return
     */
    protected abstract SimpleAuthenticationInfo Verification(jhomeToken token);

    /**
     * 获取授权 ACC自身实现授权调用 远程调用通过远程接口访问API获取权限信息
     * @param principalCollection
     * @return
     */
    protected abstract SimpleAuthorizationInfo GetAuthorizationInfo(PrincipalCollection principalCollection);


}
