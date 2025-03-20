package com.arom.polisee.global.login.service;

import com.arom.polisee.global.login.config.KakaoConfig;
import com.arom.polisee.global.login.dto.LoginResponseDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final KakaoConfig kakaoConfig;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getKakaoAccessToken(String code) {
        String requestURL = kakaoConfig.getToken_uri();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoConfig.getClientId());
        params.add("redirect_uri", kakaoConfig.getRedirect_uri());
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, createHeaders());

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    requestURL, HttpMethod.POST, requestEntity, String.class
            );

            JsonNode root = objectMapper.readTree(response.getBody());
            return root.path("access_token").asText();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("카카오 토큰 요청 실패", e);
        }
    }

    public LoginResponseDto getUserInfoFromToken(@RequestHeader("Authorization") String accessToken) {
        String url = kakaoConfig.getUser_info_uri();
        LoginResponseDto loginResponseDto = null;

        // HTTP 요청 헤더 설정
        HttpHeaders headers = createHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            // API 요청 실행
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            // JSON 응답 파싱
            JsonNode root = objectMapper.readTree(response.getBody());
            String username = root.path("properties").path("nickname").asText();
            Long kakaoId = root.path("id").asLong();

            // 사용자 정보 저장
            loginResponseDto = new LoginResponseDto(kakaoId,username);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return loginResponseDto;
    }

    public String getKakaoAuthUrl() {
        return kakaoConfig.getAuthorization_uri()
                + "?client_id=" + kakaoConfig.getClientId()
                + "&redirect_uri=" + kakaoConfig.getRedirect_uri()
                + "&response_type=code"
                + "&scope=" + kakaoConfig.getScope();

    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }
}
