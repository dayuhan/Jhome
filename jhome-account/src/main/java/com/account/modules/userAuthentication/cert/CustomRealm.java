package com.account.modules.userAuthentication.cert;

import com.account.modules.userAuthority.model.request.CommonUserIdRequest;
import com.account.modules.userAuthority.model.response.GetSysDataResponse;
import com.account.modules.userAuthority.model.response.LoginRes;
import com.account.modules.userAuthority.service.SysDataRoleService;
import com.account.modules.userAuthority.service.UserInfoService;
import com.bracket.common.Identity.UserUtil;
import com.domain.common.SysMenu;
import com.domain.common.UserDataRoles;
import com.domain.common.UserInfo;
import com.shiro.common.realm.ServerBaseAuthorizingRealm;
import com.shiro.common.token.DeviceType;
import com.shiro.common.token.jhomeToken;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台登录数据Realm
 *
 * @author : Daxv
 * @date : 11:03 2020/5/12 0012
 */
public class CustomRealm extends ServerBaseAuthorizingRealm {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SysDataRoleService sysDataRoleService;

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
    public UserInfo resolveUser(String userName) throws Exception {
        //获取本地用户
        LoginRes loginRes = userInfoService.findUser(userName);
        //更新公共用户类
        UserInfo userInfo=new UserInfo();
        String jhomeToken= (String) UserUtil.getSubject().getSession().getId();
        userInfo.setJhomeToken(jhomeToken);
        userInfo.setDeviceType(DeviceType.PC.toString());
        userInfo.setDeviceType(DeviceType.PC.toString());
        userInfo.setUserId(loginRes.getUserId().toString());
        userInfo.setLoginName(loginRes.getLoginName());
        userInfo.setGender(loginRes.getGender());
        userInfo.setPhone(loginRes.getPhone());
        userInfo.setProductId("1271318084763017218");
        userInfo.setRealName(loginRes.getRealName());
        userInfo.setRemotelyToken(loginRes.getRemotelyToken());
        userInfo.setRoleNames(loginRes.getRoleNames());
        userInfo.setSchoolName(loginRes.getSchoolName());
        userInfo.setTenantId("189516514267758612");
        userInfo.setPassword(loginRes.getPassword());
        userInfo.setSalt(loginRes.getSalt());

        CommonUserIdRequest commonUserIdRequest = new CommonUserIdRequest();
        commonUserIdRequest.setUserId(Long.valueOf(userInfo.getUserId()));
        List<GetSysDataResponse> getSysDataResponses = sysDataRoleService.findRoleDataByUserId(commonUserIdRequest);

        if (getSysDataResponses.size() > 0)
            userInfo.setUserDataRoles(
                    getSysDataResponses.stream().map(c -> {
                        UserDataRoles userDataRoles = new UserDataRoles();
                        BeanUtils.copyProperties(c,userDataRoles);
                        return userDataRoles;
                    }).collect(Collectors.toList()));

        if (loginRes.getSysMenuResponses() != null && loginRes.getSysMenuResponses().size() > 0)
            userInfo.setSysMenu(loginRes.getSysMenuResponses().stream().map(c -> {
                SysMenu sysMenu = new SysMenu();
                BeanUtils.copyProperties(c,sysMenu);
                return sysMenu;
            }).collect(Collectors.toList()));
        return userInfo;
    }

    @Override
    protected SimpleAuthorizationInfo GetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
