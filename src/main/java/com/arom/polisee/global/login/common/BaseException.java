package com.arom.polisee.global.login.common;

public class BaseException extends RuntimeException {
    private final BaseResponseStatus status;

    public BaseException(BaseResponseStatus status) {
        super(status.getMessage()); // 에러 메시지를 가져옴
        this.status = status;
    }

    public BaseResponseStatus getStatus() {
        return status;
    }
}