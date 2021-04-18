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
@ApiModel(value="菜单添加修改请求对象")
public class AddOrUpdateMenuRequest implements Serializable {
    private static final long serialVersionUID = -2371261262005066851L;

    @ApiModelProperty(value="id", name="id")
    private Long id;

    /**
     * 菜单icon
     */
    @ApiModelProperty(value="菜单icon", name="icon")
    private String icon;

    /**
     * 菜单标题，同title
     */
    @NotNull(message="name must be not null")
//    @Length(min = 2, max = 20)
    @ApiModelProperty(value="菜单标题", name="name", required=true)
    private String name;


    /**
     * 是否隐藏或显示：0显示，1隐藏
     */
    @NotNull(message="hidden must be not null")
    @ApiModelProperty(value="是否隐藏或显示：0显示，1隐藏", name="hidden")
    private boolean hidden;

    /**
     * 菜单模块（添加菜单必填）
     */
    @NotNull(message="component must be not null")
    @ApiModelProperty(value="菜单模块", name="component")
    private String component;

    /**
     * 访问路径，路由path（添加菜单必填）
     */
    @NotNull(message="path must be not null")
    @ApiModelProperty(value="访问路径", name="path")
    private String path;

    /**
     * 重定向路径
     */
    @ApiModelProperty(value="重定向路径", name="redirect")
    private String redirect;

    /**
     * 所在菜单层级显示排序（添加菜单必填）
     */
    @NotNull(message="sort must be not null")
    @ApiModelProperty(value="所在菜单层级显示排序", name="sort")
    private Integer sort;

    /**
     * 父级ID
     */
    @NotNull(message="parentId must be not null")
    @ApiModelProperty(value="父级ID", name="parentId", required=true)
    private Long parentId;

    /**
     * 是否需要登录的权限: 0不需要，1需要
     */
    @NotNull(message="requiresAuth must be not null")
    @ApiModelProperty(value="是否需要登录的权限: 0不需要，1需要", name="requiresAuth")
    private boolean requiresAuth;

    /**
     * 菜单类型，1菜单（默认），2按钮
     */
    @NotNull(message="type must be not null")
    @FlagValidator(value = {"1","2"}, message = "菜单类型不正确(1菜单（默认），2按钮))")
    @ApiModelProperty(value="菜单类型", name="type", example="1", required=true)
    private Integer type;

    /**
     * 所在层级数
     */
    @NotNull(message="level must be not null")
    @ApiModelProperty(value="所在层级数", name="level", example="1", required=true)
    private Integer level;

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
