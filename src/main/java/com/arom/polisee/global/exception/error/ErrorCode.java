package com.arom.polisee.global.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //client error : 4xx

    //user
    USER_NOT_FOUND("USR-0000", "해당 회원이 존재하지 않습니다.", ErrorDisplayType.POPUP),

    //policies
    POLICY_NOT_FOUND("POL-0000", "정책을 찾을 수 없습니다.", ErrorDisplayType.POPUP),

    //server Error
    POLICY_API_ERROR("POL-1000", "정책 API 요청 중 오류 발생",ErrorDisplayType.POPUP),
    POLICY_DETAIL_API_ERROR("POD-1000", "상세 정책 API 요청 중 오류 발생",ErrorDisplayType.POPUP),
    INTERNAL_SERVER_ERROR("COM-1000", "내부 서버 오류가 발생했습니다.",ErrorDisplayType.POPUP),
    JSON_PARSING_ERROR("JSON-0000","Json 파싱 중 오류가 발생했습니다.",ErrorDisplayType.POPUP),

    ;

    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;
}
