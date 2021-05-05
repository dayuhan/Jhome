package com.shiro.common.filter;

import com.alibaba.fastjson.JSON;
import com.bracket.common.Bus.ResponseJson;
import com.bracket.common.Http.HttpUtil;
import com.bracket.common.Identity.UserUtil;
import com.bracket.common.ToolKit.StringUtil;
import com.shiro.common.session.ClientSessionDAO;
import com.shiro.common.session.RemoteBaseInterface;
import com.shiro.common.session.ShiroSession;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-06-04 22:22
 **/
public class ClientTokenFormAuthenticationFilter extends ClientFormAuthenticationFilter {
    private ClientSessionDAO cDao;
    private RemoteBaseInterface remoteService;

    /**
     * 单点登录拦截
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        ResponseJson result = new ResponseJson();
        Subject subject = getSubject(request, response);
        //获取URL参数上的Token
        String token = UserUtil.GetToken(request, response);
        //只允许单点登录进入
        //跨浏览器，跨电脑访问，需要根据第一次Tokne 重新绘制本地session
        if (StringUtil.isNotBlank(token) && !subject.isAuthenticated()) {
            try {
                cDao.setSsoToken(token);
                //String sessionId = (String) UserUtil.ParsingToken(token);//解析token 后台登录没有加密，无需解密
                ShiroSession shiroSession = JSON.parseObject(remoteService.getSession(token), ShiroSession.class);
                if (shiroSession == null) {
                    result.error("登录已经失效，请重新登录！").setValue("callbackUrl",remoteService.getCallbackUrl());
                    HttpUtil.SendFlush(response, result);
                    return false;
                }
                subject.getSession();
                return true;
            } catch (Exception ex) {
                result.error("登录已经失效，请重新登录！").setValue("callbackUrl",remoteService.getCallbackUrl());
                HttpUtil.SendFlush(response, result);
                return false;
            }
        }
        if (StringUtil.isBlank(token) && !subject.isAuthenticated()) {
            result.error("登录已经失效，请重新登录！").setValue("callbackUrl",remoteService.getCallbackUrl());
            HttpUtil.SendFlush(response, result);
            return false;
        }
        return subject.isAuthenticated();
    }

    public ClientSessionDAO getCDao() {
        return cDao;
    }

    public void setCDao(ClientSessionDAO cDao) {
        this.cDao = cDao;
    }

    public void setRemoteService(RemoteBaseInterface remoteService) {
        this.remoteService = remoteService;
    }
}
