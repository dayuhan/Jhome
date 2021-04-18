package com.account.common.shiro.filter;/**
 * @program: account-root
 * @description
 * @author: Daxv
 * @create: 2020-07-18 15:09
 **/

import com.bracket.common.Bus.ResponseJson;
import com.bracket.common.Http.HttpUtil;
import com.bracket.common.ToolKit.CookieUtil;
import com.bracket.common.ToolKit.StringUtil;
import com.shiro.common.token.jhomeToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * //
 * //                       .::::.
 * //                     .::::::::.
 * //                    :::::::::::
 * //                 ..:::::::::::'
 * //              '::::::::::::'
 * //                .::::::::::
 * //           '::::::::::::::..
 * //                ..::::::::::::.
 * //              ``::::::::::::::::
 * //               ::::``:::::::::'        .:::.
 * //              ::::'   ':::::'       .::::::::.
 * //            .::::'      ::::     .:::::::'::::.
 * //           .:::'       :::::  .:::::::::' ':::::.
 * //          .::'        :::::.:::::::::'      ':::::.
 * //         .::'         ::::::::::::::'         ``::::.
 * //     ...:::           ::::::::::::'              ``::.
 * //    ```` ':.          ':::::::::'                  ::::..
 * //                       '.:::::'                    ':'````..
 *
 * @program: account-root
 * @description: 前后台分离模式过滤器
 * @author: Daxv
 * @create: 2020-07-18 15:09
 **/
public class SecurityAuthenticationFilter extends MixedModeFormAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityAuthenticationFilter.class);
    private String callbackUrl;

    /**
     * 跨域处理 （对前后端非分离模式支持）
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);

        //url 传递参数
        String sessionId = httpServletRequest.getParameter("sessionId");
        if (StringUtil.isNotBlank(sessionId)) {
            //sessionId = (String) UserUtil.ParsingToken(sessionId);
            CookieUtil.set(httpServletResponse, "LuxCookie", sessionId, 30);
        }
        if ("OPTIONS".equals(httpServletRequest.getMethod())) {
            httpServletResponse.setStatus(org.apache.http.HttpStatus.SC_NO_CONTENT);
            logger.info("OPTIONS 放行");
            return true;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        try {
            Subject subject = SecurityUtils.getSubject();
            boolean bo = subject.isAuthenticated();
            if (!bo) {
                if (isLoginRequest(request, response)) {
                    if (isLoginSubmission(request, response)) {
                        return executeLogin(request, response);
                    } else {
                        return true;
                    }
                } else {
                    HttpUtil.SendFlush(response, new ResponseJson().error("登陆已经失效，请重新登陆！~").setValue("callbackUrl",this.getCallbackUrl()));
                }
            }
        } catch (Exception ex) {
            HttpUtil.SendFlush(response, new ResponseJson().error("登陆已经失效，请重新登陆！~").setValue("callbackUrl",this.getCallbackUrl()));
        }
        return false;
    }


    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        jhomeToken token=new jhomeToken();
        token.setDeviceType("");
        token.setUsername(getUsername(request));
        //token.setPassword(getPassword(request));
        return token;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
