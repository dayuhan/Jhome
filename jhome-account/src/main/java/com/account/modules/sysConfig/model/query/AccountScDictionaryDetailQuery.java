package com.account.modules.sysConfig.model.query;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.bracket.common.Bus.AbstractModel.PageInfoRequest;
import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


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
 * @description: 字典明细表对象
 * @author: 2
 * @create: 2020-08-29
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)

@ApiModel(value = "AccountScDictionaryDetailQuery 查询对象", description = "字典明细表")
public class AccountScDictionaryDetailQuery extends PageInfoRequest {


    @ApiModelProperty(value = "字典明细主键Id")
    private String id;

    private String dictonaryCatalogueId;

    @ApiModelProperty(value = "字典编码", example = "10000")
    private String dictonaryCatalogueCode;

    @ApiModelProperty(value = "字典明细名", example = "字典明细名")
    private String dictonaryDetailName;

    @ApiModelProperty(value = "排序序号", example = "0")
    private Integer sort;

    @ApiModelProperty(value = "状态", example = "0,1")
    private Integer status;
    @ApiModelProperty(value = "备注", example = "备注信息")
    private String remark;


}