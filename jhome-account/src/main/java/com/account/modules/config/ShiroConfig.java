package com.account.modules.config;

import com.account.autoconfiguration.SysConfigurationPropertiesBean;
import com.account.common.shiro.SysShiroProperties;
import com.account.common.shiro.filter.*;
import com.account.common.shiro.filter.cas.CallbackFilter;
import com.account.common.shiro.filter.cas.CasClient;
import com.account.common.shiro.filter.cas.CasSecurityFilter;
import com.account.common.utils.UserAuxiliary;
import com.account.modules.userAuthentication.cert.AppShiroRealm;
import com.account.modules.userAuthentication.cert.CasRealm;
import com.account.modules.userAuthentication.cert.CustomRealm;
import com.account.modules.userAuthentication.cert.ThirdPathShiroRealm;
import com.shiro.common.SimpleCredentialsMatcher.CustomCredentialsMatcher;
import com.shiro.common.realm.ServerCustomizedModularRealmAuthenticator;
import com.shiro.common.realm.ServerDefaultWebSessionManager;
import com.shiro.common.realm.ServerRedisSessionDao;
import com.shiro.common.session.ShiroSessionFactory;
import io.buji.pac4j.subject.Pac4jSubjectFactory;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.config.CasProtocol;
import org.pac4j.core.config.Config;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import io.buji.pac4j.filter.LogoutFilter;

/**
 * Shiro 配置
 *
 * @author : Daxv
 * @date : 11:03 2020/5/12 0012
 */
@Configuration
@AutoConfigureAfter(ShiroLifecycleBeanPostProcessorConfig.class)
public class ShiroConfig {

    @Autowired
    public RedisTemplate redisTemplate;

    //    @Autowired
//    public SysShiroProperties spro;
    @Bean(name = "SysConfigurationPropertiesBean")
    public SysConfigurationPropertiesBean sysConfigurationProperties() {
        return new SysConfigurationPropertiesBean();
    }


    @Bean(name = "SysShiroProperties")
    public SysShiroProperties sysShiroProperties() {
        return new SysShiroProperties();
    }


