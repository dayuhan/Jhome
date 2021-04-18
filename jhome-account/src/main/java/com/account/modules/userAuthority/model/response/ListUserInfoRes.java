package com.account.modules.userAuthority.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Data
@ApiModel(value="用户集合列表")
public class ListUserInfoRes implements Serializable {

    private static final long serialVersionUID = -2295016623629237166L;

    @ApiModelProperty(value = "id")
    @JsonProperty("id")
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(value = "用户账号")
    @JsonProperty("loginName")
    private String loginName;

    @ApiModelProperty(value = "昵称")
    @JsonProperty("nickName")
    private String nickName;

    @ApiModelProperty(value="手机号码",name="mobile")
    private String mobile;

    @ApiModelProperty(value="注册来源：来自APP注册0，来自后台注册1",name="source")
    private String source;

    @ApiModelProperty(value = "真实姓名")
    @JsonProperty("realName")
    private String realName;

    @ApiModelProperty(value = "创建时间")
    @JsonProperty("createTime")
    private String createTime;

    @ApiModelProperty(value = "账号状态：0试用，1启动，2禁用")
    @JsonProperty("status")
    private String status;

    @ApiModelProperty(value = "工号")
    @JsonProperty("no")
    private String no;

    @ApiModelProperty(value = "住户ID")
    @JsonProperty("tenantId")
    private String tenantId;
    @ApiModelProperty(value = "性别", name = "sex")
    @JsonProperty("sex")
    private int sex;

    @ApiModelProperty(value = "角色id", name = "roleId")
    @JsonProperty("roleId")
    private String roleId;


}
