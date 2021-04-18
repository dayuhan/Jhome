package com.account.modules.userAuthority.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel(value="组织APP列表")
public class UserTenantRoleDTO implements Serializable {

    private static final long serialVersionUID = 6269639510544023905L;

    @ApiModelProperty(value="机构ID",name="tenant_id",example="644699827135053825",required=true)
    private long tenantId;
    @ApiModelProperty(value="机构名称",name="tenant_name",example="深大",required=true)
    private String tenantName;
    @ApiModelProperty(value="机构logo",name="tenant_logo",example="深大",required=true)
    private String tenantLogo;
    @ApiModelProperty(value="是否是当前默认所在组织：0不是，1是。",name="defaultFlag", required=true)
    private Integer defaultFlag;
    @ApiModelProperty(value = "角色列表(多个逗号隔开)")
    private String roles;
}
