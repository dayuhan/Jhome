package com.account.modules.config;

import com.account.common.utils.UserAuxiliary;
import com.bracket.common.ToolKit.StringUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Configuration
public class FeignConfig implements RequestInterceptor {
/*    @Bean
    public Retryer feignRetryer(){
        return new Retryer.Default(100, TimeUnit.SECONDS.toMillis(1),5);
    }*/
     /*   @Bean
        public Decoder feignDecoder() {
                HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(objectMapper());
                ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
                return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
        }
        @Bean
        public Encoder feignEncoder(){
                HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(objectMapper());
                ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
                return new SpringEncoder(objectFactory);
        }
        public ObjectMapper objectMapper(){
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
                return objectMapper;
        }*/

    private UserAuxiliary userAuxiliary;

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(1000, TimeUnit.SECONDS.toMillis(1), 2);
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String token = (String) request.getAttribute("org.apache.shiro.web.servlet.ShiroHttpServletRequest_REQUESTED_SESSION_ID");
            if (StringUtil.isNotBlank(token)) {
                requestTemplate.header("jhomeToken", token);
            }
        }
    }

    public UserAuxiliary getUserAuxiliary() {
        return userAuxiliary;
    }

    public void setUserAuxiliary(UserAuxiliary userAuxiliary) {
        this.userAuxiliary = userAuxiliary;
    }

    //解决 FeignClient 传值 mutipart丢失问题
    @Bean
    @Primary
    @Scope("prototype")
    public SpringFormEncoder multipartFormEncoder() {
        return new SpringFormEncoder();
    }

    @Bean
    public feign.Logger.Level multipartLoggerLevel() {
        return feign.Logger.Level.FULL;
    }

}
