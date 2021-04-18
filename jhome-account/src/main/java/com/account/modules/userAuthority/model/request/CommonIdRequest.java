package com.account.modules.userAuthority.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 适用所有id请求接口
 */
@Data
@ApiModel(value="id请求参数")
public class CommonIdRequest implements Serializable {
    private static final long serialVersionUID = -4305347437106793111L;

    @NotNull(message="id must be not null")
    @ApiModelProperty(value="id", name="id", required=true)
    private Long id;
}
