package com.account.common.sbUtil.model.vo;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 软删标记
 */
public enum DeleteFlagEnum {
        EFFECTIVE("0"), INVALID("1");

        private String value;

        @JsonValue
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        DeleteFlagEnum(String value) {
            this.value = value;
        }

    }
