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
@TableName("organization")
public class Organization  implements Serializable {
	private static final long serialVersionUID =  4745112764403028378L;

	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 组织编码
	 */
	private String organizationCode;

	/**
	 * 所在层级显示排序
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
	 * 组织类型，1租户（默认），2组织内部层级
	 */
	private Integer type;

	/**
	 * 是否叶子节点，0不是（默认），2是叶子节点
	 */
	private Integer isLeaf;

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
}
