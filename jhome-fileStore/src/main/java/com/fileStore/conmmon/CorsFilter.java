package com.fileStore.conmmon;

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
 * @description: CorsFilter
 * @author: Daxv
 * @create: 2020-07-07 13:13
 **/
@Component
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        // 允许哪些Origin发起跨域请求
        httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
        // 允许请求的方法
        httpResponse.setHeader("Access-Control-Allow-Methods", httpRequest.getMethod());
        //多少秒内,不需要再发送预检验请求，可以缓存该结果
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        // 表明它允许跨域请求包含xxx头
        httpResponse.setHeader("Access-Control-Allow-Headers", httpRequest.getHeader("Access-Control-Request-Headers"));
        //是否允许浏览器携带用户身份信息（cookie）
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        //prefight请求
//        if (httpRequest.getMethod().equals( "OPTIONS" )) {
//            httpResponse.setStatus( 200 );
//            return;
//        }
        filterChain.doFilter(httpRequest, httpResponse);
    }

    @Override
    public void destroy() {
    }
}