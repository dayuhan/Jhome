package com.account.modules.userAuthority.model.request;

import com.bracket.common.Bus.AbstractModel.PageInfoRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Data
@ApiModel(value = "查询组织架构列表请求参数")
public class ListOrganizationReq extends PageInfoRequest implements Serializable {

    private static final long serialVersionUID = -3286156182263124565L;

    @ApiModelProperty(value = "组织架构名称", name = "name", example = "深职院007")
    private String name;

    @ApiModelProperty(value = "组织编码", name = "organizationCode", example = "JM6t88")
    private String organizationCode;

    @NotNull(message="roleName must be not null")
    @ApiModelProperty(value = "默认角色名称")
    private String roleName;

    @NotNull(message="tenantId must be not null")
    @ApiModelProperty(value="默认租户ID",name="tenantId",example="189516514267758523",required=true)
    private Long tenantId;

}
