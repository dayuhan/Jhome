package com.sysAnnotation.common.validator;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-08-22 13:15
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Constraint(validatedBy = IsEnableValidatorClass.class)
public @interface IsEnableValidator {
    String[] value() default {};
    String message() default "isEnable is not found";
}
