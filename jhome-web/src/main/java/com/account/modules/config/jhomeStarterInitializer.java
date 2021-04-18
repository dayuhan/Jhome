package com.account.modules.config;

import com.account.modules.config.web.springMvcConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * servlter 3.0 规范 Spring 集成MVC
 * 替换之前的 xml
 */
public class jhomeStarterInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    public jhomeStarterInitializer() {
        super();
    }

    /**
     * dispatcherServlet 拦截路径
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/*"};
    }

    /**
     *  Spring IOC 父容器 启动配置类
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{springRootConfig.class};
    }

    /**
     *  IOC 子容器配置类 web容器配置
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        System.out.println("IOC 子容器配置类 web容器配置");
        return new Class[]{springMvcConfig.class};
    }
}
