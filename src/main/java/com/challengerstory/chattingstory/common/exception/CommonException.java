package com.challengerstory.chattingstory.common.exception;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
    private final ErrorCode errorCode;

    public CommonException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // Call parent constructor with the message
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return this.errorCode.getMessage();
    }
}