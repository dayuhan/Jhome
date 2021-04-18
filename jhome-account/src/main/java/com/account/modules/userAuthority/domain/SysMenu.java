package com.account.modules.userAuthority.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

/**
 * @Description  
 * @Author  
 * @Date 2019-11-26 
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
@TableName("sys_menu")
public class SysMenu implements Serializable {
	private static final long serialVersionUID =  4141651478092429918L;


	private Long id;

	/**
	 * 菜单icon
	 */
	private String icon;

	/**
	 * 菜单标题，同title
	 */
	private String name;

	/**
	 * 是否隐藏或显示：0显示，1隐藏
	 */
	private Boolean hidden;

	private String component;

	/**
	 * 访问路径，路由path
	 */
	private String path;

	/**
	 * 重定向路径
	 */
	private String redirect;

	/**
	 * 所在菜单层级显示排序
	 */
	private Integer sort;

	/**
	 * 所在层级数
	 */
	private Integer level;

	/**
	 * 父级ID
	 */
	private Long parentId;

	/**
	 * 是否需要登录的权限: 0不需要，1需要
	 */
	private Boolean requiresAuth;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 创建人ID
	 */
	private Long createUserId;

	/**
	 * 更新时间
	 */
	private String updateTime;

	/**
	 * 更新人ID
	 */
	private Long updateUserId;

	/**
	 * 假删除：0未删除，1已删除
	 */
	private Integer deleteFlag;

	/**
	 * 产品Id
	 */
	private Long productId;

	/**
	 * 租户ID
	 */
	private Long tenantId;

	/**
	 * 菜单类型，1菜单（默认），2按钮
	 */
	private Integer type;

	/**
	 * 权限标识
	 */
	private String identification;

}
