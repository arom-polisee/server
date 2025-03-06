package com.arom_policy.demo.login.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
public class JwtProvider {
    private final Key KEY;
    private final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1시간

    public JwtProvider(@Value("${jwt.secret}") String secret) {
        this.KEY = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // JWT 생성
    public String createAccessToken(Long kakaoId) {
        return Jwts.builder()
                .setSubject(String.valueOf(kakaoId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // JWT에서 사용자 ID 추출
    public Long getUserIdFromToken(String token) {
        return Long.valueOf(Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }

}
