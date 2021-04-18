package com.sysAnnotation.common.validator;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface EnableLog {
    String value() default "account";
}
