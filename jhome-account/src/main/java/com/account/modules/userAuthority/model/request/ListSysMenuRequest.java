package com.account.modules.userAuthority.model.request;

import com.bracket.common.Bus.AbstractModel.PageInfoRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@ApiModel(value="列出App的Banner请求参数")
public class ListSysMenuRequest extends PageInfoRequest implements Serializable {

    private static final long serialVersionUID = -8870787291729945205L;
    @NotNull(message="userId must be not null")
    @ApiModelProperty(value="用户",required=true)
    private Long userId;

    /**
     * 产品Id
     */
    @NotNull(message="productId must be not null")
    @ApiModelProperty(value="产品Id", name="productId", required=true)
    private Long productId;

    /**
     * 租户ID
     */
    @NotNull(message="tenantId must be not null")
    @ApiModelProperty(value="租户ID", name="tenantId", required=true)
    private Long tenantId;
}
