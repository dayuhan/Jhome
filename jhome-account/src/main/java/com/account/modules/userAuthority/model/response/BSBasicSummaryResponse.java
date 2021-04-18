package com.account.modules.userAuthority.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Author: Da xv
 * Create date: 2019.12.25
 */
@Data
@ApiModel(value="后台统计基本数据返回对象")
public class BSBasicSummaryResponse implements Serializable {
    private static final long serialVersionUID = 8670601781340154305L;

    @ApiModelProperty(value = "注册用户数")
    @JsonProperty("registedUserNum")
    private Integer registedUserNum;

    @ApiModelProperty(value = "全校学习时长")
    @JsonProperty("studyDurationForWhole")
    private Integer studyDurationForWhole;

    @ApiModelProperty(value = "本校资源")
    @JsonProperty("resourcesForThisTenant")
    private Integer resourcesForThisTenant;

    @ApiModelProperty(value = "本校教材")
    @JsonProperty("textBookForThisTenant")
    private Integer textBookForThisTenant;
}
