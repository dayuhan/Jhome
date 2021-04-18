package com.account.modules.userAuthority.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(value = "添加角色分配数据权限请求参数")
public class AddRoleSysDataRequest implements Serializable {
    private static final long serialVersionUID = -5087448066096588025L;
    @NotNull(message = "roleId must be not null")
    @ApiModelProperty(value = "角色id", name = "roleId", required = true)
    private Long roleId;


    @ApiModelProperty(value = "组织organization_IDs(数组)", name = "sysOrganizationIds")
    private Long[] sysOrganizationIds;
}
