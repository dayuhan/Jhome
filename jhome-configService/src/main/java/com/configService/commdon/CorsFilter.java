package com.configService.commdon;/**
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-07-07 21:40
 **/

import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
 * @program: jhome-root
 * @description: shrio跨域
 * @author: Daxv
 * @create: 2020-07-07 21:40
 **/
@Component
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(servletRequest);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(servletResponse);
        //允许那些Origin发起跨域请求
        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        //允许请求的方法
        httpServletResponse.setHeader("Access-Control-Allow-Methods", httpServletRequest.getMethod());
        //多少秒内，不需要发送预检验请求，可以缓存该结果
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        //表明它允许跨域请求 包含xxxx头
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Allow-Headers"));
        //是否允许浏览器携带用于Cookie
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
//        if (httpServletRequest.getMethod().equals("OPTIONS")) {
//            httpServletResponse.setStatus(200);
//            return;
//        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    @Override
    public void destroy() {

    }
}
