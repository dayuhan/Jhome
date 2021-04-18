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
@TableName("sms_record" )
public class SmsRecord  implements Serializable {
	private static final long serialVersionUID =  6397954390235119709L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 模板ID
	 */
	private String templateId;

	/**
	 * 短信类型:1验证码短信 2验证码语音
	 */
	private Long smsType;

	/**
	 * 验证码
	 */
	private String captcha;

	/**
	 * 发送时间
	 */
	private String beginTime;

	/**
	 * 接收时间
	 */
	private String endTime;

	/**
	 * 假删除：0未删除，1已删除
	 */
	private Integer deleteFlag;

	/**
	 * 操作标识： 1.注册  2.忘记密码
	 */
	private Integer operationFlag;
}
