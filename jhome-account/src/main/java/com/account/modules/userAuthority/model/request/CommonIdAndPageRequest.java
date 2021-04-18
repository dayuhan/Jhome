package com.account.modules.userAuthority.model.request;

import com.bracket.common.Bus.AbstractModel.PageInfoRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 适用所有id分页请求接口
 */
@Data
@ApiModel(value="id分页请求参数")
public class CommonIdAndPageRequest extends PageInfoRequest implements Serializable {
    private static final long serialVersionUID = -4305347437106793111L;

    @NotNull(message="id must be not null")
    @ApiModelProperty(value="id", name="id", required=true)
    private Long id;
}
