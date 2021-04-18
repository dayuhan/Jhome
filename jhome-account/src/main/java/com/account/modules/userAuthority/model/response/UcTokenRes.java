package com.account.modules.userAuthority.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "UcTokenRes", description = "用户认证凭据信息")
public class UcTokenRes {

    @ApiModelProperty("用户认证凭据")
    private String token;

    @ApiModelProperty("过期时间，单位：毫秒")
    private long expTime;

    @ApiModelProperty("生成时间，单位：毫秒")
    private long genTime;

    @ApiModelProperty(value = "返回数据")
    private LoginRes data;

    public UcTokenRes(String token, long expTime, long genTime, LoginRes loginRes) {
        super();
        this.token = token;
        this.expTime = expTime;
        this.genTime = genTime;
        this.data = loginRes;
    }

    public UcTokenRes(String token, long expTime, long genTime) {
        super();
        this.token = token;
        this.expTime = expTime;
        this.genTime = genTime;
    }

}
