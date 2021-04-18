package com.account.modules.userAuthority.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhi.wang
 * @date 2019/9/12 15:21
 */
@Data
@ApiModel(value="App扫一扫获取教材返回参数对象")
public class GetScanTextbookResponse implements Serializable {
    private static final long serialVersionUID = -7088946828577023891L;
    @ApiModelProperty(value="教材ID",name="name",example="textbookId",required=true)
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long textbookId;
    @ApiModelProperty(value="教材名称",name="name",example="发动机原理",required=true)
    private String name;
    @ApiModelProperty(value="教材简称",name="abbreviation")
    private String abbreviation;
}
