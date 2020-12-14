package com.bracket.common.register;

public enum ProductCode {
    PRODUCT_CODE("SD-INDT-017-SC-US-00");
    private String code;

    ProductCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code.toString();
    }
}
