package com.sysAnnotation.common.validator;

import org.springframework.validation.BindingResult;

/**
 * //
 * //                       .::::.
 * //                     .::::::::.
 * //                    :::::::::::
 * //                 ..:::::::::::'
 * //              '::::::::::::'
 * //                .::::::::::
 * //           '::::::::::::::..
 * //                ..::::::::::::.
 * //              ``::::::::::::::::
 * //               ::::``:::::::::'        .:::.
 * //              ::::'   ':::::'       .::::::::.
 * //            .::::'      ::::     .:::::::'::::.
 * //           .:::'       :::::  .:::::::::' ':::::.
 * //          .::'        :::::.:::::::::'      ':::::.
 * //         .::'         ::::::::::::::'         ``::::.
 * //     ...:::           ::::::::::::'              ``::.
 * //    ```` ':.          ':::::::::'                  ::::..
 * //                       '.:::::'                    ':'````..
 *
 * @program: jhome-root
 * @description: 参数验证
 * @author: Daxv
 * @create: 2020-09-30 16:26
 **/
public class ParamValidateUtil {
    public ParamValidateUtil() {
    }

    public static ValidateResult validating(BindingResult bindingResult) {
        if (bindingResult == null)
            return null;
        ValidateResult validateResult = new ValidateResult();
        validateResult.setValidatePass(bindingResult.hasErrors());
        if (bindingResult.hasErrors())
            validateResult.setMessAge(bindingResult.getFieldError().getDefaultMessage() + "错误字段：" + bindingResult.getFieldError().getField());
        return validateResult;
    }
}

