package com.account.modules.userAuthority.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@ApiModel(value="添加角色分配菜单请求参数")
public class AddRoleSysMenuRequest implements Serializable {
    private static final long serialVersionUID = -5087448066096588025L;
    @NotNull(message="roleId must be not null")
    @ApiModelProperty(value="角色id",name="roleId",required=true)
    private Long roleId;

    @NotNull(message="sysMenuIds must be not null")
    @ApiModelProperty(value="菜单IDs(数组)",name="sysMenuIds",required=true)
    private Long[] sysMenuIds;
}
