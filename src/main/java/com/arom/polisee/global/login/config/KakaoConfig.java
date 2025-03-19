package com.arom.polisee.global.login.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
public class KakaoConfig {

    @Value("${spring.kakao.client-id}")
    private String clientId;

    @Value("${spring.kakao.redirect-uri}")
    private String redirect_uri;

    @Value("${spring.kakao.scope}")
    private String scope;

    @Value("${spring.kakao.authorization-uri}")
    private String authorization_uri;


    @Value("${spring.kakao.token-uri}")
    private String token_uri;

    @Value("${spring.kakao.user-info-uri}")
    private String user_info_uri;

}