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
@ApiModel(value = "发送短信")
public class SmsMsgRequest {

    @NotNull(message = "mobile must be not null")
    @ApiModelProperty(value = "手机号码", example = "13802642723", required = true)
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "请输入正确的手机号")
    private String mobile;

    @NotNull(message = "operationFlag must be not null")
    @ApiModelProperty(value = "操作标识:1.注册 2.忘记密码", example = "1", required = true)
    private Integer operationFlag;
}