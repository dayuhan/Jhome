package com.registerMachine.autoconfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(
        prefix = "account.sysproperties"
)
public abstract class SysProperties {
}
