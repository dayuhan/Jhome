package com.account.modules.userAuthority.model.response;

import com.account.modules.userAuthority.model.dto.UserDetailsRoleDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Data
@ApiModel(value="用户信息列表")
public class UserInfoBackstageRes implements Serializable {

    private static final long serialVersionUID = -9022096222425794640L;
    @ApiModelProperty(value = "id")
    @JsonProperty("id")
    private Integer id;

    @ApiModelProperty(value = "用户名")
    @JsonProperty("loginName")
    private String loginName;

    @ApiModelProperty(value = "手机号")
    @JsonProperty("mobile")
    private String mobile;

    @ApiModelProperty(value = "昵称")
    @JsonProperty("nickName")
    private String nickName;

    @ApiModelProperty(value = "真实姓名")
    @JsonProperty("realName")
    private String realName;

    @ApiModelProperty(value = "性别")
    @JsonProperty("sex")
    private Integer sex;

    @ApiModelProperty(value = "工号")
    @JsonProperty("no")
    private String no;

    @ApiModelProperty(value = "注册来源：来自APP注册0，来自后台注册1")
    @JsonProperty("status")
    private Integer source;

    @ApiModelProperty(value="租户ID",name="tenantId")
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long tenantId;

    @ApiModelProperty(value = "用户角色列表")
    @JsonProperty("orgRoleList")
    private List<UserDetailsRoleDTO> orgRoleList;
}
