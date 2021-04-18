package com.account.modules.userAuthority.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Data
@ApiModel(value = "用户信息")
public class UserInfoReq implements Serializable {

    private static final long serialVersionUID = 2608609592269900340L;
    @ApiModelProperty(value="用户账号",name="loginName")
    private String loginName;

    @ApiModelProperty(value="用户id",name="userId")
    private Integer userId;

    @ApiModelProperty(value="昵称",name="nickName")
    private String nickName;

    @ApiModelProperty(value="密码",name="password")
    private String password;

    @ApiModelProperty(value="真实姓名",name="realName")
    private String realName;

    @ApiModelProperty(value="性别：0男,1女",name="sex")
    private String sex;

    @ApiModelProperty(value="手机号码",name="mobile")
    private String mobile;

    @ApiModelProperty(value="租户ID",name="tenantId")
    private Long tenantId;

    @ApiModelProperty(value="删除",name="deleteFlag")
    private Integer deleteFlag;

}
