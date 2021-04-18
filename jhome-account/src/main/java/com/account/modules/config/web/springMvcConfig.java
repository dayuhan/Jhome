package com.account.modules.config.web;

import com.account.autoconfiguration.SysConfigurationPropertiesBean;
import com.account.modules.config.MessageConverter;
import com.account.modules.userAuthentication.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

/**
 * springMvc 容器配置-扩展配置
 * 扫描 restController
 */
@EnableWebMvc
@Configuration
//@ComponentScan(
//        basePackages = {"com.account.modules.*"},
//        includeFilters = {
//                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {RestController.class})},
//        useDefaultFilters = false)
public class springMvcConfig implements WebMvcConfigurer {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/",
            "classpath:/resources/",
            "classpath:/static/",
            "classpath:/public/"};

    @Autowired
    public SysConfigurationPropertiesBean spro;


    /**
     * 注册拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //此处添加需要注册的拦截器...
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**").excludePathPatterns("/login");
    }

    /**
     * 添加 viewControlle映射
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //System.out.println(String.format("@@@@@@@@@@@@@@@@@@@ %s",spro.getAdminPath()));
        //registry.addViewController(String.format("/%s/userAuthentication/error",spro.getAdminPath())).setViewName("/modules/userAuthentication/error");
        //registry.addViewController(String.format("/%s/userAuthentication/main",spro.getAdminPath())).setViewName("/modules/userAuthentication/main");
        // registry.addViewController(String.format("/%s/userAuthentication/login*",spro.getAdminPath())).setViewName("/modules/userAuthentication/login");
        //registry.addViewController("/userAuthentication/login").setViewName("/modules/userAuthentication/login");
    }

    /**
     * 文件上传下载组件
     *
     * @return
     */
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("UTF-8");
        multipartResolver.setMaxUploadSizePerFile(1024 * 1024 * 10);
        return multipartResolver;
    }

    /**
     * 试图解析器
     *
     * @return
     */
    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setSuffix(".html");
        viewResolver.setPrefix("classpath:/templates/");
        return viewResolver;
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MessageConverter());
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    /**
     * 跨域设置
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置语序跨域的请求的域名
        registry.addMapping("/*")
                //设置允许跨域的域名
                .allowedOrigins("")
                //是否允许证书 不在默认开启
                .allowCredentials(true)
                //设置允许的方法
                .allowedMethods("*")
                //跨域允许的时间
                .maxAge(3600);
    }
}
