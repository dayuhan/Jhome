package com.account.modules.userAuthority.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class GetPersonInfoReq implements Serializable {

    private static final long serialVersionUID = 1607540466605939100L;
    @NotNull(message="userId must be not null")
    @ApiModelProperty(value = "用户id", required = true)
    private Integer userId;
}
