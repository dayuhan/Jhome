package com.account.modules.userAuthority.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;


@Data
@ApiModel(value="添加用户组织角色成员DTO")
public class AddUserOrgRoleMemberDTO implements Serializable {
    private static final long serialVersionUID = 972572209900674959L;

    @NotNull(message = "id must be not null")
    @ApiModelProperty(value = "id", name = "id", required = true)
    @Positive(message = "id must be gt 0")
    private Long id;
}
