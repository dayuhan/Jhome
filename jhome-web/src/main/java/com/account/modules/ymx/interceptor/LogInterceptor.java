package com.account.modules.ymx.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MVC 日志拦截器
 * 用户自定义类需要注入到容器:sysInterceptorConfig
 */
public class LogInterceptor implements HandlerInterceptor {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug(String.format("日志拦截器执行之前...访问URL:%s", request.getRequestURI().toString()));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.debug(String.format("日志拦截器执行之后...访问URL:%s", request.getRequestURI().toString()));

    }
}
