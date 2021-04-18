package com.domain.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value="菜单返回对象")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = -8995288721315613412L;

    @ApiModelProperty(value="ID", name="id", required=true)
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间", name="createTime")
    private String createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间", name="updateTime")
    private String updateTime;

    /**
     * 菜单icon
     */
    @ApiModelProperty(value="菜单icon", name="icon")
    private String icon;

    /**
     * 菜单标题，同title
     */
    @ApiModelProperty(value="菜单标题，同title", name="name")
    private String name;

    /**
     * 是否隐藏或显示：0显示，1隐藏
     */
    @ApiModelProperty(value="是否隐藏或显示：0显示，1隐藏", name="hidden")
    private Boolean hidden;

    @ApiModelProperty(value="组件模块", name="component")
    private String component;

    /**
     * 访问路径，路由path
     */
    @ApiModelProperty(value="访问路径，路由path",name="path",required=true)
    private String path;

    /**
     * 父级ID
     */
    @ApiModelProperty(value="父级ID", name="parentId")
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long parentId;

    /**
     * 父级名称
     */
    @ApiModelProperty(value="父级名称", name="parentName")
    private String parentName;

    /**
     * 重定向路径
     */
    @ApiModelProperty(value="重定向路径", name="redirect")
    private String redirect;

    /**
     * 所在菜单层级显示排序
     */
    @ApiModelProperty(value="所在菜单层级显示排序", name="sort")
    private Integer sort;

    /**
     * 所在层级数
     */
    @ApiModelProperty(value="所在层级数", name="level")
    private Integer level;

    /**
     * 是否需要登录的权限: 0不需要，1需要
     */
    @ApiModelProperty(value="是否需要登录的权限: 0不需要，1需要", name="requiresAuth")
    private Boolean requiresAuth;

    /**
     * 是否显示子菜单
     */
    @ApiModelProperty(value="是否显示子菜单", name="showChild")
    private short showChild;

    /**
     * 菜单类型，1菜单（默认），2按钮
     */
    @ApiModelProperty(value="菜单类型，1菜单（默认），2按钮", name="type")
    private Integer type;

    /**
     * 权限标识
     */
    @ApiModelProperty(value="权限标识", name="identification")
    private String identification;

    @ApiModelProperty(value="字菜单", name="children", required=true)
    private List<SysMenu> children;
}
