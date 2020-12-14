package com.registerMachine.autoconfiguration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SysConfigurationPropertiesBean extends SysProperties {
    private String validPeriod;//有效期
    private String productCode;//产品编码
}