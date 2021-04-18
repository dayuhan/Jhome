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
public class UserInfoRes implements Serializable {

    private static final long serialVersionUID = 9211148281541867075L;
    @ApiModelProperty(value = " 用户ID")
    @JsonProperty("userId")
    private Integer userId;

    @ApiModelProperty(value = "用户名称")
    @JsonProperty("realName")
    private String realName;

    @ApiModelProperty(value = "头像路径，为主站的相对路径")
    @JsonProperty("imgUrl")
    private String imgUrl;

    @ApiModelProperty(value="租户ID",name="tenantId",example="189516514267758523",required=true)
    private String tenantId = "189516514267758523";

    @ApiModelProperty(value = "文件服务器地址前缀")
    @JsonProperty("dfsServer")
    private String dfsServer;
}
