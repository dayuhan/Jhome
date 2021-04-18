package com.account.modules.userAuthority.model.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "当前组织分类用户信息数")
public class ListOrgUserNumRes implements Serializable {

    private static final long serialVersionUID = 8934687009642778704L;

    @ApiModelProperty(value = "组织分类ID")
    @JsonProperty("id")
    private String id;

    @ApiModelProperty(value = "组织架构名称")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(value = "用户人数")
    @JsonProperty("userNum")
    private Integer userNum;

}