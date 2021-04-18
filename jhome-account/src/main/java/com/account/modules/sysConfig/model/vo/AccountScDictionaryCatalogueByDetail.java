package com.account.modules.sysConfig.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

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
 * @description:
 * @author: Daxv
 * @create: 2020-08-18 20:20
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "字典查询返回结果对象")
public class AccountScDictionaryCatalogueByDetail implements Serializable {
    private static final long serialVersionUID = 4921214124588261605L;
    @ApiModelProperty(value = "字典id",name = "id")
    private String id;
    @ApiModelProperty(value = "字典目录编码",name = "dictonaryDetailName")
    private String dictonaryCatalogueCode;
    @ApiModelProperty(value = "字典名称",name = "dictonaryDetailName")
    private String dictonaryDetailName;
    @ApiModelProperty(value = "所属分类名称",name = "dictonaryCatalogueName")
    private String dictonaryCatalogueName;
    @ApiModelProperty(value = "备注",name = "remark")
    private String remark;
    @ApiModelProperty(value = "排序",name = "Sort")
    private Integer Sort;
    @ApiModelProperty(value = "状态",name = "Status")
    private Integer Status;
}
