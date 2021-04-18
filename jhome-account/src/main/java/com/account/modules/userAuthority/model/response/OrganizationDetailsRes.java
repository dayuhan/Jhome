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
@ApiModel(value="组织架构详情列表")
public class OrganizationDetailsRes implements Serializable {
    private static final long serialVersionUID = 7097393695369528174L;
    @ApiModelProperty(value = "id")
    @JsonProperty("id")
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(value = "组织架构名称")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(value = "组织架构父级名称")
    @JsonProperty("parentName")
    private String parentName;

    @ApiModelProperty(value = "所在层级显示排序")
    @JsonProperty("sort")
    private Integer sort;

    @ApiModelProperty(value = "所在层级数")
    @JsonProperty("level")
    private Integer level;

    @ApiModelProperty(value = "父级ID")
    @JsonProperty("parentId")
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long parentId;

    @ApiModelProperty(value = "组织类型，1租户（默认）,2组织内部层级")
    @JsonProperty("type")
    private Integer type;

    @ApiModelProperty(value = "是否叶子节点，0不是（默认）,2是叶子节点")
    @JsonProperty("isLeaf")
    private Integer isLeaf;

    @ApiModelProperty(value = "组织编码")
    @JsonProperty("organizationCode")
    private String organizationCode;

    @ApiModelProperty(value = "创建时间")
    @JsonProperty("createTime")
    private String createTime;

    @ApiModelProperty(value = "修改时间")
    @JsonProperty("updateTime")
    private String updateTime;

    @ApiModelProperty(value = "租户ID")
    @JsonProperty("tenantId")
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long tenantId;
}
