package com.account.common.sbUtil;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 */
@Data
//@NoArgsConstructor
public class PageUtils<T> implements Serializable {

	private static final long serialVersionUID = -2419461766241588799L;
	@ApiModelProperty(value = "响应内容", required = true)
	@JsonProperty( "content")
	private T content;

	@ApiModelProperty(value = "总记录数", required = true)
	@JsonProperty( "total")
	private long total;

	@ApiModelProperty(value = "总页数", required = true)
	@JsonProperty( "total_pages")
	private int totalPages;

	/**
	 * 分页
	 * 
	 * @param list
	 *            列表数据

	 * @param total
	 *            每页记录数
	 * @param list
	 *            当前页数
	 */
	public PageUtils(T list, long total, int pageSize) {
		this.content = list;
		this.total = total;
		this.totalPages = (int)(total +  pageSize - 1)/ pageSize;
	}

}
