package com.arom.polisee.global.login.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class BaseResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public BaseResponse(T data) {
        this.success = true;
        this.message = "요청 성공";
        this.data = data;
    }

    public BaseResponse(BaseResponseStatus status) {
        this.success = false;
        this.message = status.getMessage();
        this.data = null;
    }
}
