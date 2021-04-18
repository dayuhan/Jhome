package com.account.modules.userAuthority.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

/**
 * @Description  
 * @Author  daxv
 * @Date 2019-10-10 
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
@TableName("sys_exception")
public class SysException implements Serializable {
	private static final long serialVersionUID =  7975796589528869288L;

	private Long id;

	/**
	 * 模块
	 */
	private String module;

	/**
	 * 行为
	 */
	private String action;

	/**
	 * 异常信息
	 */
	private String exceptionInfo;

	/**
	 * 设备信息
	 */
	private String deviceInfo;

	/**
	 * 操作系统版本
	 */
	private String osVersion;

	/**
	 * CPU
	 */
	private String cpu;

	/**
	 * 内存
	 */
	private String memory;

	/**
	 * 浏览器内核版本
	 */
	private String browserKernelVersion;

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
	private Long deleteFlag;

	/**
	 * 租户ID
	 */
	private Long tenantId;
}
