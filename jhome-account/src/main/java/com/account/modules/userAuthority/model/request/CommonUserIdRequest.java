package com.account.modules.userAuthority.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 菜单请求
 * @author
 */
@Data
@ApiModel(value="用户Id请求对象")
public class CommonUserIdRequest implements Serializable {
    private static final long serialVersionUID = -394954847568014226L;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
