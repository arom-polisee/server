package com.arom.polisee.global.login.jwt;

import com.arom.polisee.global.login.common.BaseResponse;
import com.arom.polisee.global.login.common.BaseResponseStatus;
import com.arom.polisee.global.login.dto.CustomUserDetails;
import com.arom.polisee.global.login.dto.UserDto;
import com.arom.polisee.global.login.service.CustomUserDetailsService;
import com.arom.polisee.global.login.service.RedisCacheService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final RedisCacheService redisCacheService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestUri = request.getRequestURI();


        if (requestUri.startsWith("/auth/") || requestUri.equals("/favicon.ico")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 헤더에서 JWT 추출
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // "Bearer " 제거
            log.info("token = {}", token);
        }

        // JWT가 유효한 경우
        if (token != null && jwtProvider.validateToken(token)) {
            Long userId = jwtProvider.getUserIdFromToken(token);

            UserDto JwtuserDto = redisCacheService.getUserDetails(userId);

            if (JwtuserDto == null) {
                JwtuserDto = customUserDetailsService.loadUserById(userId);
                redisCacheService.saveUserDetails(JwtuserDto);
            }

            CustomUserDetails customUserDetails = new CustomUserDetails(JwtuserDto);


            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    customUserDetails, null, customUserDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } else {
            log.error("jwt 유효하지 않음");
            response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401 상태 코드 설정
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String responseBody = new ObjectMapper().writeValueAsString(
                    new BaseResponse<>(BaseResponseStatus.SC_UNAUTHORIZED)
            );

            response.getWriter().write(responseBody);
            response.getWriter().flush();
        }
    }
}
