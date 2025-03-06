package com.arom.polisee.global.login.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseDto {
    private Long userId;
    private String username;
}