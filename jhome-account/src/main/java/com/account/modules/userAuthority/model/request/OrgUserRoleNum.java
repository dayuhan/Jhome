package com.account.modules.userAuthority.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "组织分类角色用户数")
public class OrgUserRoleNum extends CommonIdsRequest implements Serializable {

    private static final long serialVersionUID = -5025373449787911638L;

    //@NotNull(message="roleId must be not null")
    @ApiModelProperty(value="角色Id", name="roleId", required=true)
    private Long roleId;


}
