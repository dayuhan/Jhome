package com.account.modules.userAuthority.model.request;

import com.bracket.common.Bus.AbstractModel.PageInfoRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value="用户组织角色成员查询请求参数")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class UserOrgRolePageRequest extends PageInfoRequest implements Serializable {

    private static final long serialVersionUID = -2398811577504059947L;
    @NotNull(message="roleId must be not null")
    @ApiModelProperty(value="角色Id", name="roleId", required=true)
    private Long roleId;

//    @ApiModelProperty(value="用户id", name="userId", required=true)
//    private Long userId;

    @ApiModelProperty(value="搜索值", name="searchName", required=true)
    private String searchName;

//    @ApiModelProperty(value="账号", name="loginName", required=true)
//    private String loginName;
//
//    @ApiModelProperty(value = "姓名", required=true)
//    private String realName;
//
//    @ApiModelProperty(value = "手机号")
//    @Pattern(regexp = "^[0-9]{11}$", message = "请输入正确的手机号")
//    private String mobile;
}
