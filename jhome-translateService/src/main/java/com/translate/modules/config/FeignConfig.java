package com.translate.modules.config;

import com.bracket.common.ToolKit.StringUtil;
import com.translate.conmmon.UserAuxiliary;

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
public class FeignConfig  implements RequestInterceptor {
    private UserAuxiliary userAuxiliary;
    @Bean
    public Retryer feignRetryer(){
        return new Retryer.Default(1000, TimeUnit.SECONDS.toMillis(1),2);
    }
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String token = (String) request.getAttribute("org.apache.shiro.web.servlet.ShiroHttpServletRequest_REQUESTED_SESSION_ID");
            if (StringUtil.isNotBlank(token)) {
                requestTemplate.header("luxToken",token);
            }
        }
    }
    public UserAuxiliary getUserAuxiliary() {
        return userAuxiliary;
    }

    public void setUserAuxiliary(UserAuxiliary userAuxiliary) {
        this.userAuxiliary = userAuxiliary;
    }

}
