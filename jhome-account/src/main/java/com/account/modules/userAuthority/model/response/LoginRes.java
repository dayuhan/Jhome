package com.account.modules.userAuthority.model.response;

import com.account.modules.userAuthority.model.dto.UserOrgRoleDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class LoginRes  {

    private static final long serialVersionUID = 9211148281541867075L;
    @ApiModelProperty(value = " 用户ID")
    private Integer userId;

    @ApiModelProperty(value = "用户头像")
    private String photo;

    @ApiModelProperty(value = "用户名称")
    private String realName;

    @ApiModelProperty(value = "登录名称")
    private String loginName;

    @ApiModelProperty(value = "头像路径，为主站的相对路径")
    private String imgUrl;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "签名")
    private String resume;

    @ApiModelProperty(value="默认租户ID",name="tenantId",example="189516514267758523",required=true)
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long tenantId;
    /**
     * 产品Id
     */
    @ApiModelProperty(value="产品Id", name="productId", example="11110000", required=true)
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long productId = 11110000L;

    @ApiModelProperty(value = "文件服务器地址前缀")
    private String dfsServer;

    @ApiModelProperty(value = "赛名师服务器地址前缀1")
    private String smsServer;

    @ApiModelProperty(value = "文件服务器上传地址前缀")
    private String dfsUploadServer;

    @ApiModelProperty(value = "默认角色名称(多个逗号隔开)")
    private String roleNames;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String salt;

    @JsonIgnore
    private Integer status;

    @ApiModelProperty(value = "我的学校名称")
    private String schoolName;

    @ApiModelProperty(value = "用户组织角色列表")
    private List<UserOrgRoleDTO> userOrgRoleList;

    @ApiModelProperty(value = "远程token")
    private String remotelyToken;
    @ApiModelProperty(value = "本地token")
    private String jhomeToken;
    @ApiModelProperty(value = "功能数据权限")
    private List<GetSysMenuResponse> sysMenuResponses;





}