    /**
     * 1、shiroFilter 配置---------------------------------------------------------------------------
     **/
    @Bean(name = "shiroFilter")
    //@DependsOn("config")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, CustomRealm customRealm,@Autowired SysConfigurationPropertiesBean sysConfigurationProperties) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        try {
            shiroFilterFactoryBean.setLoginUrl(sysShiroProperties().getLoginUrl());//登录页面
            shiroFilterFactoryBean.setSuccessUrl(sysShiroProperties().getSuccessUrl());//登录成功页面 首页
            shiroFilterFactoryBean.setUnauthorizedUrl(sysShiroProperties().getUnauthorizedUrl());//没有权限访问 错误页面，认证不通过跳转

            Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
            Map<String, Filter> filterMap = shiroFilterFactoryBean.getFilters();
            if (sysConfigurationProperties.getCasConfig().getIsEnable().equals("true")) {
                //cas 资源认证拦截器
                //SecurityFilter securityFilter = new SecurityFilter();
                CasSecurityFilter securityFilter = new CasSecurityFilter();
                securityFilter.setCDao(serverRedisSessionDao(sysConfigurationProperties));
                securityFilter.setConfig(config(sysConfigurationProperties));
                securityFilter.setClients(sysConfigurationProperties.getCasConfig().getClientName());
                filterMap.put("casSecurityFilter", securityFilter);
                //cas 认证后回调拦截器
                CallbackFilter callbackFilter = new CallbackFilter();
                callbackFilter.setConfig(config(sysConfigurationProperties));
                callbackFilter.setDefaultUrl(sysConfigurationProperties.getCasConfig().getProjectUrl() + "/index");
                filterMap.put("callbackFilter", callbackFilter);
                // 注销 拦截器
                LogoutFilter logoutFilter = new LogoutFilter();
                logoutFilter.setConfig(config(sysConfigurationProperties));
                logoutFilter.setCentralLogout(true);
                logoutFilter.setLocalLogout(true);
                logoutFilter.setDefaultUrl(sysConfigurationProperties.getCasConfig().getProjectUrl() + "/callback?client_name=" + sysConfigurationProperties.getCasConfig().getClientName());
                filterMap.put("logout", logoutFilter);
                filterChainDefinitionMap = sysShiroProperties().getFilterChainDefinitionMap(new String[]{"authc"});
            } else {
                filterMap.put("authc", shiroAuthcFilter(sysConfigurationProperties));
                filterMap.put("logout", shiroLogoutFilter());
                filterMap.put("perms", shiroPermsFilter());
                filterMap.put("roles", shiroRolesFilter());
                filterMap.put("user", shiroUserFilter());
                // filterMap.put("user", crossDomainFilter());
                filterChainDefinitionMap = sysShiroProperties().getFilterChainDefinitionMap(new String[]{"casSecurityFilter", "callbackFilter"});
            }
            shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
            shiroFilterFactoryBean.setSecurityManager(securityManager);
        } catch (Exception ex) {
            System.err.println(String.format("Shiro 报错：%s", ex.getMessage()));
        }
        return shiroFilterFactoryBean;
    }

    /**
     * 2、SecurityManager 配置---------------------------------------------------------------------------
     **/
    @Bean
    public SecurityManager securityManager(@Autowired SysConfigurationPropertiesBean sysConfigurationProperties) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置认证源头
        securityManager.setAuthenticator(new ServerCustomizedModularRealmAuthenticator());
        // 设置Realm
        Collection<Realm> realms = new ArrayList<>();
        realms.add(customRealm());//后台系统数据认证
        realms.add(thirdPathShiroRealm());//第三方数据认证
        realms.add(appShiroRealm());//手机端数据认证
        realms.add(casRealm(sysConfigurationProperties));//单点登录数认证
        securityManager.setRealms(realms);
        //设置会话
        if (sysConfigurationProperties.getCasConfig().getIsEnable().equals("true")) {
            //增加pac4jSubjectFactory
            securityManager.setSubjectFactory(subjectFactory());
        } else {
            securityManager.setSessionManager(sessionManager(sysConfigurationProperties));
        }
        //注入Cookie(记住我)管理器(remenberMeManager)
        //securityManager.setRememberMeManager(this.rememberMeManager());
        return securityManager;
    }

    /**
     * 生成代理，通过代理进行控制
     */
    @Bean
    //@DependsOn({"lifecycleBeanPostProcessor"})
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
     * 3、Cookie 配置---------------------------------------------------------------------------
     **/
    /**
     * 会话Cookie模板 30天缓存
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        //cookie的name,对应的默认是 JSESSIONID
        SimpleCookie simpleCookie = new SimpleCookie("JhomeCookie");
        simpleCookie.setMaxAge(2592000);//30天
        // jsessionId的path为 / 用于多个系统共享jsessionId
        simpleCookie.setPath("/");
        simpleCookie.setHttpOnly(true);
        return simpleCookie;
    }

    /**
     * @Description 关闭浏览器 Cookie失效 Add+2020-05-13
     * @Param
     * @Return
     * @Author Mr.Ren
     * @Date
     * @Time
     */
    @Bean
    public SimpleCookie sessionIdCookie() {
        //cookie的name,对应的默认是 JSESSIONID
        SimpleCookie simpleCookie = new SimpleCookie("JhomeCookie");
        simpleCookie.setMaxAge(-1);//30天
        // jsessionId的path为 / 用于多个系统共享jsessionId
        simpleCookie.setPath("/");
        simpleCookie.setHttpOnly(true);
        return simpleCookie;
    }

    /**
     * 设置记住我
     *
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
     * 4、 sessionManager 配置---------------------------------------------------------------------------
     **/
    @Bean
    public DefaultWebSessionManager sessionManager(@Autowired SysConfigurationPropertiesBean sysConfigurationProperties) {

        //DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        ServerDefaultWebSessionManager sessionManager = new ServerDefaultWebSessionManager();
        //会话验证器调度时间 设置全局会话超时时间，默认30分钟(1800000)
        sessionManager.setSessionValidationInterval(1800000);
        //定时检查失效的session
        sessionManager.setSessionValidationSchedulerEnabled(false);
        //是否在会话过期后会调用SessionDAO的delete方法删除会话 默认true
        sessionManager.setDeleteInvalidSessions(true);
        //实现session存储
        sessionManager.setSessionDAO(serverRedisSessionDao(sysConfigurationProperties));
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionIdCookie(rememberMeCookie());
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionFactory(sessionFactory());
        return sessionManager;
    }


    /**
     * 5、定义自己的SessionFactory 配置---------------------------------------------------------------------------
     **/
    @Bean
    public SessionFactory sessionFactory() {
        ShiroSessionFactory sessionFactory = new ShiroSessionFactory();
        return sessionFactory;
    }

    /**
     * 6、 密码比较器 配置---------------------------------------------------------------------------
     **/
    @Bean
    public CustomCredentialsMatcher customCredentialsMatcher() {
        return new CustomCredentialsMatcher();
    }

    /** 7、 Realm 数据源 配置---------------------------------------------------------------------------**/

    /**
     * @param
     * @return
     * @describe 自定义AppRealm
     */
    @Bean
    public AppShiroRealm appShiroRealm() {
        AppShiroRealm appShiroRealm = new AppShiroRealm();
        appShiroRealm.setName("AppRealm");
        appShiroRealm.setCredentialsMatcher(customCredentialsMatcher());
        return appShiroRealm;
    }


    /**
     * @param
     * @return
     * @describe 自定义AppRealm
     */
    @Bean
    public CustomRealm customRealm() {
        CustomRealm customRealm = new CustomRealm();
        customRealm.setName("PcRealm");
        customRealm.setCredentialsMatcher(customCredentialsMatcher());
        //customRealm.setCachingEnabled(true);
        return customRealm;
    }


    /**
     * @param
     * @return
     * @describe 第三方 自定义ThirdRealm
     */
    @Bean
    public ThirdPathShiroRealm thirdPathShiroRealm() {
        ThirdPathShiroRealm thirdPathShiroRealm = new ThirdPathShiroRealm();
        thirdPathShiroRealm.setName("ThirdPathRealm");
        thirdPathShiroRealm.setCredentialsMatcher(customCredentialsMatcher());
        return thirdPathShiroRealm;
    }

    /**
     * 8、 shiro 会话配置---------------------------------------------------------------------------
     **/
    @Bean
    @ConditionalOnMissingBean(ServerRedisSessionDao.class)
    public ServerRedisSessionDao serverRedisSessionDao(@Autowired SysConfigurationPropertiesBean sysConfigurationProperties) {
        ServerRedisSessionDao redisSessionDao = new ServerRedisSessionDao();
        redisSessionDao.setRedisTemplate(redisTemplate);
        redisSessionDao.setExpiredTime(sysConfigurationProperties.getExpiredTime());
        return redisSessionDao;
    }

    @Bean
    public RedisTemplate<String, Object> stringSerializerRedisTemplate() {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        return redisTemplate;
    }

    /**
     * 8、 pac4j集成Shiro 配置---------------------------------------------------------------------------
     **/
    //使用 pac4j 的 subjectFactory
    @Bean
    public Pac4jSubjectFactory subjectFactory() {
        return new Pac4jSubjectFactory();
    }


    /**
     * pac4j配置
     *
     * @return
     */
    @Bean("Config")
    @DependsOn("CasClient")
    @ConditionalOnProperty(name = "jhome.sysproperties.casConfig.isEnable", havingValue = "true")
    public Config config(@Autowired SysConfigurationPropertiesBean sysConfigurationProperties) {
        return new Config(casClient(sysConfigurationProperties));
    }

    /**
     * 配置CAS客户端
     *
     * @return
     */
    @Bean("CasClient")
    @DependsOn("CasConfiguration")
    @ConditionalOnProperty(name = "jhome.sysproperties.casConfig.isEnable", havingValue = "true")
    public CasClient casClient(@Autowired SysConfigurationPropertiesBean sysConfigurationProperties) {
        CasClient casClient = new CasClient(casConfiguration(sysConfigurationProperties));
        //设置cas服务端信息
        //casClient.setConfiguration(casConfiguration);
        //登录成功后重定向回来的请求URL
        casClient.setCallbackUrl(sysConfigurationProperties.getCasConfig().getProjectUrl() + "/callback?client_name=" + sysConfigurationProperties.getCasConfig().getClientName());
        casClient.setName(sysConfigurationProperties.getCasConfig().getClientName());
        return casClient;
    }

    /**
     * cas配置服务器信息
     *
     * @return
     */
    @Bean("CasConfiguration")
    @ConditionalOnProperty(name = "jhome.sysproperties.casConfig.isEnable", havingValue = "true")
    public CasConfiguration casConfiguration(@Autowired SysConfigurationPropertiesBean sysConfigurationProperties) {
        CasConfiguration casConfiguration = new CasConfiguration();
        //CAS 版本，默认为 CAS30， CAS20
        casConfiguration.setProtocol(CasProtocol.CAS20);
        casConfiguration.setLoginUrl(sysConfigurationProperties.getCasConfig().getServerUrl() + "/GTASSO/login");
        casConfiguration.setPrefixUrl(sysConfigurationProperties.getCasConfig().getServerUrl() + "/GTASSO");
        return casConfiguration;
    }

    /**
     * Cas数据源
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "SmartCampusCasRealm")
    public CasRealm casRealm(@Autowired SysConfigurationPropertiesBean sysConfigurationProperties) {
        CasRealm realm = new CasRealm();
        realm.setName("CasRealm");
        realm.setCachingEnabled(false);
        realm.setClientName(sysConfigurationProperties.getCasConfig().getClientName());
        //暂时不使用缓存
        realm.setAuthenticationCachingEnabled(false);
        realm.setAuthorizationCachingEnabled(false);
        return realm;
    }

//    @Bean
//    public FilterRegistrationBean singleSignOutFilter() {
//        FilterRegistrationBean bean = new FilterRegistrationBean();
//        bean.setName("singleSignOutFilter");
//        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
//        singleSignOutFilter.setCasServerUrlPrefix(sysConfigurationProperties.getCasConfig().getServerUrl());
//        singleSignOutFilter.setIgnoreInitConfiguration(true);
//        bean.setFilter(singleSignOutFilter);
//        bean.addUrlPatterns("/*");
//        bean.setEnabled(true); //bean.setOrder(Ordered.HIGHEST_PERCEDENCE);
//        return bean;
//    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD);
        return filterRegistration;
    }


    /**
     * 9、 配置过滤器 配置---------------------------------------------------------------------------
     **/
    //内部系统访问过滤器
    /*
        private InnerFilter shiroInnerFilter() {
            return new InnerFilter();
        }
    */

    /**
     * Form登录过滤器 前后台分离过滤器
     */
    private SecurityAuthenticationFilter shiroAuthcFilter(@Autowired SysConfigurationPropertiesBean sysConfigurationProperties) {
        SecurityAuthenticationFilter bean = new SecurityAuthenticationFilter();
        bean.setCallbackUrl(sysConfigurationProperties.getCallbackUrl());
        return bean;
    }

    /**
     * 登出过滤器
     */
    private LogoutFilter shiroLogoutFilter() {
        LogoutFilter bean = new LogoutFilter();
        //bean.setAuthorizingRealm(authorizingRealm);
        return bean;
    }

    /**
     * 权限字符串过滤器
     */
    private PermissionsAuthorizationFilter shiroPermsFilter() {
        return new PermissionsAuthorizationFilter();
    }

    /**
     * 角色权限过滤器
     */
    private RolesAuthorizationFilter shiroRolesFilter() {
        return new RolesAuthorizationFilter();
    }

    /**
     * 用户权限过滤器
     */
    private UserFilter shiroUserFilter() {
        return new UserFilter();
    }

    /**
     * 用户辅助类
     *
     * @return
     */
    @Bean("UserAuxiliary")
    public UserAuxiliary userAuxiliary(@Autowired SysConfigurationPropertiesBean sysConfigurationProperties) {
        UserAuxiliary userAuxiliary = new UserAuxiliary();
        userAuxiliary.setCDao(serverRedisSessionDao(sysConfigurationProperties));
        return userAuxiliary;
    }

}
