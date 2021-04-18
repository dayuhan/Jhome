package com.account.modules.userAuthority.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Data
@ApiModel(value = "用户注册")
public class AddUserRegister {
    @NotNull(message="smsCode must be not null")
    @ApiModelProperty(value="短信验证码",name="smsCode")
    private String smsCode;

    @ApiModelProperty(value = "手机号码", required = true)
    @NotNull(message="mobile must be not null")
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "请输入正确的手机号")
    private String mobile;

    @NotNull(message="password must be not null")
    @ApiModelProperty(value="用户密码",name="password")
    private String password;
}
