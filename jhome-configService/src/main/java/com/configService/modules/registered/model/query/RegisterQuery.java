package com.configService.modules.registered.model.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
 * @program: account-root
 * @description: 注册类
 * @author: Daxv
 * @create: 2020-12-31 15:33
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({})
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "注册实体对象", description = "注册实体对象")
public class RegisterQuery {
    @ApiModelProperty(value = "申请码", name = "key", example = "key", required = true)
    private String declareCode;
    @ApiModelProperty(value = "注册码", name = "key", example = "key", required = true)

    private String registerCode;
    @ApiModelProperty(value = "授权码", name = "key", example = "key", required = true)
    private String grantCode;

    @ApiModelProperty(value = "未激活：0 激活：1", name = "value", example = "1")
    private Integer validationResults;

}
