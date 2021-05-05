package com.bracket.common.Bus.AbstractModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import sun.rmi.runtime.Log;

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
 * @program: jhome-root
 * @description: 分页
 * @author: Daxv
 * @create: 2020-09-12 12:07
 **/
@Data
public class PageUtils<T> implements Serializable {
    private static final long serialVersionUID = -2419461766241588799L;
    @ApiModelProperty(value = "响应内容", required = true)
    @JsonProperty("content")
    private T content;

    @ApiModelProperty(value = "总记录数", required = true)
    @JsonProperty("total")
    private long total;

    @ApiModelProperty(value = "总页数", required = true)
    @JsonProperty("totalPages")
    private int totalPages;

    public PageUtils(T list, long total, int pageSize) {
        this.content = list;
        this.total = total;
        this.totalPages = (int) (total + pageSize - 1) / pageSize;
    }
}
