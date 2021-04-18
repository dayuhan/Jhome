package com.account.modules.userAuthority.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@ApiModel(value="修改用户角色请求参数")
public class EditUserRoleRequest  implements Serializable {
    private static final long serialVersionUID = -6463658642501775584L;
    @NotNull(message="id must be not null")
    @ApiModelProperty(value="id",name="id",required=true)
    private Long id;

    @NotNull(message="userId must be not null")
    @ApiModelProperty(value="用户id",name="userId",required=true)
    private Long userId;

    @NotNull(message="roleId must be not null")
    @ApiModelProperty(value="角色ID",name="roleId",required=true)
    private Long roleId;
}
