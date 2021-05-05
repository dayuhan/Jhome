package com.bracket.common.Bus.AbstractModel;/**
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-08-22 12:30
 **/

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
 * @program: jhome-root
 * @description: 请求层公共类
 * @author: Daxv
 * @create: 2020-08-22 12:30
 **/

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.io.Serializable;

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
 * @description: 分页抽象类对象
 * @author: Daxv
 * @create: 2020-08-18 20:42
 **/
@Getter
@Setter
public abstract class PageInfoRequest implements Serializable {
    private static final long serialVersionUID = 5791002527831580919L;
    //@NotNull(message = "当前页数不能为空")
    @Min(0)
    @ApiModelProperty(value = "[必填,当前页数]", name = "pageNum", example = "1", required = true)
    private Integer pageNum;

    //@NotNull(message = "每页显示条数不能为空")
    // @Range(min=1, max=100, message="pageSize must be range between 1 and 100")
    @ApiModelProperty(value = "[必填,每页条数]", name = "pageSize", example = "10", required = true)
    private Integer pageSize;
}
