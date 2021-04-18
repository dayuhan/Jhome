package com.account.modules.userAuthority.model.request;

import com.account.modules.userAuthority.model.dto.RoleOrgListDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Data
@ApiModel(value = "添加-编辑用户请求参数")
public class OperateUserReq implements Serializable {
    private static final long serialVersionUID = 8577831243333563768L;

    @ApiModelProperty(value = "用户账号", name = "loginName", example = "账号酷酷")
    @NotNull(message = "用户账号不能为空")
    private String loginName;

    @ApiModelProperty(value = "昵称", name = "nickName", example = "昵称酷酷")
    private String nickName;

    @ApiModelProperty(value = "真实姓名", name = "realName", example = "姓名酷酷")
    private String realName;

    @ApiModelProperty(value = "性别：0男,1女", name = "sex", example = "1")
    private Integer sex;

    @ApiModelProperty(value = "工号", name = "no", example = "工号酷酷")
    private String no;


    @ApiModelProperty(value = "角色id", name = "roleId", example = "角色id")
    private long roleId;

    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "请输入正确的手机号")
    @NotNull(message = "手机号码不能为空")
    @ApiModelProperty(value = "手机号码", name = "mobile", example = "1357200000")
    private String mobile;

    @ApiModelProperty(value = "租户ID", name = "tenantId")
    @NotNull(message = "租户ID不能为空")
    private Long tenantId;

    @ApiModelProperty(value = "用户ID", name = "userId")
    @NotNull(message = "用户不能为空不能为空")

    private Integer userId;

    @Valid
    //@NotNull(message = "roleOrgList must be not null")
    @ApiModelProperty(value = "角色组织列表", name = "roleOrgList", required = true)
    private List<RoleOrgListDTO> roleOrgList;

    @ApiModelProperty(value = "用户密码", name = "passWord")
    private String passWord;
    @ApiModelProperty(value = "状态", name = "status" , example = "1")
    private Integer status;



}
