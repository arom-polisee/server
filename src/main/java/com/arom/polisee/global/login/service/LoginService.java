package com.arom.polisee.global.login.service;

import com.arom.polisee.global.login.dto.UserDto;
import com.arom.polisee.global.login.dto.LoginResponseDto;
import com.arom.polisee.global.login.entity.Role;
import com.arom.polisee.global.login.entity.UserEntity;
import com.arom.polisee.global.login.repository.UserRepository;
import com.arom.polisee.global.login.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final TokenService tokenService;

    public UserEntity register(LoginResponseDto userInfo) {
        Long kakaoId = userInfo.getUserId();
        String username = userInfo.getUsername();

        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setKakaoId(kakaoId);
        newUserEntity.setUsername(username);
        newUserEntity.setRole(Role.ROLE_USER);
        return userRepository.save(newUserEntity);
    }

    public ResponseEntity<Map<String, String>> loginOrRegister(LoginResponseDto userInfo) {
        Long kakaoId = userInfo.getUserId();

        // 기존 유저 조회 or 회원가입
        UserEntity userEntity = userRepository.findByKakaoId(kakaoId)
               .orElseGet(() -> register(userInfo));

        UserDto userDto = UserDto.fromEntity(userEntity);

        // JWT 생성
        String jwt = jwtProvider.createAccessToken(userDto);

        log.info("로그인한 유저 : {} | 발급된 JWT : {}", userEntity, jwt);

        // 응답 바디에 JWT 포함 (클라이언트에서 저장할 수 있도록)
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("token", jwt);
        responseBody.put("userName", userDto.getUsername());
        responseBody.put("role", userDto.getRole().name());

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt) // 헤더에 JWT 추가
                .body(responseBody);
    }

    public ResponseEntity<Map<String, String>> loginWithKakao(String code) {

        String accessToken = tokenService.getKakaoAccessToken(code);
        log.info("카카오 Access Token : {} ", accessToken);

        // 카카오 유저 정보 요청
        LoginResponseDto userInfo = tokenService.getUserInfoFromToken(accessToken);
        log.info("카카오 유저 정보 : {} ", userInfo);

        return loginOrRegister(userInfo);
    }
}
