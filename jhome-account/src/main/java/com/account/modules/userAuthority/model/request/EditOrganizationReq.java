package com.account.modules.userAuthority.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Data
@ApiModel(value = "编辑组织架构请求参数")
public class EditOrganizationReq implements Serializable {

    private static final long serialVersionUID = 5309696602085496928L;
    @NotNull(message = "[ID必填]")
    @ApiModelProperty(value = "必填,ID", name = "id", example = "1927705550081777664", required = true)
    private Long id;

    @NotNull(message = "[名称必填]")
    @ApiModelProperty(value = "必填,组织架构名称", name = "name", example = "深职院007", required = true)
    private String name;

    @NotNull(message = "[父级ID必填]")
    @ApiModelProperty(value = "必填,父级ID {如果是根目录parentId = 0}", name = "parentId" , example = "0", required = true)
    private Long parentId;

}
