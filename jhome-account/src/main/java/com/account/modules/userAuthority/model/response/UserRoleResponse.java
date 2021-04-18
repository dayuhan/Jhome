package com.account.modules.userAuthority.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="获取当前组织所有的角色列表返回参数")
public class UserRoleResponse implements Serializable {
    private static final long serialVersionUID = -1277322661441613117L;
    @ApiModelProperty(value="角色ID",name="role_id",example="644699827135053825",required=true)
    private long roleId;
    @ApiModelProperty(value="角色名称",name="role_name",example="教师",required=true)
    private String roleName;
    @ApiModelProperty(value="是否是当前组织所在默认角色：0不是，1是。",name="defaultFlag", required=true)
    private Integer defaultFlag;
}
