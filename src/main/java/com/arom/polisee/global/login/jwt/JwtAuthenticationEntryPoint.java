package com.arom.polisee.global.login.jwt;

import com.arom.polisee.global.exception.BaseException;
import com.arom.polisee.global.exception.error.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        BaseException baseException = (BaseException) request.getAttribute("exception");

        ErrorCode errorCode = ErrorCode.UNAUTHORIZED_REQUEST;
        String message = errorCode.getMessage();

        // 💡 null 체크 추가
        if (baseException != null) {
            errorCode = baseException.getErrorCode();
            message = baseException.hasCustomMessage()
                    ? baseException.getCustomErrorMessage()
                    : errorCode.getMessage();
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(String.format(
                "{\"code\":\"%s\", \"message\":\"%s\"}",
                errorCode.getCode(), message
        ));
    }
}
