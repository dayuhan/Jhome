package com.account.modules.userAuthority.model.request;

import com.ar.common.validator.FlagValidator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author zhiqiang.liu1
 */
@Data
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class LoginCodeReq implements Serializable {
    private static final long serialVersionUID = -8161919629122956546L;

    @NotEmpty(message="userName must be not null")
    @ApiModelProperty(value = "手机（登录名）", required = true)
    private String mobile;

    @NotEmpty(message="pwd must be not null")
    @ApiModelProperty(value = "密码", required = true)
    private String pwd;

    @ApiModelProperty(value = "登录ip", example = "10.1.20.58")
    private String loginIp;

    @FlagValidator(value = {"0","1","2"}, message = "登录设备状态不正确(登录设备(0:web, 1:Ios, 2:Android))")
    @ApiModelProperty(value = "登录设备(0:web, 1:Ios, 2:Android)",example = "0")
    private Integer loginDevice;

    @ApiModelProperty(value = "登录平台(0:ar)", example = "0")
    private String loginSource;

    @ApiModelProperty(value = "设备型号")
    private String equipmentType;

    @FlagValidator(value = {"1","2"}, message = "网络类型不正确(1:内部环境, 2:外部环境)")
    @ApiModelProperty(value = "网络类型(1:内部环境, 2:外部环境)",example = "1")
    private Integer netType;
}
