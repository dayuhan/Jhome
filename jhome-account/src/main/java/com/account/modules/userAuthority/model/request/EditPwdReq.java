package com.account.modules.userAuthority.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class EditPwdReq implements Serializable {

    private static final long serialVersionUID = -5264658292459060792L;
    @NotNull(message="userId must be not null")
    @ApiModelProperty(value = "用户id", required = true)
    private Integer userId;

    @NotNull(message="oldPassword must be not null")
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;

    @NotNull(message="newPassword must be not null")
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;
}
