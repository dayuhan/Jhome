package com.account.modules.userAuthority.model.request;

import com.ar.common.validator.FlagValidator;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Data
@ApiModel(value = "添加组织架构请求参数")
public class AddOrganizationReq implements Serializable {

    private static final long serialVersionUID = 6862739043889096237L;

    @NotNull(message = "[名称必填]")
    @ApiModelProperty(value = "必填,组织架构名称", name = "name", example = "深职院007", required = true)
    private String name;

    @NotNull(message = "[父级ID必填]")
    @ApiModelProperty(value = "必填,父级ID {如果是根目录parentId = 0}", name = "parentId" , example = "0", required = true)
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long parentId;

    @ApiModelProperty(value = "所在层级显示排序", name = "sort")
    private Integer sort;

    @ApiModelProperty(value = "所在层级数", name = "level")
    private Integer level;

    @FlagValidator(value = {"1","2"}, message = "组织类型不正确(1租户（默认）,2组织内部层级)")
    @ApiModelProperty(value = "组织类型，1租户（默认）,2组织内部层级", name = "type")
    private Integer type;

    @FlagValidator(value = {"0","1"}, message = "是否叶子节点不正确(0不是（默认），1是叶子节点)")
    @ApiModelProperty(value = "是否叶子节点，0不是（默认），1是叶子节点", name = "isLeaf")
    private Integer isLeaf;

    @ApiModelProperty(value = "创建人ID", name = "createUserId")
    private Long createUserId;

    @ApiModelProperty(value = "租户ID", name = "tenantId")
    private Long tenantId;
}
