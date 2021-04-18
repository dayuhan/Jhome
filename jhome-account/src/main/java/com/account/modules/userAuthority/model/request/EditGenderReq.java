package com.account.modules.userAuthority.model.request;

import com.ar.common.validator.FlagValidator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class EditGenderReq implements Serializable {

    private static final long serialVersionUID = -9017096994757729670L;
    @NotNull(message="userId must be not null")
    @ApiModelProperty(value = "用户id", required = true)
    private Integer userId;

    @NotNull(message="gender must be not null")
    @FlagValidator(value = {"0","1"}, message = "性别不正确(0男，1女)")
    @ApiModelProperty(value = "性别：0男，1女", required = true)
    private Integer gender;
}
