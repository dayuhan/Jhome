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
 * @description: 字典明细表对象
 * @author:  1
 * @create: 2020-08-29
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)

@TableName("account_sc_dictionary_detail")
@ApiModel(value = "AccountScDictionaryDetail对象", description = "字典明细表")
public class AccountScDictionaryDetail extends Model<AccountScDictionaryDetail> {

    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "字典明细主键Id")
                private String id;

    @TableField("`dictonaryCatalogueId`")
        private String dictonaryCatalogueId;

    @ApiModelProperty(value = "字典编码")
    @TableField("`dictonaryCatalogueCode`")
        private String dictonaryCatalogueCode;

    @ApiModelProperty(value = "字典明细名")
    @TableField("`dictonaryDetailName`")
        private String dictonaryDetailName;

    @TableField("`sort`")
        private Integer sort;

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


 }