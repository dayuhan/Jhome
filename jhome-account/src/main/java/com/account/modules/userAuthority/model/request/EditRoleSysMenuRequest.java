package com.account.modules.userAuthority.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@ApiModel(value="修改角色分配的菜单请求参数")
public class EditRoleSysMenuRequest extends CommonProductAndTenantIdRequest implements Serializable {

    private static final long serialVersionUID = 5750926553422648691L;
    @NotNull(message="roleId must be not null")
    @ApiModelProperty(value="角色id", name="roleId", required=true)
    private Long roleId;

    @NotNull(message="sysMenuId must be not null")
    @ApiModelProperty(value="菜单ID", name="sysMenuId", required=true)
    private Long sysMenuId;
}
