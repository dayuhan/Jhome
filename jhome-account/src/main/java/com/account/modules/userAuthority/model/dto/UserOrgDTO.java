package com.account.modules.userAuthority.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Data
@ApiModel(value="组织APP列表")
public class UserOrgDTO implements Serializable {

    private static final long serialVersionUID = 6269639510544023905L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "学校名称",name="orgName")
    private String orgName;

    @ApiModelProperty(value = "所在层级数",name="level")
    private Integer level;
}
