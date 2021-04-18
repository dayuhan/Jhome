package com.account.common.sbUtil.model.response.error;

import com.ar.common.rest.RestStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.annotation.Nonnull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class ErrorEntity implements Serializable {

    private static final long serialVersionUID = -4905748180129783523L;

    @ApiModelProperty(value = "错误码", required = true)
    @JsonProperty("returnCode")
    private Integer returnCode;

    @ApiModelProperty(value = "消息")
    @JsonProperty("returnMsg")
    private String returnMsg;

//    @ApiModelProperty(value = "详情")
//    @JsonProperty("details")
//    private String details;

    public ErrorEntity(@Nonnull RestStatus statusCodes) {
        this.returnCode = statusCodes.code();
        this.returnMsg = statusCodes.subMessage();
    }

    public ErrorEntity(@Nonnull RestStatus statusCodes, String detail) {
        this.returnCode = statusCodes.code();
        this.returnMsg = statusCodes.subMessage() + "," + detail;
    }

    public void setDetail(String detail) {
        this.returnMsg = this.returnMsg + "," + detail;
    }

    public void setOnlyDetail(String detail) {
        this.returnMsg = detail;
    }

}
