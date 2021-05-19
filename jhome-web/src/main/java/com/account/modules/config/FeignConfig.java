package com.account.modules.config;

import com.bracket.common.ToolKit.StringUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Configuration
public class FeignConfig implements RequestInterceptor {
    @Bean
    public Retryer feignRetryer() {
        //重试次数
        return new Retryer.Default(1000, TimeUnit.SECONDS.toMillis(1), 2);
    }

    /**
     * Feign接口请求其他服务 需要token认证，此处通过shiro请求头中带的sessonid 从而转换成jhomeToken
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attribute = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attribute != null) {
            HttpServletRequest request = attribute.getRequest();
            //获取token
            String token = (String) request.getAttribute("org.apache.shiro.web.servlet.ShiroHttpServletRequest_REQUESTED_SESSION_ID");
            if (StringUtil.isNotBlank(token))
            {
                //header 里面添加 token
                requestTemplate.header("jhomeToken", token);
            }
            else
            {
                token=request.getHeader("jhomeToken");
                requestTemplate.header("jhomeToken",token);
            }
        }
    }


        /*        @Bean
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
}
