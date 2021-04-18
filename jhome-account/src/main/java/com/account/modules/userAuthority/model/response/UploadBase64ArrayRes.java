package com.account.modules.userAuthority.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class UploadBase64ArrayRes implements Serializable {
    private static final long serialVersionUID = -5211283171897673722L;

    @NotEmpty(message="fileIds must be not null")
    @ApiModelProperty(value = "文件ID数组", required = true)
    private String[] fileIds;

}
