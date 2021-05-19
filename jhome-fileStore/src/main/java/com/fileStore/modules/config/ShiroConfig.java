package com.fileStore.modules.config;
import com.fileStore.autoconfiguration.SysConfigurationPropertiesBean;
import com.fileStore.conmmon.ClientRealm;
import com.fileStore.conmmon.SysShiroProperties;
import com.fileStore.conmmon.UserAuxiliary;
import com.fileStore.conmmon.UserRemoteServiceInterface;
import com.shiro.common.filter.ClientTokenFormAuthenticationFilter;
import com.shiro.common.session.ClientSessionDAO;
import com.shiro.common.session.ClientWebSessionManager;
import com.shiro.common.session.ShiroSessionFactory;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Shiro 配置
 */
@Configuration
@AutoConfigureAfter(ShiroLifecycleBeanPostProcessorConfig.class)
public class ShiroConfig {

    @Autowired
    private UserRemoteServiceInterface remoteService;
    @Autowired
    public RedisTemplate redisTemplate;

    @Bean(name = "SysConfigurationPropertiesBean")
    public SysConfigurationPropertiesBean sysConfigurationProperties() {
        return new SysConfigurationPropertiesBean();
    }

    @Bean(name = "SysShiroProperties")
    public SysShiroProperties sysShiroProperties() {
        return new SysShiroProperties();
    }


    /**
     * 注入 ShiroFilterFactoryBean
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, ClientRealm clientRealm) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        try {
            Map<String, Filter> filterMap = shiroFilterFactoryBean.getFilters();
            filterMap.put("authc", shiroAuthcFilter(clientRealm));
            shiroFilterFactoryBean.setLoginUrl(sysShiroProperties().getLoginUrl());//登录页面
            shiroFilterFactoryBean.setSuccessUrl(sysShiroProperties().getSuccessUrl());//登录成功页面 首页
            shiroFilterFactoryBean.setUnauthorizedUrl(sysShiroProperties().getUnauthorizedUrl());//没有权限访问 错误页面，认证不通过跳转
            // 设置拦截器
            Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
            filterChainDefinitionMap = sysShiroProperties().getFilterChainDefinitionMap();

            shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
            shiroFilterFactoryBean.setSecurityManager(securityManager);
        } catch (Exception ex) {
            System.err.println(String.format("Shiro 报错：%s", ex.getMessage()));
        }

        return shiroFilterFactoryBean;
    }

    /**
     * 注入 SecurityManager
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        Collection<Realm> realms = new ArrayList<>();
        realms.add(clientRealm());
        securityManager.setRealms(realms);
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }



    @Bean
    public DefaultWebSessionManager sessionManager() {
        ClientWebSessionManager sessionManager = new ClientWebSessionManager();
        sessionManager.setDeleteInvalidSessions(true); //+
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionIdCookieEnabled(true);//+
        sessionManager.setSessionValidationSchedulerEnabled(false);//无须进行会话过期调度
        sessionManager.setSessionFactory(sessionFactory());//自定Session覆盖父类的不可序列化属性
        //定时检查失效的session
        sessionManager.setSessionDAO(clientSessionDAO());
        sessionManager.setSessionIdCookie(rememberMeCookie());

        return sessionManager;
    }

    /**
     * cookie管理器.
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        //cookie的name,对应的默认是 JSESSIONID
        SimpleCookie simpleCookie = new SimpleCookie("jhomeCookie");
        simpleCookie.setMaxAge(3600);
        // jsessionId的path为 / 用于多个系统共享jsessionId
        simpleCookie.setPath("/");
        simpleCookie.setHttpOnly(true);
        return simpleCookie;
    }
    /**
     * 设置记住我
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        byte[] cipherKey = Base64.decode("4AvVhmFLUs0KTA3Kprsdag==");
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(cipherKey);
        return cookieRememberMeManager;
    }
    @Bean
    public SessionFactory sessionFactory()
    {
        ShiroSessionFactory sessionFactory=new ShiroSessionFactory();
        return  sessionFactory;
    }

    /**
     * @param
     * @return
     * @describe 自定义AppRealm
     */
    @Bean
    public ClientRealm clientRealm() {
        ClientRealm customRealm = new ClientRealm();
        return customRealm;
    }

    /**
     * 生成代理，通过代理进行控制
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 加入注解
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 保证实现了Shiro内部lifecycle函数的bean执行 .
     *
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    /**
     * Form登录过滤器
     */
    private ClientTokenFormAuthenticationFilter shiroAuthcFilter(AuthorizingRealm authorizingRealm) {
        ClientTokenFormAuthenticationFilter bean = new ClientTokenFormAuthenticationFilter();
        bean.setCDao(clientSessionDAO());
        bean.setRemoteService(remoteService);
        //bean.setAuthorizingRealm(authorizingRealm);
        return bean;
    }

    /**
     * shiro 会话管理
     * @return
     */
    @Bean
    public ClientSessionDAO clientSessionDAO() {
        ClientSessionDAO clientSessionDAO= new ClientSessionDAO();
        clientSessionDAO.setRemoteService(remoteService);
        return clientSessionDAO;
    }

    /**
     * 获取用户
     * @return
     */
    @Bean
    public UserAuxiliary userAuxiliary()
    {
        UserAuxiliary userAuxiliary=new UserAuxiliary();
        userAuxiliary.setClientSessionDAO(clientSessionDAO());
        return userAuxiliary;
    }
}
