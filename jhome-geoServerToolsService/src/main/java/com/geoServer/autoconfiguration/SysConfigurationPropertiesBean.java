package com.geoServer.autoconfiguration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Configuration

public class SysConfigurationPropertiesBean extends SysProperties {
   public String directoryPath;
}