package com.account.modules.userAuthority.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 适用所有接口，根据id、产品id和租户id请求参数
 */
@Data
@ApiModel(value="id、产品id和租户id请求参数")
public class CommonProductAndTenantIdRequest implements Serializable {
    private static final long serialVersionUID = -4305347437106793111L;

    @NotNull(message="id must be not null")
    @ApiModelProperty(value="id", name="id", required=true)
    private Long id;

    /**
     * 产品Id
     */
//    @NotNull(message="productId must be not null")
    @ApiModelProperty(value="产品Id", name="productId", required=true)
    private Long productId;

    /**
     * 租户ID
     */
    @NotNull(message="tenantId must be not null")
    @ApiModelProperty(value="租户ID", name="tenantId", required=true)
    private Long tenantId;
}
