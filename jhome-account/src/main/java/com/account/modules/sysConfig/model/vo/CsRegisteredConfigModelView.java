package com.account.modules.sysConfig.model.vo;

import com.bracket.common.Bus.AbstractModel.PageInfoRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


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
 * @description:
 * @author: Daxv
 * @create: 2020-08-29 17:06
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "参数设置查询返回结果对象")
public class CsRegisteredConfigModelView extends PageInfoRequest {
    private static final long serialVersionUID = 4921214124588261605L;

    @ApiModelProperty(value = "主键")

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

    @ApiModelProperty(value = "是否是公共组件 0不是 1是")
    private Integer isPublicComponent;

    @ApiModelProperty(value = "应用名称")
    private String applicationName;


}
