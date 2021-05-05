package com.sysAnnotation.common.validator;

import com.bracket.common.Bus.ResponseJson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
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
 * @description: ValidateParamBase 需要工程项目继承 AOP拦截验证参数
 * @author: Daxv
 * @create: 2020-09-30 16:34
 **/

@Slf4j
@Aspect
@Component
@Order(2)
public class AutoValidateParam {
    /**
     * 定义切入点
     */
    @Pointcut("execution(public * com.*.modules.*.controller.*.*(..))")
    public void cutService() {

    }

    /**
     * 在切入点开始处切入内容
     *
     * @param joinPoint
     * @throws Exception
     */
    @Around("cutService()")
    public Object around(ProceedingJoinPoint joinPoint) throws Exception {
        Object result = null;
        // 验证结果
        ValidateResult validateResult = new ValidateResult(Boolean.TRUE);
        // 获取所有的请求参数
        Object[] args = joinPoint.getArgs();
        if (null != args && args.length > 0) {
            for (Object obj : args) {
                if (obj instanceof BindingResult) {
                    // 参数验证
                    validateResult = validate((BindingResult) obj);
                    break;
                }
            }
        } // 验证通过执行拦截方法，否则不执行
        if (!validateResult.isValidatePass()) {
            try {
                // 执行拦截方法
                result = joinPoint.proceed();
            } catch (Throwable ex) {
                log.error("AOP执行拦截方法时异常, {}", ex);
                throw new Exception(ex);
            }
        } else {
            return new ResponseJson().error(HttpStatus.INTERNAL_SERVER_ERROR.value(), validateResult.getMessAge());
        }
        return result;
    }

    /**
     * 验证
     *
     * @param bindingResult
     * @return
     */
    private ValidateResult validate(BindingResult bindingResult) {
        // 参数验证结果
        ValidateResult validateResult = ParamValidateUtil.validating(bindingResult);
        log.info("请求参数验证结果：{}", validateResult);
        return validateResult;
    }
}