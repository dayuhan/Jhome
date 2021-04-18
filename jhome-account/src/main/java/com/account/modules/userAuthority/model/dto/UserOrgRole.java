package com.account.modules.userAuthority.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="用户组织角色")
public class UserOrgRole implements Serializable {

    private static final long serialVersionUID = -9212716892340286541L;
    @ApiModelProperty(value = "主键",name="id")
    private String id;

    @ApiModelProperty(value = "角色名称(多个逗号隔开)",name="user_id")
    private String userId;

    @ApiModelProperty(value="组织Id",name="org_id",example="189516514267758523",required=true)
    private Long orgId;

    @ApiModelProperty(value="租户ID",name="tenant_id",example="189516514267758523",required=true)
    private Long tenantId;

    @ApiModelProperty(value="是否是当前默认所在组织：0不是，1是。",name="default_flag", required=true)
    private Integer defaultFlag;
}
