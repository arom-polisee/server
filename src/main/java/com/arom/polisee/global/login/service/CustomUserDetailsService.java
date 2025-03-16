package com.arom.polisee.global.login.service;

import com.arom.polisee.global.login.dto.CustomUserDetails;
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
        UserEntity userEntity = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("유저 정보 없음 : " + username));
        return new CustomUserDetails(userEntity);
    }

    public CustomUserDetails loadUserById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("유저 정보 없음 : " + userId));
        return new CustomUserDetails(userEntity);
    }
}
