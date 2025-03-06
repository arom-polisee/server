package com.arom.polisee.global.login.service;

import com.arom.polisee.global.login.entity.User;
import com.arom.polisee.global.login.repository.UserRepository;
import com.arom.polisee.global.login.token.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final TokenService tokenService;

    public User register(HashMap<String, Object> userInfo) {
        Long kakaoId = Long.valueOf(userInfo.get("id").toString());
        String username = userInfo.get("username").toString();

        User newUser = new User();
        newUser.setKakaoId(kakaoId);
        newUser.setUserName(username);
        return userRepository.save(newUser);
    }

    public ResponseEntity<Void> loginOrRegister(HashMap<String, Object> userInfo) {
        Long kakaoId = Long.valueOf(userInfo.get("id").toString());

        // 기존 유저 조회 or 회원가입
        User user = userRepository.findByKakaoId(kakaoId)
                .orElseGet(() -> register(userInfo));

        // JWT 생성
        String jwt = jwtProvider.createAccessToken(user.getKakaoId());

        // HTTP-Only 쿠키 설정
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwt)
                .httpOnly(true)
                .secure(false)    // HTTPS에서만 사용(로컬 개발이므로 false)
                .path("/")
                .maxAge(60 * 60) // 1시간 유지
                .build();
        log.info("{} : {}", user, jwtCookie);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString()) // 쿠키 설정
                .build();
    }

    public ResponseEntity<Void> loginWithKakao(String code) {

        String accessToken = tokenService.getKakaoAccessToken(code);
        log.info("카카오 Access Token = {} ", accessToken);

        // 카카오 유저 정보 요청
        HashMap<String, Object> userInfo = tokenService.getUserInfoFromToken(accessToken);
        log.info("카카오 유저 정보 = {} ",userInfo);

        return loginOrRegister(userInfo);
    }
}