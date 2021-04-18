package com.account.common.shiro.filter;

import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class InnerFilter extends AccessControlFilter {

/**
     * 方法验证用户是否登录
     * @param servletRequest
     * @param servletResponse
     * @param o
     * @return
     * @throws Exception*/

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        System.out.println("isAccessAllowed");
        return false;
    }


/**
     * 处理用户没登录后的逻辑
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception*/

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        System.out.println("onAccessDenied");
        return false;
    }

}
