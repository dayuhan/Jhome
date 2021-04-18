package com.account.common.shiro;


import com.shiro.common.ShiroProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.*;


/**
 * 系统配置类
 *
 * @author : Daxv
 * @date : 11:03 2020/5/12 0012
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "shiro")
public class SysShiroProperties extends ShiroProperties {

    public Map filterChainDefinition;
    public String filterChainDefinitionMap;
    public String LoginUrl;
    public String SuccessUrl;
    public String UnauthorizedUrl;

    public LinkedHashMap getFilterChainDefinitionMap(String[] excludeFilter) {
        return super.ConvertMap(filterChainDefinitionMap, excludeFilter);
    }
}
