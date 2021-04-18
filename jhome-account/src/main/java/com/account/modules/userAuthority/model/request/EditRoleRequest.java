package com.account.modules.userAuthority.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@ApiModel(value="修改角色请求参数")
public class EditRoleRequest extends CommonProductAndTenantIdRequest implements Serializable {
    private static final long serialVersionUID = -1269128357720985534L;
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
     * 修改人ID
     */
    @NotNull(message="updateUserId must be not null")
    @ApiModelProperty(value="修改人ID", name="updateUserId", required=true)
    private Long updateUserId;
}
