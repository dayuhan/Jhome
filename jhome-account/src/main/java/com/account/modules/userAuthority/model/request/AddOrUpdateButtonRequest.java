package com.account.modules.userAuthority.model.request;

import com.ar.common.validator.FlagValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author daxv
 * @date 2019/12/3 11:23
 */
@Data
@ApiModel(value="菜单按钮添加修改请求对象")
public class AddOrUpdateButtonRequest implements Serializable {
    private static final long serialVersionUID = -7528399162810939171L;

    @ApiModelProperty(value="id", name="id")
    private Long id;
    /**
     * 菜单标题，同title
     */
    @NotNull(message="name must be not null")
    @ApiModelProperty(value="菜单标题", name="name", required=true)
    private String name;

    /**
     * 父级ID
     */
    @NotNull(message="parentId must be not null")
    @ApiModelProperty(value="父级ID", name="parentId", required=true)
    private Long parentId;

    /**
     * 菜单类型，1菜单（默认），2按钮
     */
    @NotNull(message="type must be not null")
    @FlagValidator(value = {"1","2"}, message = "菜单类型不正确(1菜单（默认），2按钮))")
    @ApiModelProperty(value="菜单类型，1菜单（默认），2按钮", name="type", example="1", required=true)
    private Integer type;

    /**
     * 是否隐藏或显示：0显示，1隐藏
     */
    @NotNull(message="hidden must be not null")
    @ApiModelProperty(value="是否隐藏或显示：0显示，1隐藏", name="hidden")
    private boolean hidden;

    /**
     * 所在层级数
     */
    @NotNull(message="level must be not null")
    @ApiModelProperty(value="所在层级数", name="level", example="1", required=true)
    private Integer level;

    /**
     * 所在菜单层级显示排序（添加菜单必填）
     */
    @NotNull(message="sort must be not null")
    @ApiModelProperty(value="所在菜单层级显示排序", name="sort")
    private Integer sort;

    /**
     * 权限标识
     */
    @NotNull(message="identification must be not null")
    @ApiModelProperty(value="权限标识", name="identification", required=true)
    private String identification;

    /**
     * 产品Id
     */
    @NotNull(message="productId must be not null")
    @ApiModelProperty(value="产品Id", name="productId", required=true)
    private Long productId;

    /**
     * 租户ID
     */
    @NotNull(message="tenantId must be not null")
    @ApiModelProperty(value="租户ID", name="tenantId", required=true)
    private Long tenantId;
}
