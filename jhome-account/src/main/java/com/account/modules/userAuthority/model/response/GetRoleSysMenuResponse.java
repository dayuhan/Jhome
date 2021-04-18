package com.account.modules.userAuthority.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色菜单请求返回
 * @author
 */
@Data
@ApiModel(value="角色菜单返回对象")
public class GetRoleSysMenuResponse implements Serializable {

    private static final long serialVersionUID = -6265524646658716364L;
    @ApiModelProperty(value="子菜单id", name="id", required=true)
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long id;
}
