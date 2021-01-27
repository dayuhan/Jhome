package com.jhome.modules.userAuthentication.controller;

import com.alibaba.fastjson.JSONObject;
import com.bracket.common.Bus.AbstractController.BaseController;
import com.bracket.common.Bus.ResponseJson;
import com.bracket.common.Identity.UserUtil;
import com.bracket.common.ToolKit.JSONUtils;
import com.bracket.common.ToolKit.StringUtil;
import com.domain.common.UserInfo;
import com.jhome.autoconfiguration.SysConfigurationPropertiesBean;
import com.jhome.modules.userAuthentication.service.RemoteService;
import com.shiro.common.realm.SessionCons;
import com.shiro.common.session.ShiroSession;
import com.shiro.common.token.DeviceType;
import com.shiro.common.token.jhomeToken;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "Z、账户服务-登录API")
@RestController
@RequestMapping("/${adminPath}")
public class AccountController extends BaseController {
    @Autowired
    public SysConfigurationPropertiesBean sysConfigurationPropertiesBean;
    @Autowired
    public RemoteService remoteService;


    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseJson login(HttpServletRequest request, HttpServletResponse response, @RequestBody UserInfo user) {
        try {
            if (user == null || StringUtil.isBlank(user.getLoginName()) || StringUtil.isBlank(user.getPassword()))
                return new ResponseJson().error("用户名密码不能为空！");
            if (!DeviceType.toList().contains(user.getDeviceType()))
                return new ResponseJson().error("登陆设备类型不能存在！");
            Subject subject = SecurityUtils.getSubject();
            jhomeToken token = new jhomeToken(user.getLoginName(), user.getPassword(), 0, user.getDeviceType());
            token.setRememberMe(true);
            subject.login(token);
            if (subject.isAuthenticated()) {
                String userJson = (String) subject.getPrincipal();
                Session session = subject.getSession();
                session.setAttribute(SessionCons.DEVICE_TYPE, user.getDeviceType());
                session.setAttribute(SessionCons.LOGIN_USER_SESSION, userJson);
                String tokenStr = (String) session.getId();
                JSONObject jsonObject = (JSONObject) JSONObject.parse(userJson);
                if (StringUtil.isNotBlank(jsonObject.getString("jhomeToken"))) {
                    return new ResponseJson()
                            .success()
                            .setValue("data", jsonObject);
                } else {
                    return new ResponseJson()
                            .error("用户登陆失败");
                }

            }
        } catch (Exception ex) {
            return new ResponseJson().error("登陆失败");
        }

        return null;
    }


    //退出
    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")

    public String logout(@RequestParam("luxToken") String jhomeToken, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtil.isBlank(jhomeToken))
                return new ResponseJson().error("token不能为空！").toString();
            if (sysConfigurationPropertiesBean.getCasConfig().getIsEnable().equals("false")) {
                Subject subject = UserUtil.getSubject();
                Session session = subject.getSession();
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("Token", jhomeToken);
                session.getAttribute("LOGIN_USER_SESSION");
                //第三方认证
                /*
                ResponseEntity<JSONObject> jsonObject = ucService.loginOut(ucApiConfigProperties.getLoginOut(), HttpMethod.GET, jsonObj, JSONObject.class);
                if (jsonObject.getBody().getString("returnCode").equals("200")) {
                    if (session != null) {
                        remoteService.deleteSession(session);
                        UserUtil.loginOut();
                    }
                }*/
                if (session != null) {
                    remoteService.deleteSession(session);
                    UserUtil.loginOut();
                }
            } else {
                ShiroSession session = new ShiroSession();
                session.setId(jhomeToken);
                remoteService.deleteSession(session);
            }
        } catch (Exception ex) {
            return new ResponseJson().error(ex.getMessage()).toString();
        }
        return new ResponseJson().success().setValue("callbackUrl", sysConfigurationPropertiesBean.getCallbackUrl()).toString();
    }
}
