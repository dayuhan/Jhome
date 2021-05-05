package com.registerMachine.autoconfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(
        prefix = "jhome.sysproperties"
)
public abstract class SysProperties {
}
