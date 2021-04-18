package com.account.modules.userAuthority.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="异常记录请求参数")
public class SysExceptionRequest implements Serializable {
    private static final long serialVersionUID = 1864323609929668201L;
    /**
     * 模块
     */
    @ApiModelProperty(value="模块",name="module")
    private String module;

    /**
     * 行为
     */
    @ApiModelProperty(value="行为",name="action")
    private String action;

    /**
     * 异常信息
     */
    @ApiModelProperty(value="异常信息",name="exceptionInfomation")
    private String exceptionInfo;

    /**
     * 设备信息
     */
    @ApiModelProperty(value="设备信息",name="deviceInfo")
    private String deviceInfo;

    /**
     * 操作系统版本
     */
    @ApiModelProperty(value="操作系统版本",name="osVersion")
    private String osVersion;

    /**
     * CPU
     */
    @ApiModelProperty(value="CPU",name="cpu")
    private String cpu;

    /**
     * 内存
     */
    @ApiModelProperty(value="内存",name="memory")
    private String memory;

    /**
     * 浏览器内核版本
     */
    @ApiModelProperty(value="浏览器内核版本",name="browserKernelVersion")
    private String browserKernelVersion;

    /**
     * 创建人ID
     */
    @ApiModelProperty(value="创建人ID",name="createUserId")
    private Long createUserId;

    /**
     * 租户ID
     */
    @ApiModelProperty(value="租户ID",name="tenantId")
    private Long tenantId;


}
