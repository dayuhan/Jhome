package com.account.modules.userAuthority.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色请求返回
 * @author
 */
@Data
@ApiModel(value="角色请求返回对象")
public class GetRoleResponse implements Serializable {

    private static final long serialVersionUID = 8802948180372318888L;
    @ApiModelProperty(value="ID", name="id", required=true)
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty(value="角色名称", name="roleName", required=true)
    private String roleName;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注", name="remark", required=true)
    private String remark;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间", name="createTime", required=true)
    private String createTime;

    /**
     * 租户ID
     */
    @ApiModelProperty(value="租户ID", name="tenantId", required=true)
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long tenantId;
}
