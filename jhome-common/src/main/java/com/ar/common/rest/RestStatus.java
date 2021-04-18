package com.ar.common.rest;

public interface RestStatus {
    int code();

    String name();

    String message();

    String subMessage();
}
