package com.arom.polisee.global.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoResponseDto {
    private Long kakaoId;
    private String username;
}