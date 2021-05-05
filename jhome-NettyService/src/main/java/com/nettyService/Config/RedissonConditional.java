package com.nettyService.config;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-05-22 14:53
 **/
public class RedissonConditional implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String str= conditionContext.getEnvironment().getProperty("redis").toString();
        //return str.isEmpty()?true:false;
        return false;
    }
}
