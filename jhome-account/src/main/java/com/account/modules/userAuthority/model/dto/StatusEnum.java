package com.account.modules.userAuthority.model.dto;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 *  删除(是否有效状态),发布(是否有效状态),其他(是否有效状态)
 *  EFFECTIVE:有效  INVALID:无效
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
public enum StatusEnum {

    EFFECTIVE("0"), INVALID("1");

    private String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    StatusEnum(String value) {
        this.value = value;
    }

}
