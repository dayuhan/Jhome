package com.account.modules.userAuthority.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@ApiModel(value="添加用户组织角色请求参数")
public class AddUserOrgRoleRequest implements Serializable {
    private static final long serialVersionUID = -4615243621711075803L;
    @NotNull(message="userId must be not null")
    @ApiModelProperty(value="用户id",name="userId",required=true)
    private Long userId;

    @NotNull(message="orgId must be not null")
    @ApiModelProperty(value="组织id",name="orgId",required=true)
    private Long orgId;

    @NotNull(message="roleIds must be not null")
    @ApiModelProperty(value="角色IDs(数组)",name="roleIds",required=true)
    private Long[] roleIds;
}
