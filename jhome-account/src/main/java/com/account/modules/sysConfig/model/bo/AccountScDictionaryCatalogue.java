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
 * @description: 字典分类表对象
 * @author:  1
 * @create: 2020-08-29
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)

@TableName("account_sc_dictionary_catalogue")
@ApiModel(value = "AccountScDictionaryCatalogue对象", description = "字典分类表")
public class AccountScDictionaryCatalogue extends Model<AccountScDictionaryCatalogue> {

    private static final long serialVersionUID=1L;
                private String id;

    @ApiModelProperty(value = "字典编码")
    @TableField("`dictonaryCatalogueCode`")
        private String dictonaryCatalogueCode;

    @ApiModelProperty(value = "字典名")
    @TableField("`dictonaryCatalogueName`")
        private String dictonaryCatalogueName;

    @ApiModelProperty(value = "是否系统类型")
    @TableField("`isDefault`")
        private Boolean isDefault;

    @ApiModelProperty(value = "状态")
    @TableField("`status`")
        private Integer status;

    @ApiModelProperty(value = "创建人")
    @TableField("`createdBy`")
        private String createdBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("`createdTime`")
        private Date createdTime;

    @ApiModelProperty(value = "修改人")
    @TableField("`updatedBy`")
        private String updatedBy;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("`updatedTime`")
        private Date updatedTime;

    @TableField("`isDeleted`")
        private Boolean isDeleted;

    @TableField("`remark`")
        private String remark;

    @TableField("`tenantId`")
        private String tenantId;


    @Override
    public String toString(){
        return"AccountScDictionaryCatalogue{"+
                "id="+ id +
                ", dictonaryCatalogueCode="+ dictonaryCatalogueCode +
                ", dictonaryCatalogueName="+ dictonaryCatalogueName +
                ", isDefault="+ isDefault +
                ", status="+ status +
                ", createdBy="+ createdBy +
                ", createdTime="+ createdTime +
                ", updatedBy="+ updatedBy +
                ", updatedTime="+ updatedTime +
                ", isDeleted="+ isDeleted +
                ", remark="+ remark +
                ", tenantId="+ tenantId +
        "}";
    }
 }