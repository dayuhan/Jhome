package com.account.modules.userAuthority.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="用户组织角色列表")
public class UserOrgRoleDTO implements Serializable {

    private static final long serialVersionUID = -9212716892340286541L;
    @ApiModelProperty(value = "机构名称",name="tenantName")
    private String tenantName;

    @ApiModelProperty(value = "角色名称(多个逗号隔开)")
    private String roleNames;

    @ApiModelProperty(value="默认租户ID",name="tenantId",example="189516514267758523",required=true)
    private Long tenantId;

    @ApiModelProperty(value="是否是当前默认所在组织：0不是，1是。",name="defaultFlag", required=true)
    private Integer defaultFlag;
}
