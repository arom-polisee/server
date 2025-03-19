package com.arom.polisee.global.login.dto;

import com.arom.polisee.global.login.entity.Role;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String username;
    private Role role;
}
