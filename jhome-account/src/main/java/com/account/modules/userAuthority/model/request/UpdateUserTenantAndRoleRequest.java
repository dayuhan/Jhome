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
public class UpdateUserTenantAndRoleRequest implements Serializable {

    private static final long serialVersionUID = -1544304611676646550L;
    @NotNull(message="userId must be not null")
    @ApiModelProperty(value = "userId", required = true)
    private Long userId;

    @NotNull(message="tenantId must be not null")
    @ApiModelProperty(value = "tenantId", required = true)
    private Long tenantId;

    @NotNull(message="roleId must be not null")
    @ApiModelProperty(value = "roleId", required = true)
    private Long roleId;
}
