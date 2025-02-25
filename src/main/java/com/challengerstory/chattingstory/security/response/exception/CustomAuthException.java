package com.challengerstory.chattingstory.security.response.exception;

import lombok.Getter;

@Getter
public class CustomAuthException extends RuntimeException {
    private final AuthErrorCode errorCode;

    public CustomAuthException(AuthErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }


}
