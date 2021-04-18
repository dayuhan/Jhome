package com.ar.common.exception;


import com.ar.common.rest.RestStatus;

public class RestStatusException extends RuntimeException {

    private static final long serialVersionUID = -1534037473275885852L;

    public RestStatusException(String message) {
        super(message);
    }

    public RestStatusException(RestStatus message) {
        super(message.name());
    }

    public RestStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestStatusException(Throwable cause) {
        super(cause);
    }

    protected RestStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
