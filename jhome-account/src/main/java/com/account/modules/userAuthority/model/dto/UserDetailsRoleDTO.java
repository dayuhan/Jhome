package com.account.modules.userAuthority.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Data
@ApiModel(value="用户角色组织列表返回对象")
public class UserDetailsRoleDTO implements Serializable {

    private static final long serialVersionUID = -7921558089174950700L;

    /**
     * id
     */
    @ApiModelProperty(value="id",name="id")
    private Long id;

    /**
     * 角色列表
     */
    @ApiModelProperty(value="角色列表",name="roleList")
    private List<RoleListDTO> roleList;

    /**
     * 组织编码
     */
    @ApiModelProperty(value="组织编码",name="organizationCode")
    private String organizationCode;

    /**
     * 组织ID
     */
    @ApiModelProperty(value="组织ID",name="orgId")
    private Long orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value="组织名称",name="name")
    private String name;

    /**
     * 租户ID
     */
    @ApiModelProperty(value="租户ID",name="tenantId")
    private Long tenantId;
}
