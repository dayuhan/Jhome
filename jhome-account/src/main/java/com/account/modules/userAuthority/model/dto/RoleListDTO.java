package com.account.modules.userAuthority.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Data
@ApiModel(value="用户角色列表返回对象")
public class RoleListDTO {

    /**
     * 角色名称
     */
    @ApiModelProperty(value="角色名称",name="roleName")
    private String roleName;

    /**
     * 角色ID
     */
    @ApiModelProperty(value="角色名称",name="roleId")
    private Long id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间",name="createTime")
    private String createTime;

}
