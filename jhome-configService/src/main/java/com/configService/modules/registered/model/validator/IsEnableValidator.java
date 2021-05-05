package com.configService.modules.registered.model.validator;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-08-08 12:46
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Constraint(validatedBy = IsEnableValidatorClass.class)
public @interface IsEnableValidator {
    String[] value() default {};
    String message() default "isEnable is not found";
}
