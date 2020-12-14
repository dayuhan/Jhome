package com.fileStore.modules.fileManagement.model.bo;

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

//    @NotEmpty(message="orgId must be not null")
//    @ApiModelProperty(value = "组织ID", required = true)
//    private Long orgId;
}
