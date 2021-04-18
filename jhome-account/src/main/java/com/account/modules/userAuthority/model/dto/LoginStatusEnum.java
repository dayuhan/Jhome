package com.account.modules.userAuthority.model.dto;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 *状态(0:登录，1:退出)
 *
 */
public enum LoginStatusEnum {

    LOGININ((short)0), LOGINOUT((short)1);

    private Short status;

    @JsonValue
    public Short getValue() {
        return status;
    }

    public void setValue(Short status) {
        this.status = status;
    }

    LoginStatusEnum(Short value) {
        this.status = status;
    }

}
