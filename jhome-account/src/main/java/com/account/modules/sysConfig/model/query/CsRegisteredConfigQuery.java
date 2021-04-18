package com.account.modules.sysConfig.model.query;

import com.bracket.common.Bus.AbstractModel.PageInfoRequest;
import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;
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
 * @author: 2
 * @create: 2020-08-22
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)

@ApiModel(value = "CsRegisteredConfigQuery 查询对象", description = "")
public class CsRegisteredConfigQuery extends PageInfoRequest {


    private String id;

    @ApiModelProperty(value = "参数名")
    private String paramName;

    @ApiModelProperty(value = "键")
    private String key;

    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "应用名称")
    private String application;

    @ApiModelProperty(value = "服务Id")
    private String serviceId;

    @ApiModelProperty(value = "是否启用 0:启用 1:禁用")
    private Integer isEnable;

    @ApiModelProperty(value = "环境")
    private String profile;

    @ApiModelProperty(value = "分支")
    private String label;
/*

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "修复人")
    private String updateBy;
*/

    @ApiModelProperty(value = "是否是公共组件 0不是 1是")
    private Integer isPublicComponent;


}