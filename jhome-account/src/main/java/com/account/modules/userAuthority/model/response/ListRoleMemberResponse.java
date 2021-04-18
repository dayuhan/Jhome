package com.account.modules.userAuthority.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel(value="角色的成员列表请求返回参数")
public class ListRoleMemberResponse implements Serializable {

    private static final long serialVersionUID = 201393262526635278L;
    @ApiModelProperty(value="id", name="id", required=true)
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(value="角色id", name="roleId", required=true)
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long roleId;

    @ApiModelProperty(value="用户id", name="userId", required=true)
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long userId;

    @ApiModelProperty(value="账号", name="loginName", required=true)
    private String loginName;

    @ApiModelProperty(value = "姓名", required=true)
    private String realName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

}
