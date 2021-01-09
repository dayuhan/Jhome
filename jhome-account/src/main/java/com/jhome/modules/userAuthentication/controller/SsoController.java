package com.jhome.modules.userAuthentication.controller;

import com.bracket.common.Bus.AbstractController.BaseController;
import com.bracket.common.Bus.ResponseJson;
import com.bracket.common.Identity.UserUtil;
import com.bracket.common.ToolKit.StringUtil;
import com.jhome.modules.userAuthentication.service.RemoteService;
import com.shiro.common.realm.SessionCons;
import com.shiro.common.session.ShiroSession;
import com.shiro.common.token.DeviceType;
import com.shiro.common.token.jhomeToken;
import io.swagger.annotations.Api;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "Z、账户服务-token认证")
@RestController
public class SsoController extends BaseController {
    @Autowired
    public RemoteService remoteService;

    /**
     * 单点登录（如已经登录，则直接跳转）
     * <p>
     * username         登录用户名（loginCode）
     * token            单点登录令牌，令牌组成：sso密钥+用户名+日期，进行md5加密，举例：
     * // 注意如果 shiro.sso.encryptKey 为 true，则 secretKey 会自动加密。
     * String secretKey = Global.getConfig("shiro.sso.secretKey");
     * String token = Md5Utils.md5(secretKey + username + DateUtils.getDate("yyyyMMdd"));
     * （JSON格式），或 param_ 前缀的请求参数。
     * url              登录成功后跳转的url地址。
     * 是否强制重新登录，需要强制重新登录传递true
     * ：
     * http://localhost/project/sso/{username}/{token}?url=/userAuthentication/user/list?p1=v1%26p2=v2&relogin=true
     * 如果url中携带参数，请使用转义字符，如“&”号，使用“%26”转义。
     */
    @GetMapping(value = "/sso/{type}/{name}/{token}")
    public String sso(@PathVariable String type,
                      @PathVariable String name,
                      String token,
                      HttpServletRequest request,
                      HttpServletResponse response) {

        if (StringUtil.isBlank(name))
            return new ResponseJson().error("用户名不能为空！").toString();
        if (StringUtil.isBlank(type))
            return new ResponseJson().error("登录设备类型不能为空！").toString();
        if (!DeviceType.toList().contains(type))
            return new ResponseJson().error("登录设备类型不存在！").toString();

        try {
            // 如果已经登录，并且是同一个人，并且不强制重新登录，则直接跳转到目标页
            if (StringUtil.isNotBlank(token)) {
                ShiroSession shiroSession = (ShiroSession) remoteService.getSession(token);
                if (shiroSession != null)
                    return new ResponseJson().successByMessAge("您已经登录，无需二次登陆！").setValue("jhomeToken", shiroSession.getId()).toString();
            }
            // 通过令牌登录系统
            jhomeToken jhomeToken = new jhomeToken(name, "", 0, type);
            UserUtil.getSubject().login(jhomeToken);
            if (UserUtil.getSubject().isAuthenticated()) {
                String userJosn = (String) UserUtil.getSubject().getPrincipal();
                Session session = UserUtil.getSubject().getSession();
                session.setAttribute(SessionCons.DEVICE_TYPE, DeviceType.CAS.toString());
                session.setAttribute(SessionCons.LOGIN_USER_SESSION, userJosn);
                return new ResponseJson()
                        .success()
                        .setValue("token", UserUtil.getSubject()
                                .getSession()
                                .getId())
                        .toString();

            }

        } catch (AuthenticationException e) {
            return new ResponseJson().error("msg:登录失败，请联系管理员！").toString();

        }
        return new ResponseJson().error("msg:登录失败，请联系管理员！").toString();
    }
}
