package com.arom.polisee.global.login.dto;

import com.arom.polisee.domain.user.Role;
import com.arom.polisee.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

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
    public static UserDto fromEntity(User user) {
        return new UserDto(
                user.getUserId(),
                user.getUserName(),
                user.getRole()
        );
    }

    public static UserDto fromJwt(Long id, String username, Role role) {
        return new UserDto(id, username, role);
    }

    public List<GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role.name()));
    }
}
