package com.rpc.common.zk.scoket;

import com.rpc.common.zk.ZkRegistry;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *  使用方式：
 *  @DubboExpand(serverName="jhome-account",value="192.168.20.102:9101",isDiscovery=true)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE,ElementType.PARAMETER})
@Inherited
@Import({ ZkRegistry.class})
public @interface DubboExpand {
    String serverName() default "";
    String value() default "";
    boolean isDiscovery() default true;
}
