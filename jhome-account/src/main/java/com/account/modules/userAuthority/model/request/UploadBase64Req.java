package com.account.modules.userAuthority.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class UploadBase64Req  implements Serializable {
    private static final long serialVersionUID = -5211283171897673722L;

    @NotEmpty(message="base64Str must be not null")
    @ApiModelProperty(value = "文件base64编码", required = true)
    private String base64Str;

    @NotEmpty(message="fileName must be not null")
    @ApiModelProperty(value = "带文件扩展名的文件名，如test.png", required = true)
    private String fileName;
}
