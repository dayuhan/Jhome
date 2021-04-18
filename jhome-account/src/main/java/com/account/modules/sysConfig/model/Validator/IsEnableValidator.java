package com.account.modules.sysConfig.model.Validator;


import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = IsEnableValidatorClass.class)
public @interface IsEnableValidator {
    String[] value() default {};
    String message() default "isEnable is not found";
}
