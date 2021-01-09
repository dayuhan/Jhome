package com.jhome.modules.userAuthentication.controller;

import com.bracket.common.Bus.AbstractController.BaseController;
import com.bracket.common.Identity.UserUtil;
import com.bracket.common.ToolKit.CookieUtil;
import com.bracket.common.ToolKit.JSONUtils;
import com.bracket.common.ToolKit.StringUtil;
import com.domain.common.UserInfo;
import com.jhome.autoconfiguration.SysConfigurationPropertiesBean;
import com.jhome.modules.userAuthentication.service.RemoteService;
import com.shiro.common.realm.SessionCons;
import com.shiro.common.session.ShiroSession;
import com.shiro.common.token.DeviceType;
import io.buji.pac4j.subject.Pac4jPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cas 单点登录回调地址
 */
@Api(tags = "D、单点登录回调")
@RestController
@RequestMapping("/${adminPath}")
public class CasSinglePointController extends BaseController {
    @Autowired
    public RemoteService remoteService;
    //    @Autowired
//    public UserInfoService userInfoService;
    @Autowired
    public SysConfigurationPropertiesBean sysConfigurationPropertiesBean;

    @ApiOperation(value = "CAS - 单点登陆接口回调", notes = "单点登陆接口回调")
    @RequestMapping(value = {"/", "", "/index"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public void smartCampusLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            String loginName = UserUtil.GetCasPac4jInfo(request, response);
            Pac4jPrincipal principal = (Pac4jPrincipal) request.getUserPrincipal();
            if (StringUtil.isBlank(loginName)) loginName = principal.getProfile().getId();
            //单点登录，根据单点登录回传的用户id 本地的用户
            //LoginRes loginRes = userInfoService.findUser(loginName);
            String userJson = JSONUtils.beanToJson(UserUtil.getSubject().getPrincipal());
            if (SecurityUtils.getSubject().isAuthenticated()) {
                UserInfo userInfo = new UserInfo();
                userInfo.setRemotelyToken((String) UserUtil.getSubject().getSession().getId());
                userInfo.setJhomeToken((String) UserUtil.getSubject().getSession().getId());
                userInfo.setLoginName(loginName);
                userInfo.setDeviceType(DeviceType.CAS.toString());
                //...更新用户，把用户信息写入到本地缓存redis中

                //pack4j session 转换为 shirosession
                CommonProfile commonProfile = principal.getProfiles().get(0);
                Session sessionPac4j = SecurityUtils.getSubject().getSession();
                sessionPac4j.setAttribute("pac4jUserProfiles", commonProfile);
                ShiroSession session = new ShiroSession();
                session.setAttribute(SessionCons.DEVICE_TYPE, DeviceType.CAS.toString());
                //获取本地用户信息，解析用户
                session.setAttribute(SessionCons.LOGIN_USER_SESSION, userInfo.toString());
                session.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
                session.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, UserUtil.getSubject().getSession().getId());
                session.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
                session.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED, true);
                session.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED, true);
                session.setAttribute("pac4jUserProfiles", JSONUtils.beanToJson(commonProfile));
                session.setId(userInfo.getJhomeToken());
                try {
                    //判断，如果redis中存在用户，则无需写入
                    if (remoteService.getSession(userInfo.getJhomeToken()) == null)
                        remoteService.doCreateByUserInfo(session, userInfo);
                } catch (Exception ex) {
                    remoteService.doCreateByUserInfo(session, userInfo);
                }
                Cookie cookie = CookieUtil.get(request, "JSESSIONID");
                cookie.setMaxAge(1000);
                response.addCookie(cookie);
                //跳转前段
                String url = String.format("%s?token=%s", sysConfigurationPropertiesBean.getCasConfig().redirectUrl, userInfo.getJhomeToken());
                response.sendRedirect(url);
            }
        } catch (Exception ex) {
            logger.debug(String.format("回调函数报错：%s", ex.getMessage()));
        }
    }
}