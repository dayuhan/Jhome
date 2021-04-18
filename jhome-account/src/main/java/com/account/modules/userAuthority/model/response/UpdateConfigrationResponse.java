package com.account.modules.userAuthority.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="更新配置返回参数")
public class UpdateConfigrationResponse implements Serializable {
    private static final long serialVersionUID = 1842410696642871446L;
    @ApiModelProperty(value="配置名称",name="configrationName",example="三角函数PPT",required=true)
    private String configrationName;
    @ApiModelProperty(value="源文件名称",name="fileName",example="三角函数PPT",required=true)
    private String fileName;
    @ApiModelProperty(value="配置大小",name="fileSize",example="759.60",required=true)
    private Double fileSize;
    @ApiModelProperty(value="文件路径",name="filePath",example="/test/2019-05-31/5fdd2aa56dc345c48235e6fbc7813b70-Koala.jpg",required=true)
    private String filePath;
    @ApiModelProperty(value="更新说明",name="remark",example="修复了上传bug",required=true)
    private String remark;
    @ApiModelProperty(value="更新策略(0不更新，1建议更新，2强制更新)",name="updatePolicy",example="修复了上传bug",required=true)
    private Integer updatePolicy;
}
