package com.account.modules.userAuthority.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Data
@ApiModel(value = "重置用户密码")
public class EditUserPswReq extends CommonIdsRequest  implements Serializable {

    private static final long serialVersionUID = -4515592202666561400L;
    @NotNull(message = "resourceIdList must be not null")
    @ApiModelProperty(value = "[必填,用户密码]", name = "password", required = true)
    private String password;

}
