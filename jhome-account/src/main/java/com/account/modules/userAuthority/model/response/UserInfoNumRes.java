package com.account.modules.userAuthority.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "当前组织用户信息数")
public class UserInfoNumRes implements Serializable {
    private static final long serialVersionUID = 6797101323362541787L;

    @ApiModelProperty(value="当前组织用户总数",name="userNum")
    private Integer userNum;

}
