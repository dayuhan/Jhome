package com.sysAnnotation.common.validator;/**
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-09-30 16:24
 **/

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
 * @description: 验证结果
 * @author: Daxv
 * @create: 2020-09-30 16:24
 **/
public class ValidateResult {
    protected boolean isValidatePass;
    protected String messAge;

    public ValidateResult() {
    }

    public ValidateResult(boolean isValidateResult) {
        this.isValidatePass = isValidateResult;
    }

    public boolean isValidatePass() {
        return isValidatePass;
    }

    public void setValidatePass(boolean validatePass) {
        isValidatePass = validatePass;
    }

    public String getMessAge() {
        return messAge;
    }

    public void setMessAge(String messAge) {
        this.messAge = messAge;
    }
}
