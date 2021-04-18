package com.account.modules.userAuthority.model.request;

import com.ar.common.validator.FlagValidator;
import com.bracket.common.Bus.AbstractModel.PageInfoRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Data
@ApiModel(value = "用户信息")
public class ListUserOrgIdsReq extends PageInfoRequest implements Serializable {

    private static final long serialVersionUID = 7050609175021622876L;

    @ApiModelProperty(value = "组织架构IDs", name = "orgIds")
    private Long[] orgIds;

    @ApiModelProperty(value = "手机号、账号、姓名", name = "loginName")
    private String valueName;

    @FlagValidator(value = {"0","1"}, message = "注册来源不正确(注册来源：来自APP注册0，来自后台注册1)")
    @ApiModelProperty(value = "注册来源：来自APP注册0，来自后台注册1", name = "source")
    private Integer source;

    @ApiModelProperty(value = "开始时间", name = "startTime")
    private String startTime;

    @ApiModelProperty(value = "结束时间", name = "endTime")
    private String endTime;

    @ApiModelProperty(value = "租户Id", name = "tenantId")
    private Long tenantId;

    @ApiModelProperty(value = "用户Id", name = "userId")
    private Long userId;

    @ApiModelProperty(value = "角色名称", name = "roleName")
    private String roleName;

}
