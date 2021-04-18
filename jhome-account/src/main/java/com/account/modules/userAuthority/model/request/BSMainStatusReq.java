package com.account.modules.userAuthority.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Author: Da xv
 * Create date: 2019.12.20
 */
@Data
@ApiModel(value = "后台首页基本统计栏请求")
public class BSMainStatusReq implements Serializable {
    private static final long serialVersionUID = -1575263493979595037L;

    @NotNull(message="tenantId must not null")
    @ApiModelProperty(value = "企业机构ID", name = "tenantId", example="1970734198902022144", required=true)
    private Long tenantId;
}
