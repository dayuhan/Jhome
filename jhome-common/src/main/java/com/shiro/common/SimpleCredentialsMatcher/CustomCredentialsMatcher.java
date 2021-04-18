package com.shiro.common.SimpleCredentialsMatcher;

import com.alibaba.fastjson.JSONObject;
import com.ar.common.util.StringUtil;
import com.bracket.common.ToolKit.MD5Util;
import com.domain.common.UserInfo;
import com.shiro.common.token.DeviceType;
import com.shiro.common.token.jhomeToken;
import lombok.SneakyThrows;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import java.security.cert.TrustAnchor;

/**
 * 密码比较器：规范是extends SimpleCredentialsMatcher
 *
 * @author : Daxv
 * @date : 11:03 2020/5/12 0012
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    /**
     * 重写父类的密码比较的方法
     * 比较的算法：
     * 1.接收用户在密码框输入的明文
     * 2.使用Md5Hash算法进行加密
     * 3.获取数据库中的加密的密码进行比较
     * <p>
     * 第一个参数token:代表用户在界面上输入的用户名和密码
     * 第二个参数info:它内部会包含数据库中的密码（当前用户加密后的密码）
     * <p>
     * 返回值：如果返回true代表密码验证成功，如果返回false代表密码比对失败，失败后程序就会出现异常
     */
    @SneakyThrows
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        //1.向下转型
        jhomeToken jhomeToken = (com.shiro.common.token.jhomeToken) token;
        //过滤单点登录
        if (DeviceType.IsSinglepointLoginContainType(jhomeToken.getDeviceType()))
            return true;
        //2.获取用户名或密码
        String username = jhomeToken.getUsername();
        String userInfoStr = info.getPrincipals().toString();
        UserInfo userInfo = JSONObject.parseObject(userInfoStr, UserInfo.class);
        String pwd = new String(upToken.getPassword());
        //获取密码并使用Md5Hash算法进行加密
        //String inputPwdEncrypt = MD5Util.md5(new String(jhomeToken.getPassword()), "account");
        String inputPwdEncrypt = StringUtil.hashPassword(pwd,userInfo.getSalt());
        //3.获取数据库中的加密的密码
        String dbPwd = info.getCredentials().toString();
        return equals(inputPwdEncrypt, dbPwd);
        //return true;


    }
}
