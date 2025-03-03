package com.arom.polisee.global.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //client error : 4xx

    //user
    USER_NOT_FOUND("USR-0000", "해당 회원이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    ;


    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;
}
