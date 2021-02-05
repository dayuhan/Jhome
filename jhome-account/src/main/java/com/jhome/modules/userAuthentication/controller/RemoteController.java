package com.jhome.modules.userAuthentication.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bracket.common.Bus.AbstractController.BaseController;
import com.bracket.common.ToolKit.JSONUtils;
import com.domain.common.PermissionContext;
import com.jhome.autoconfiguration.SysConfigurationPropertiesBean;
import com.jhome.modules.userAuthentication.service.RemoteService;
import com.shiro.common.SessionDaoZH;
import com.shiro.common.session.ShiroSession;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@Api(tags = "S、服务器远程调用接口")
@Controller
@RequestMapping(value = "/${foreignServerPath}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)//解决汉字转换问题
public class RemoteController extends BaseController {

    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    public RemoteService remoteService;
    @Autowired
    public SysConfigurationPropertiesBean propertiesBean;

    @PostMapping(value = "/getSession")
    @ResponseBody
    public String getSession(@RequestParam("sessionId") String sessionId) {
        try {
            ShiroSession shiroSession = (ShiroSession) remoteService.getSession(sessionId);
            return JSONUtils.beanToJson(shiroSession);
        } catch (Exception ex) {
            logger.error(String.format("createSession error :%s", ex.getMessage().toString()));
        }
        return "";
    }

    @PostMapping(value = "/createSession")
    @ResponseBody
    public String createSession(@RequestParam("sessionJson") String sessionJson) {
        try {
            ShiroSession shiroSession = JSON.parseObject(sessionJson, ShiroSession.class);
            SessionDaoZH.SerializedStringToAttributeBean(shiroSession);
            Serializable sessionId = remoteService.createSession(shiroSession);
            return (String) sessionId;
        } catch (Exception ex) {
            logger.error(String.format("createSession error :%s", ex.getMessage().toString()));
        }
        return "";
    }

    @PostMapping(value = "/updateSession")
    @ResponseBody
    public String updateSession(@RequestParam("sessionJson") String sessionJson) {
        try {
            ShiroSession shiroSession = JSON.parseObject(sessionJson, ShiroSession.class);
            SessionDaoZH.SerializedStringToAttributeBean(shiroSession);
            remoteService.updateSession(shiroSession);
            return String.format("UpdateSession success :200");

        } catch (Exception ex) {
            logger.error(String.format("UpdateSession error :%s", ex.getMessage().toString()));
            return String.format("UpdateSession error :%s", ex.getMessage().toString());
        }
    }

    @PostMapping(value = "/deleteSession")
    @ResponseBody
    public String deleteSession(@RequestParam("sessionJson") String sessionJson) {
        try {
            ShiroSession shiroSession = JSON.parseObject(sessionJson, ShiroSession.class);

            String userJson = (String) shiroSession.getAttribute("session_login_user");
            JSONObject userInfoObject = JSON.parseObject(userJson);
            String token = userInfoObject.getJSONObject("data").getString("token");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", token);
            //通过接口远程成功后再销毁本地Session
            // ResponseEntity<JSONObject> jsonObject= ucServer.loginOut(url,jsonObject,JSONObject.class)
            //if(jsonObject.getBody().getString("")).equealls("200")
            if (1 == 1) {
            }
            remoteService.deleteSession(shiroSession);
            return "true";
        } catch (Exception ex) {
            logger.error(String.format("deleteSession error :%s", ex.getMessage().toString()));
            return "false";
        }
    }

    @PostMapping(value = "/getPermissions")
    @ResponseBody
    public PermissionContext getPermissions(String username) {
        return remoteService.getPermissions(username);
    }

    /**
     * 返回退出地址
     * @return
     */
    @PostMapping(value = "/getCallbackUrl")
    @ResponseBody
    public String getCallbackUrl() {
        return propertiesBean.getCallbackUrl();
    }
}
