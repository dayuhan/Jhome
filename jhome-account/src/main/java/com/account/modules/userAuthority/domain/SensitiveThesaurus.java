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
@TableName("sensitive_thesaurus" )
public class SensitiveThesaurus  implements Serializable {
	private static final long serialVersionUID =  8075542265067355623L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 敏感词
	 */
	private String sensitiveWords;

	/**
	 * 假删除：0未删除，1已删除
	 */
	private Long deleteFlag;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 修改时间
	 */
	private String updateTime;

}
