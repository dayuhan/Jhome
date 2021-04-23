package com.translate.autoconfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(
        prefix = "lux.sysproperties"
)
public abstract class SysProperties {

}
