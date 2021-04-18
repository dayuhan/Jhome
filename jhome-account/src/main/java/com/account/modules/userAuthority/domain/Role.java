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
@TableName("role")
public class Role implements Serializable {
	private static final long serialVersionUID =  3714689456150009266L;

	/**
	 * 角色表ID
	 */
	private Long id;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 备注
	 */
	private String remark;

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
	 * 产品Id
	 */
	private Long productId;
}
