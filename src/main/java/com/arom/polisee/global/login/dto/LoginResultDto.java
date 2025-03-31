package com.arom.polisee.global.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResultDto {
    private String token;
    private String userName;
    private String role;
}
