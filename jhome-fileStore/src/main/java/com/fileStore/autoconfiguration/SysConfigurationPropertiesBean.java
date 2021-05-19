package com.fileStore.autoconfiguration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@ConfigurationProperties(
        prefix = "jhome.sysproperties"
)
public class SysConfigurationPropertiesBean  {
    public QueueConfig queueconfig;//队列配置
    public String adminPath;//后台路径
    public String frontPath;//前台路径
    public String TestName;
    private String rootPath;//上传根目录
    private static String serverType;//服务类型（0:fastdfs,1:oss）
    @Getter
    @Setter
    public static class QueueConfig {
        public String hostName;
        public String userName;
        public String passWord;
        public String virtualHost;

        @Override
        public String toString() {
            return "QueueConfig{" +
                    "hostName='" + hostName + '\'' +
                    ", userName='" + userName + '\'' +
                    ", passWord='" + passWord + '\'' +
                    ", virtualHost='" + virtualHost + '\'' +
                    '}';
        }
    }

}