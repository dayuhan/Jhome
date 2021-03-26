package com.rpc.common.zk.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
@Import(ZkRegistry.class)
public @interface ZkRegistryClient {
    String path() default "";
    String value() default "";
}
