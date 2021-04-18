package com.account.modules.userAuthority.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 适用所有ids数组请求接口
 */
@Data
@ApiModel(value="ids请求参数")
public class CommonIdsRequest implements Serializable {
    private static final long serialVersionUID = -4305347437106793111L;

    @NotNull(message="ids must be not null")
    @ApiModelProperty(value="ids数组", name="ids", required=true)
    private Long[] ids;
}
