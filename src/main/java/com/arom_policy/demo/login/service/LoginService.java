package com.arom_policy.demo.login.service;

import com.arom_policy.demo.login.common.BaseException;
import com.arom_policy.demo.login.common.BaseResponseStatus;
import com.arom_policy.demo.login.dto.LoginResponseDto;
import com.arom_policy.demo.login.entity.User;
import com.arom_policy.demo.login.repository.UserRepository;
import com.arom_policy.demo.login.token.JwtProvider;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    public LoginResponseDto getUserInfo(Long kakaoId) {
        User user = userRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND));

        // JWT 발급
        String accessToken = jwtProvider.createAccessToken(kakaoId);

        return new LoginResponseDto(user.getId(), accessToken, user.getUserName());
    }

    public LoginResponseDto register(HashMap<String, Object> userInfo) {
        Long kakaoId = Long.valueOf(userInfo.get("id").toString());
        String username = userInfo.get("username").toString();

        User newUser = new User();
        newUser.setKakaoId(kakaoId);
        newUser.setUserName(username);

        userRepository.save(newUser);

        // JWT 발급
        String accessToken = jwtProvider.createAccessToken(kakaoId);

        // 회원가입 성공 응답 반환
        return new LoginResponseDto(newUser.getId(), accessToken, newUser.getUserName());
    }


    public int checkKakaoId(Long kakaoId) {
        return userRepository.existsByKakaoId(kakaoId) ? 1 : 0;
    }

    public HashMap<String, Object> getUserInfo(String accessToken) {
        HashMap<String, Object> userInfo = new HashMap<>();
        String url = "https://kapi.kakao.com/v2/user/me";

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            // API 요청 실행
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            // JSON 응답 파싱
            JsonNode root = objectMapper.readTree(response.getBody());
            String username = root.path("properties").path("nickname").asText();
            Long kakaoId = root.path("id").asLong();

            // 사용자 정보 저장
            userInfo.put("username", username);
            userInfo.put("id", kakaoId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userInfo;
    }

}