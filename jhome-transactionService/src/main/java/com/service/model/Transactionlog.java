package com.service.model;


import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * //
 * //                       .::::.
 * //                     .::::::::.
 * //                    :::::::::::
 * //                 ..:::::::::::'
 * //              '::::::::::::'
 * //                .::::::::::
 * //           '::::::::::::::..
 * //                ..::::::::::::.
 * //              ``::::::::::::::::
 * //               ::::``:::::::::'        .:::.
 * //              ::::'   ':::::'       .::::::::.
 * //            .::::'      ::::     .:::::::'::::.
 * //           .:::'       :::::  .:::::::::' ':::::.
 * //          .::'        :::::.:::::::::'      ':::::.
 * //         .::'         ::::::::::::::'         ``::::.
 * //     ...:::           ::::::::::::'              ``::.
 * //    ```` ':.          ':::::::::'                  ::::..
 * //                       '.:::::'                    ':'````..
 *
 * @description: 对象
 * @author: 1
 * @create: 2020-09-17
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Transactionlog对象", description = "")
public class Transactionlog implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    @ApiModelProperty(value = "0：待确认 1：确认消息  提交事务 2：删除消息  3： 已完成  4：失败  回滚事务" )
    private Integer type;
    @ApiModelProperty(value = "0 正常 1删除")
    private Integer status;

    @ApiModelProperty(value = "消息体")
    private String messageBody;

    @ApiModelProperty(value = "生产者服务")
    private String producer;

    @ApiModelProperty(value = "消费者服务",notes = "其他上下游服务需要处理，消息队列名称为:jhome-account,jhome-fileStore ")
    private String consumer;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Override
    public String toString() {
        return "Transactionlog{" +
                "id=" + id +
                ", type=" + type +
                ", status=" + status +
                ", messageBody=" + messageBody +
                ", producer=" + producer +
                ", consumer=" + consumer +
                ", createTime=" + createTime +
                "}";
    }
}