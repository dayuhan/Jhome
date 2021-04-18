package com.account.modules.userAuthority.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Data
@ApiModel(value="添加角色组织请求参数")
public class RoleOrgListDTO {

    @NotNull(message="roleIds must be not null")
    @ApiModelProperty(value="角色IDs(数组)",example="[450187167612896121,450187167612896120]",name="roleIds",required=true)
    private Long[] roleIds;

    @ApiModelProperty(value="组织ID",example="1931974541416603648",name="orgId")
    private Long orgId;

    @ApiModelProperty(value="租户ID",example="1931974541416603648",name="tenantId")
    private Long tenantId;
}
