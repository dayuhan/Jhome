package com.shiro.common.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * ClientAuthenticationFilter是用于实现身份认证的拦截器（authc），当用户没有身份认证时；
 * 1、首先得到请求参数backUrl，即登录成功重定向到的地址；
 * 2、然后保存保存请求到会话，并重定向到登录地址（server模块）；
 * 3、登录成功后，返回地址按照如下顺序获取：backUrl、保存的当前请求地址、defaultBackUrl（即设置的successUrl）
 */
public class ClientFormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(ClientFormAuthenticationFilter.class);

    //允许访问
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        logger.info("ClientAuthenticationFilter----处理拦截URL：" + httpServletRequest.getRequestURI());
        Subject subject = getSubject(request, response);
        return subject.isAuthenticated();
    }

    //拒绝访问
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // 服务端收到拒绝后跳转到登录页面 前后端分离在，isAccessAllowed验证输出给前段，让跳转
        String backUrl = request.getParameter("backUrl");
        Session session = saveRequest(request, backUrl, getDefaultBackUrl(WebUtils.toHttp(request)));
        //HttpServletRequest req = WebUtils.toHttp(request);
        //HttpServletResponse rep = WebUtils.toHttp(response);
        //rep.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        //客户端向Acccount服务 发送token 服务端复制SessionId
        //实现方式很多，也可以配置服务器端允许跨域，允许客户端携带的Cookie中存储的tokenID = SessionId 进行传递
        //String mbUrl = String.format("%s?token=%s", super.getLoginUrl(), session.getId());
        //WebUtils.issueRedirect(request, response, mbUrl);
        //redirectToLogin(request, response);
        return false;
    }

    // 调用远程会话 将返回地址 存储到 Redis中
    protected Session saveRequest(ServletRequest request, String backUrl, String fallbackUrl) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        session.setAttribute("fallbackUrl", fallbackUrl);
        SavedRequest savedRequest = new ClientSavedRequest(httpRequest, backUrl);
        session.setAttribute(WebUtils.SAVED_REQUEST_KEY, savedRequest);
        return session;
    }

    //获取登录成功后返回地址
    private String getDefaultBackUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String domain = request.getServerName();
        int port = request.getServerPort();
        String contextPath = request.getContextPath();
        StringBuilder backUrl = new StringBuilder(scheme);
        backUrl.append("://");
        backUrl.append(domain);
        if ("http".equalsIgnoreCase(scheme) && port != 80) {
            backUrl.append(":").append(String.valueOf(port));
        } else if ("https".equalsIgnoreCase(scheme) && port != 443) {
            backUrl.append(":").append(String.valueOf(port));
        }
        backUrl.append(contextPath);
        backUrl.append(getSuccessUrl());
        return backUrl.toString();
    }


}
