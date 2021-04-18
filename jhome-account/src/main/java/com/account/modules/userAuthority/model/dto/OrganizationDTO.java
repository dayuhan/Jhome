package com.account.modules.userAuthority.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Data
@ApiModel(value = "编辑用户信息封装类")
public class OrganizationDTO {

    private Long[] ids;

    private Integer deleteFlag;

}
