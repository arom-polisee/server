package com.arom.polisee.global.login.service;

import com.arom.polisee.global.login.dto.CustomUserDetails;
import com.arom.polisee.global.login.dto.UserDto;
import com.arom.polisee.global.login.entity.UserEntity;
import com.arom.polisee.global.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UsernameNotFoundException("JWT 기반 인증에서는 사용 X");
    }

    public UserDto loadUserById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("유저 정보 없음 : " + userId));
        return UserDto.fromEntity(userEntity);
    }
}
