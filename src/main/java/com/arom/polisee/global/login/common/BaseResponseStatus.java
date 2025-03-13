package com.arom.polisee.global.login.common;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    SUCCESS(200, "요청 성공"),
    SC_UNAUTHORIZED(401,"토큰 인증 실패"),
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "서버 오류 발생");

    private final int statusCode;
    private final String message;

    BaseResponseStatus(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
