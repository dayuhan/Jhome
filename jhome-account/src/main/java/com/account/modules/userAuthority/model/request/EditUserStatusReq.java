package com.account.modules.userAuthority.model.request;

import com.ar.common.validator.FlagValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Data
@ApiModel(value = "编辑用户状态")
public class EditUserStatusReq extends CommonIdsRequest implements Serializable {

    private static final long serialVersionUID = -6882064305392310948L;
    @NotNull(message = "status must be not null")
    @FlagValidator(value = {"0","1","2"}, message = "账号状态不正确(0试用，1启动，2禁用)")
    @ApiModelProperty(value = "[必填,账号状态：0试用，1启动，2禁用]", name = "status", required = true)
    private Integer status;

}
