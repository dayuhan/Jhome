package com.account.modules.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataBaseConfig {

    @Value("${spring.datasource.dynamic.datasource.KQDataBase.username:}")
    private String name;

    @Value("${spring.datasource.dynamic.datasource.KQDataBase.username:}")
    private String username;

    @Value("${spring.datasource.dynamic.datasource.KQDataBase.password:}")
    private String password;

    @Value("${spring.datasource.dynamic.datasource.KQDataBase.host-ip:}")
    private String hostIp;

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getHostIp() {
        return hostIp;
    }
}
