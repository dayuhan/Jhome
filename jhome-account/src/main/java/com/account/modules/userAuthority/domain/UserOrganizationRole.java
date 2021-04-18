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
@TableName("user_organization_role" )
public class UserOrganizationRole implements Serializable {
	private static final long serialVersionUID =  6745424406324331678L;

	private Long id;

	private Long userId;

	private Long orgId;

	private Long roleId;

	private String createTime;

	/**
	 * 租户ID
	 */
	private Long tenantId;


	/**
	 * 是否是当前默认所在组织：0不是，1是。
	 */
	private Integer defaultFlag;
}
