package com.account.common;

import com.ar.common.rest.BasicRestStatusEnum;
import com.ar.common.rest.RestStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@ApiModel
@Data
//@NoArgsConstructor
public class CommonRlt<T> implements Serializable {

    private static final long serialVersionUID = -7120512148337601640L;
    @NotNull(message="returnCode must be not null")
    @ApiModelProperty(value = "返回码", required = true)
    private Integer returnCode;

    @NotNull(message="returnMsg must be not null")
    @ApiModelProperty(value = "返回消息", required = true)
    private String returnMsg;

//    @ApiModelProperty(value = "详情")
//    private String details;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public CommonRlt(T data) {
        this.data = data;
    }

    public CommonRlt(Integer returnCode, String returnMsg) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public CommonRlt(Integer returnCode, String returnMsg, T data) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
        this.data = data;
    }

    public CommonRlt(Integer restStatus, T data) {
        this.returnCode = restStatus;
        this.data = data;
    }

    public CommonRlt(RestStatus restStatus) {
        this.returnCode = restStatus.code();
        this.returnMsg = restStatus.subMessage();
    }

    public CommonRlt(RestStatus restStatus, T data) {
        this.returnCode = restStatus.code();
        this.returnMsg = restStatus.subMessage();
        this.data = data;
    }



    /**
     * 成功返回结果
     */
    public static CommonRlt success() {
        return new CommonRlt(BasicRestStatusEnum.OK);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonRlt<T> success(T data) {
        return new CommonRlt<>(BasicRestStatusEnum.OK, data);
    }
}
