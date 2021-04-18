package com.account.modules.userAuthority.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@ApiModel(value="添加角色请求参数")
public class AddRoleRequest implements Serializable {
    private static final long serialVersionUID = -5856145233783386632L;
    /**
     * 角色名称
     */
    @NotNull(message="roleName must be not null")
    @Length(min = 3, max = 20)
    @ApiModelProperty(value="角色名称", name="roleName", required=true)
    private String roleName;

    /**
     * 备注
     */
    @NotNull(message="remark must be not null")
    @ApiModelProperty(value="备注", name="remark", required=true)
    private String remark;

    /**
     * 创建人ID
     */
    @NotNull(message="createUserId must be not null")
    @ApiModelProperty(value="创建人ID", name="createUserId", required=true)
    private Long createUserId;

    /**
     * 租户ID
     */
    @ApiModelProperty(value="租户ID", name="tenantId")
    private Long tenantId;

    /**
     * 产品Id
     */
//    @NotNull(message="productId must be not null")
    @ApiModelProperty(value="产品Id", name="productId", required=true)
    private Long productId;
}
