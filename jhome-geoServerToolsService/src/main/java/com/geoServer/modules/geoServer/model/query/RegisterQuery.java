package com.geoServer.modules.geoServer.model.query;

import com.bracket.common.Bus.AbstractModel.PageInfoRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * @description: 注册对象
 * @author: 2
 * @create: 2020-09-04
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "", description = "")
public class RegisterQuery extends PageInfoRequest {

    @ApiModelProperty(value = "申请码")
    private String declareCode;

    @ApiModelProperty(value = "注册码")
    private String registerCode;

    @ApiModelProperty(value = "授权码")
    private String grantCode;

}