package com.account.common.sbUtil.domain;

import com.account.common.sbUtil.model.vo.OperateLogTypeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description  
 * @Author  
 * @Date 2019-12-13 
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
@TableName("operate_log")
@Accessors(chain = true)
public class OperateLog extends Model<OperateLog> implements Serializable {
	private static final long serialVersionUID =  6467135584322114241L;

	private Long id;

	/**
	 * 操作人Id
	 */
	private Long userId;

	private String bigModule;

	private String smallModule;

	/**
	 * 操作表名
	 */
	private String tableName;

	/**
	 * 标记：0插入，1删除，2修改，3访问
	 */
	private OperateLogTypeEnum type;

	private String data1;

	private String data2;

	private String data3;

	private String data4;

	private String data5;

	/**
	 * 备注：某某 修改 表‘教材’ 字段‘name’ '发动机原理' 为 ‘发动机构造与原理’
	 */
	private String remarks;

	/**
	 * 操作时间
	 */
	private String createTime;

}
