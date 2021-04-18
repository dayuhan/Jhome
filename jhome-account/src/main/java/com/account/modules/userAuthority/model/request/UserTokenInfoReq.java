package com.account.modules.userAuthority.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zhiqiang.liu1
 */
@Data
@ApiModel(value = "用户令牌信息")
public class UserTokenInfoReq implements Serializable {

    private static final long serialVersionUID = -3720423195267048373L;

    @NotNull(message="令牌键不能为空")
    @ApiModelProperty(value = "[必填,用户令牌(全路径)]", required = true)
    private String token;

}
