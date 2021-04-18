package com.configService.modules.registered.model.bo;

import com.configService.modules.registered.model.validator.IsEnableValidator;
import com.bracket.common.Bus.AbstractModel.DataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.validation.constraints.NotNull;

/**
 //
 //                       .::::.
 //                     .::::::::.
 //                    :::::::::::
 //                 ..:::::::::::'
 //              '::::::::::::'
 //                .::::::::::
 //           '::::::::::::::..
 //                ..::::::::::::.
 //              ``::::::::::::::::
 //               ::::``:::::::::'        .:::.
 //              ::::'   ':::::'       .::::::::.
 //            .::::'      ::::     .:::::::'::::.
 //           .:::'       :::::  .:::::::::' ':::::.
 //          .::'        :::::.:::::::::'      ':::::.
 //         .::'         ::::::::::::::'         ``::::.
 //     ...:::           ::::::::::::'              ``::.
 //    ```` ':.          ':::::::::'                  ::::..
 //                       '.:::::'                    ':'````..
 * @program: account-root
 * @description:
 * @author: Daxv
 * @create: 2020-08-08 12:43
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"address"})
@ApiModel(value = "SysConfigProperties对象", description = "对象注册实体")
public class SysConfigProperties extends DataEntity<SysConfigProperties> {

    @ApiModelProperty(value = "配置参数名称", name = "paramName", example = "数据库配置地址")
    private String paramName;
    @NotNull(message = "配置参数的key不能为空")
    @ApiModelProperty(value = "配置参数的key", name = "key", example = "database.url", required = true)
    private String key;

    @NotNull(message = "配置参数key对应的value不能为空")
    @ApiModelProperty(value = "配置参数key对应的value", name = "value", required = true, example = "jdbc:mysql://192.135.1.1:3306/get_edb?serverTimezone=UTC&userSSL=false")
    private String value;

    @NotNull(message = "应用名称不能为空")
    @ApiModelProperty(value = "应用名称", name = "application", example = "Lux-account")
    private String application;

    @IsEnableValidator(value = "是否启用 0:启用 1:禁用", message = "默认为0启用")
    @ApiModelProperty(value = "是否启用 0:启用 1:禁用", name = "isEnable", example = "0")
    private Integer isEnable;

    @ApiModelProperty(value = "环境", name = "profile", example = "dev")
    private String profile;

    @ApiModelProperty(value = "分支", name = "label", example = "master")
    private String label;

    @ApiModelProperty(value = "是否启用 0:不是公共组件 1:是公共组件", name = "isPublicComponent", example = "0")
    private Integer isPublicComponent;

}
