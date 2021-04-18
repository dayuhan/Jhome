package com.account.modules.userAuthority.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

/**
 * @Description  
 * @Author Levi_liu 
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
@TableName("tenant" )
public class Tenant  implements Serializable {
	private static final long serialVersionUID =  3405892384813258106L;

	/**
	 * 租户ID
	 */
	private Long id;

	/**
	 * 租户名称（学校企业机构名称）
	 */
	private String tenantName;

	/**
	 * 地址
	 */
	private String tenantAddress;

	/**
	 * 合同编号
	 */
	private Long contractNo;

	/**
	 * 合同到期时间
	 */
	private String contractExpireTime;

	/**
	 * 免费维护时间，以天为单位，0表示无期限
	 */
	private Long maintainDate;

	/**
	 * 免费维护开始时间
	 */
	private String maintainStartTime;

	/**
	 * 免费维护结束时间
	 */
	private String maintainEndTime;

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

}
