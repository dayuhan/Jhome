package com.account.modules.userAuthority.model.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserStatusEnum {
    NORMAL(1, "正常"), SOFT_DELETE(2, "逻辑删除"), DISABLE(3, "禁用");

    private Integer value;

    private String name;

    @JsonValue
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    UserStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
