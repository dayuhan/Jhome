package com.account.modules.userAuthority.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

/**
 *
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
@TableName("role_sysmenu")
public class RoleSysMenu implements Serializable {
	private static final long serialVersionUID =  1297157786374686488L;

	private Long id;

	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 权限模块ID
	 */
	private Long sysMenuId;
}
