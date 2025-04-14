package com.arom.polisee.global.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KakaoResponseDto {
    private Long kakaoId;
    private String username;
}