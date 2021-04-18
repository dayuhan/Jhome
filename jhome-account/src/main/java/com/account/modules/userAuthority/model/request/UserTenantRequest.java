package com.account.modules.userAuthority.model.request;

import com.bracket.common.Bus.AbstractModel.PageInfoRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@ApiModel(value = "用户机构信息")
public class UserTenantRequest extends PageInfoRequest implements Serializable {

    private static final long serialVersionUID = -2778392523450895439L;

    @NotNull(message="userId must be not null")
    @ApiModelProperty(value = "userId", required = true)
    private Long userId;

    @NotNull(message="tenantId must be not null")
    @ApiModelProperty(value = "tenant_id", required = true)
    private Long tenantId;
}
