package com.arom.polisee.global.login.contoller;

import com.arom.polisee.global.exception.BaseException;
import com.arom.polisee.global.exception.error.ErrorCode;
import com.arom.polisee.global.login.service.LoginService;
import com.arom.polisee.global.login.service.KakaoOAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final KakaoOAuthService kakaoOAuthService;

    @GetMapping("/login")
    public ResponseEntity<Void> getKakaoAuthUrl() {
        String kakaoAuthUrl = kakaoOAuthService.getKakaoAuthUrl();
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", kakaoAuthUrl)
                .build();
    }

    @GetMapping(value = "/login/code/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code) {
        if(code == null) {
            throw BaseException.from(ErrorCode.KAKA0_CODE_REQUEST_FAILED);
        }
        return loginService.loginWithKakao(code); // 서비스에서 JWT 발급 & 쿠키 설정
    }

}