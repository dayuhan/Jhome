package com.account.common.sbUtil.model.vo;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 操作记录
 */
public enum OperateLogTypeEnum {
        INSERT(0), UPDATE(1), DELETE(2);

        private Integer value;

        @JsonValue
        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        OperateLogTypeEnum(Integer value) {
            this.value = value;
        }

    }
