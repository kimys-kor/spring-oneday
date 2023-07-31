package com.oneday.api.common.exception;

public class CustomException extends RuntimeException {
    public final ErrorCode CODE;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.message);
        this.CODE = errorCode;
    }

    public CustomException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.message, cause);
        this.CODE = errorCode;

    }

}
