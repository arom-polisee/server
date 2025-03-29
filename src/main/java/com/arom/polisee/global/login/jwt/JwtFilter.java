package com.arom.polisee.global.login.jwt;

import com.arom.polisee.domain.user.Role;
import com.arom.polisee.global.exception.BaseException;
import com.arom.polisee.global.login.dto.UserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestUri = request.getRequestURI();


        if (requestUri.startsWith("/auth/") || requestUri.equals("/favicon.ico")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = resolveToken(request);

            if (token != null && jwtProvider.validateToken(token)) {

                UserDto jwtUserDto = jwtProvider.getUserDto(token);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        jwtUserDto, null, jwtUserDto.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (BaseException e) {
            request.setAttribute("exception", e);
            throw new JwtAuthenticationException(e.getMessage(),e) {};
        }
    }

    //헤더에서 토큰 추출
    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
