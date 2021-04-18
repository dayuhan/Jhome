package com.account.modules.userAuthority.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class UserInfoSMSRes implements Serializable {

    private static final long serialVersionUID = -4759950923044771971L;
    @ApiModelProperty(value = " 用户ID")
    @JsonProperty("userId")
    private Integer userId;


    @ApiModelProperty(value = "用户名称")
    @JsonProperty("realName")
    private String realName;

    @ApiModelProperty(value = "头像路径，为主站的相对路径")
    @JsonProperty("imgUrl")
    private String imgUrl;
}
