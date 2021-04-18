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
public class UpdateUserOrgRequest implements Serializable {

    private static final long serialVersionUID = -1544304611676646550L;
    @NotNull(message="userId must be not null")
    @ApiModelProperty(value = "userId", required = true)
    private Long userId;

    @NotNull(message="tenantId must be not null")
    @ApiModelProperty(value = "tenantId", required = true)
    private Long tenantId;

    @FlagValidator(value = {"1","2"}, message = "网络类型不正确(1:内部环境, 2:外部环境)")
    @ApiModelProperty(value = "网络类型(1:内部环境, 2:外部环境)",example = "1")
    private Integer netType;
}
