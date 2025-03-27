package com.arom.polisee.global.login.dto;

import com.arom.polisee.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String username;
    private Role role;
}
