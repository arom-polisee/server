package com.arom.polisee.global.login.service;

import com.arom.polisee.domain.user.Role;
import com.arom.polisee.domain.user.User;
import com.arom.polisee.global.login.dto.LoginResultDto;
import com.arom.polisee.global.login.dto.UserDto;
import com.arom.polisee.global.login.dto.KakaoResponseDto;
import com.arom.polisee.global.login.repository.UserRepository;
import com.arom.polisee.global.login.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final KakaoOAuthService kakaoOAuthService;

    public ResponseEntity<LoginResultDto> loginWithKakao(String code) {

        // Access Token 발급
        String accessToken = kakaoOAuthService.getKakaoAccessToken(code);
        log.info("카카오 Access Token : {} ", accessToken);

        // 카카오 유저 정보 요청
        KakaoResponseDto userInfo = kakaoOAuthService.getUserInfoFromToken(accessToken);
        log.info("카카오 유저 정보 : {} ", userInfo);

        // 로그인 or 회원가입
        User user = loginOrRegister(userInfo);
        log.info("로그인 or 회원가입 완료 : {}",user);

        // JWT 생성
        String jwt = jwtProvider.createAccessToken(UserDto.fromEntity(user));
        log.info("jwt 생성 완료 : {}", jwt);

        // 응답 바디에 JWT 포함 (클라이언트에서 저장할 수 있도록)
        LoginResultDto responseDto = new LoginResultDto(jwt, user.getUserName(), user.getRole().name());

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt) // 헤더에 JWT 추가
                .body(responseDto);
    }

    private User loginOrRegister(KakaoResponseDto userInfo) {
        User user = userRepository.findByKakaoId(userInfo.getKakaoId());
        if (user == null) {
            return register(userInfo);
        }
        return user;
    }

    private User register(KakaoResponseDto userInfo) {
        User newUserEntity = new User();
        newUserEntity.setKakaoId(userInfo.getKakaoId());
        newUserEntity.setUserName(userInfo.getUsername());
        newUserEntity.setRole(Role.ROLE_USER);
        return userRepository.save(newUserEntity);
    }
}
