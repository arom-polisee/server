package com.arom.polisee.global.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

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

    //kakao Error
    KAKAO_CONFIG_MISSING("KAKAO-0000", "카카오 로그인 설정 오류 발생.", ErrorDisplayType.HIDE),
    KAKA0_CODE_REQUEST_FAILED("KAKAO-0001", "카카오 인증 코드 요청 실패.", ErrorDisplayType.HIDE),
    KAKAO_TOKEN_REQUEST_FAILED("KAKAO-0002", "카카오 토큰 요청 실패", ErrorDisplayType.HIDE),
    KAKAO_USER_INFO_REQUEST_FAILED("KAKAO-0003", "카카오 사용자 정보 요청 실패", ErrorDisplayType.HIDE),

    //jwt
    JWT_TOKEN_EXPIRED("JWT-0000","JWT 토큰 유효기간 만료.",ErrorDisplayType.HIDE),
    INVALID_JWT_TOKEN("JWT-0001", "잘못된 JWT 형식입니다.", ErrorDisplayType.HIDE),

    //auth
    UNAUTHORIZED_REQUEST("auth-0000", "인증이 필요한 요청입니다", ErrorDisplayType.HIDE);


    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;
}
