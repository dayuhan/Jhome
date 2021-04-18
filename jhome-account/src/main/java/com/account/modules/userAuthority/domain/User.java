package com.account.modules.userAuthority.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("user")
public class User implements Serializable {
	private static final long serialVersionUID =  313784899553740653L;

	/**
	 * 用户ID
	 */
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id;

	/**
	 * 账号(用于登录）
	 */
	private String loginName;

	/**
	 * 用户密码（用于登录）
	 */
	private String password;

	/**
	 * 盐值
	 */
	private String salt;

	/**
	 * 昵称(用于显示）
	 */
	private String nickName;

	/**
	 * 真实姓名，给老师看，格式为昵称(真实姓名)
	 */
	private String realName;

	/**
	 * 性别：0男，1女
	 */
	private Integer sex;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 注册来源：来自APP注册0，来自后台注册1
	 */
	private Integer source;

	/**
	 * 工号
	 */
	private String no;

	/**
	 * 头像（文件ID）
	 */
	private String photo;

	/**
	 * 职称
	 */
	private String title;

	/**
	 * 个人介绍，签名
	 */
	private String resume;

	/**
	 * 记录注册短信验证Code
	 */
	private String smsCode;

	/**
	 * 账号状态：0试用，1启动，2禁用
	 */
	private Integer status;

	/**
	 * 试用有效时间开始
	 */
	private String validTimeStart;

	/**
	 * 试用有效时间结束
	 */
	private String validTimeEnd;

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
	 * 租户ID
	 */
	private Long tenantId;

	/**
	 *  角色ID
	 */
	private Long roleId;
}
