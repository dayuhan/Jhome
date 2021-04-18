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
public class DownloadReq implements Serializable {

    private static final long serialVersionUID = -8454112932085543228L;
    @NotEmpty(message="fileId must be not null")
    @ApiModelProperty(value = "文件路径id", required = true)
    private String fileId;
}
