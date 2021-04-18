package com.account.modules.userAuthority.model.request;

import com.account.modules.userAuthority.model.dto.AddUserOrgRoleMemberDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;


@Data
@ApiModel(value = "添加用户组织角色成员请求参数")
public class AddUserOrgRoleMemberRequset implements Serializable {

    private static final long serialVersionUID = -7921856154488131687L;
    @NotNull(message = "roleId must be not null")
    @ApiModelProperty(value = "角色Id", name = "roleId", required = true)
    @Positive(message = "roleId must be gt 0")
    private Long roleId;

    @Valid
    @NotNull(message = "addUserOrgRoleMemberList must be not null")
    @ApiModelProperty(value = "用户组织角色成员列表", name = "addUserOrgRoleMemberList", required = true)
    private List<AddUserOrgRoleMemberDTO> addUserOrgRoleMemberList;
}
