package com.arom.polisee.global.login.dto;

import com.arom.polisee.global.login.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponseDto {
    private String username;

    public UserResponseDto(UserEntity userEntity) {
        this.username = userEntity.getUserName();
    }
}
