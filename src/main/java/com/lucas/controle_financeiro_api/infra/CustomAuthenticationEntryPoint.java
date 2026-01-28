package com.lucas.controle_financeiro_api.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.controle_financeiro_api.dto.login.ApiError;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpServletResponse.SC_UNAUTHORIZED,
                "Missing or invalid token"
        );

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(mapper.writeValueAsString(apiError));
    }
}