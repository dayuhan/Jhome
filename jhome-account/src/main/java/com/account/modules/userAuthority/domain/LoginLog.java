package com.account.modules.userAuthority.domain;

import com.account.modules.userAuthority.model.dto.LoginStatusEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

/**
 * @Description  
 * @Author  
 * @Date 2019-08-13 
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
@TableName("login_log")
public class LoginLog implements Serializable {
	private static final long serialVersionUID =  1608967568764515882L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 用户ID
	 */
	private Integer userId;

	/**
	 * 状态(0:登录，1:退出)
	 */
	private LoginStatusEnum status;

	/**
	 * 登录ip
	 */
	private String loginIp;

	/**
	 * 登录设备(0:web, 1:Ios, 2:Android)
	 */
	private Integer loginDevice;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 创建时间
	 */
	private String loginSource;

	/**
	 * 设备型号
	 */
	private String equipmentType;

	/**
	 * 默认所在学校Id
	 */
	private Long tenantId;
}
