package com.arom_policy.demo.login.contoller;

import com.arom_policy.demo.login.common.BaseException;
import com.arom_policy.demo.login.common.BaseResponse;
import com.arom_policy.demo.login.config.KakaoConfig;
import com.arom_policy.demo.login.dto.LoginResponseDto;
import com.arom_policy.demo.login.service.LoginService;
import com.arom_policy.demo.login.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final KakaoConfig kakaoConfig;
    private final LoginService loginService;
    private final TokenService tokenService;

    @GetMapping("/login")
    public ResponseEntity<Void> getKakaoAuthUrl() {
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize"
                + "?client_id=" + kakaoConfig.getClientId()
                + "&redirect_uri=" + kakaoConfig.getRedirectUri()
                + "&response_type=code"
                + "&scope=profile_nickname";

        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", kakaoAuthUrl)
                .build();
    }

    @GetMapping(value = "/login/kakao")
    public BaseResponse<LoginResponseDto> kakaoLogin(@RequestParam(required = false) String code) throws IOException {
        try {
            // 카카오 액세스 토큰 요청
            String accessToken = tokenService.getKakaoAccessToken(code);
            log.info("카카오 Access Token = {} ", accessToken);

            // 카카오 유저 정보 요청
            HashMap<String, Object> userInfo = loginService.getUserInfo(accessToken);
            log.info("카카오 유저 정보 = {} ",userInfo);

            LoginResponseDto loginResponseDto;

            Long kakaoId = Long.valueOf(userInfo.get("id").toString());

            if (loginService.checkKakaoId(kakaoId) == 0) {
                loginResponseDto = loginService.register(userInfo);
                log.info("회원 가입 완료 = {}",loginResponseDto);

            } else {
                loginResponseDto = loginService.getUserInfo(kakaoId);
                log.info("로그인 성공 = {}",loginResponseDto);
            }
            return new BaseResponse<>(loginResponseDto);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}