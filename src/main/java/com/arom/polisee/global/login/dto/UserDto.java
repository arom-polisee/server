package com.arom.polisee.global.login.dto;

import com.arom.polisee.global.login.entity.Role;
import com.arom.polisee.global.login.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private Role role;

    // private 생성자로 fromEntity 메서드 사용 강제
    private UserDto(Long id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
    public static UserDto fromEntity(UserEntity userEntity) {
        return new UserDto(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getRole()
        );
    }
}
