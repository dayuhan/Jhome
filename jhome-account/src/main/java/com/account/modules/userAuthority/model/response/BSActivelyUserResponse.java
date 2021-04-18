package com.account.modules.userAuthority.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Author: Da xv
 * Create date: 2019.12.24
 */
@Data
@ApiModel(value="后台首页活跃用户统计返回对象")
public class BSActivelyUserResponse implements Serializable {
    private static final long serialVersionUID = -7239837532383223066L;

    @ApiModelProperty(value = "最近一天活跃用户数")
    @JsonProperty("oneDayActively")
    private Integer oneDayActively;

    @ApiModelProperty(value = "最近一周活跃用户数")
    @JsonProperty("oneWeekDayActively")
    private Integer oneWeekDayActively;

    @ApiModelProperty(value = "最近一月活跃用户数")
    @JsonProperty("oneMonthActively")
    private Integer oneMonthActively;
}
