package com.arom.polisee.global.login.jwt;

import com.arom.polisee.domain.user.Role;
import com.arom.polisee.global.exception.BaseException;
import com.arom.polisee.global.exception.error.ErrorCode;
import com.arom.polisee.global.login.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
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
            Claims claims = parseClaims(token);
            log.info("토큰 검증 완료 - userId : {}, role : {}, 만료시간 : {}", claims.get("userId"),claims.get("role"), claims.getExpiration());
            return true;
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT: {}", e.getMessage());
            throw BaseException.from(ErrorCode.JWT_TOKEN_EXPIRED);
        } catch (JwtException e) {
            log.error("JWT 파싱 실패: {}", e.getMessage());
            throw BaseException.from(ErrorCode.INVALID_JWT_TOKEN);
        }
    }

    // JWT에서 사용자 ID 추출
    public Long getUserIdFromToken(String token) {
        Claims claims = parseClaims(token);
        return claims.get("userId", Long.class);
    }

    //JWT에서 사용자 이름 추출
    public String getUsernameFromToken(String token) {
        Claims claims = parseClaims(token);
        return claims.get("username", String.class);
    }

    //JWT에서 사용자 권한 추출
    public Role getRoleFromToken(String token) {
        Claims claims = parseClaims(token);
        String role = claims.get("role", String.class);
        return Role.valueOf(role);
    }


    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
