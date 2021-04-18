package com.account.modules.userAuthority.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

/**
 * @Description  
 * @Author  
 * @Date 2019-08-02 
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
@TableName("sys_arg")
public class SysArg implements Serializable {

	private static final long serialVersionUID = 9009925502128408382L;

	private Long id;

	/**
	 * 文件服务器地址前缀
	 */
	private String dfsServer;

	/**
	 * 文件服务器上传地址前缀
	 */
	private String uploadServer;

	/**
	 * 文件服务器地址前缀
	 */
	private String dfsServerLocal;

	/**
	 * 文件服务器上传地址前缀
	 */
	private String uploadServerLocal;

	/**
	 * 赛名师服务器地址前缀
	 */
	private String smsServer;

	/**
	 * 整个平台的超级管理员角色id
	 */
	private Long platformSuperManagerRoleId;

	/**
	 * 教师默认角色id
	 */
	private Long teacherDefaultRoleId;

	/**
	 * 管理员默认角色id
	 */
	private Long managerDefaultRoleId;

	/**
	 * 学生默认角色id
	 */
	private Long studentDefaultRoleId;

	/**
	 * 备用字段1
	 */
	private String reserve1;

	/**
	 * 备用字段2
	 */
	private String reserve2;

	/**
	 * 备用字段3
	 */
	private String reserve3;

}
