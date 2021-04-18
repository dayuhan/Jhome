package com.account.modules.userAuthority.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
@ApiModel(value="数据权限返回对象")
public class GetSysDataResponse implements Serializable {

    private static final long serialVersionUID = -8995288721315613412L;

    @ApiModelProperty(value="ID", name="id", required=true)
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long id;


    @ApiModelProperty(value="role_id", name="角色id", required=true)
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long roleId;

    @ApiModelProperty(value="sys_organization_id", name="组织id", required=true)
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long sysOrganizationId;

}
