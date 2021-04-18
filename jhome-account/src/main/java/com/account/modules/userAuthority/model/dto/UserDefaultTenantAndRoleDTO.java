package com.account.modules.userAuthority.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="用户默认组织的角色信息")
public class UserDefaultTenantAndRoleDTO implements Serializable {

    private static final long serialVersionUID = -9212716892340286541L;

    @ApiModelProperty(value="默认租户ID",name="tenantId",example="189516514267758523",required=true)
    private Long tenantId;

    @ApiModelProperty(value = "默认租户名称",name="tenantName")
    private String tenantName;

    @ApiModelProperty(value = "此租户下的角色名称(多个逗号隔开)")
    private String roleNames;
}
