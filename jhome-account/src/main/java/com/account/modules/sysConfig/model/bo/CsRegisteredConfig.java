package com.account.modules.sysConfig.model.bo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


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
 * @description: 对象
 * @author:  1
 * @create: 2020-08-24
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)

@TableName("cs_registered_config")
@ApiModel(value = "CsRegisteredConfig对象", description = "")
public class CsRegisteredConfig extends Model<CsRegisteredConfig> {

    private static final long serialVersionUID=1L;
    private String id;

    @ApiModelProperty(value = "参数名")
    @TableField("`paramName`")
    private String paramName;

    @ApiModelProperty(value = "键")
    @TableField("`key`")
    private String key;

    @ApiModelProperty(value = "值")
    @TableField("`value`")
    private String value;

    @ApiModelProperty(value = "服务Id")
    @TableField("`serviceId`")
    private String serviceId;

    @ApiModelProperty(value = "应用名称")
    @TableField("`application`")
    private String application;

    @ApiModelProperty(value = "是否启用 0:启用 1:禁用")
    @TableField("`isEnable`")
    private Integer isEnable;

    @ApiModelProperty(value = "环境")
    @TableField("`profile`")
    private String profile;

    @ApiModelProperty(value = "分支")
    @TableField("`label`")
    private String label;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("`createTime`")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("`createBy`")
    private String createBy;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("`updateTime`")
    private Date updateTime;

    @ApiModelProperty(value = "修复人")
    @TableField("`updateBy`")
    private String updateBy;

    @ApiModelProperty(value = "是否是公共组件 0不是 1是")
    @TableField("`isPublicComponent`")
    private Integer isPublicComponent;


    @Override
    public String toString(){
        return"CsRegisteredConfig{"+
                "id="+ id +
                ", paramName="+ paramName +
                ", key="+ key +
                ", value="+ value +
                ", application="+ application +
                ", isEnable="+ isEnable +
                ", profile="+ profile +
                ", label="+ label +
                ", createTime="+ createTime +
                ", createBy="+ createBy +
                ", updateTime="+ updateTime +
                ", updateBy="+ updateBy +
                ", isPublicComponent="+ isPublicComponent +
                "}";
    }
}