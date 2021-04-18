package com.account.modules.sysConfig.model.query;

import com.bracket.common.Bus.AbstractModel.PageInfoRequest;
import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;
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
 * @description: 字典分类表对象
 * @author: 2
 * @create: 2020-08-29
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)

@ApiModel(value = "AccountScDictionaryCatalogueQuery 查询对象", description = "字典分类表")
public class AccountScDictionaryCatalogueQuery extends PageInfoRequest {


    private String id;

    @ApiModelProperty(value = "字典编码")
    private String dictonaryCatalogueCode;

    @ApiModelProperty(value = "字典名")
    private String dictonaryCatalogueName;

    @ApiModelProperty(value = "是否系统类型")
    private Boolean isDefault;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "字典明细名")
    private String dictonaryDetailName;

    @ApiModelProperty(value = "字典明细所属分类id")
    private String dictonaryCatalogueId;





}