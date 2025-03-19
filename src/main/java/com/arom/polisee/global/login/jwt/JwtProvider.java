package com.arom.polisee.global.login.jwt;

import com.arom.polisee.global.login.dto.UserDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
@Slf4j
public class JwtProvider {
    private final Key KEY;
    private final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1시간

    public JwtProvider(@Value("${jwt.secret}") String secret) {
        this.KEY = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // JWT 생성
    public String createAccessToken(UserDto userDto) {
        Claims claims = Jwts.claims();
        claims.put("userId", userDto.getId());
        claims.put("username", userDto.getUsername());
        claims.put("role", userDto.getRole());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            log.info("토큰 검증 완료 - userId : {}, role : {}, 만료시간 : {}", claims.get("userId"),claims.get("role"), claims.getExpiration());
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT 검증 실패 : {}", e.getMessage());
            return false;
        }
    }

    // JWT에서 사용자 ID 추출
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(KEY) // 시크릿 키 설정
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Long.valueOf(claims.get("userId").toString());

    }

}
