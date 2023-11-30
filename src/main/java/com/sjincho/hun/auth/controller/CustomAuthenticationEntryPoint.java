package com.sjincho.hun.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjincho.hun.exception.DeliveryApplicationException;
import com.sjincho.hun.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("filter CustomAuthenticationEntryPoint.class의 commence()실행");

        Object ExceptionObject = request.getAttribute("JwtAuthenticationFilterException");

        if (existJetFilterException(ExceptionObject)) {
            DeliveryApplicationException jwtFilterException = (DeliveryApplicationException) ExceptionObject;
            setJwtResponse(response, jwtFilterException);
            return;
        }

        setResponse(response, authException);
    }

    private void setJwtResponse(HttpServletResponse response, DeliveryApplicationException e) throws IOException {
        ErrorResponse body = new ErrorResponse(LocalDateTime.now(), e.getHttpStatusMessage(), e.getDetailMessage());
        setReponseBase(response, body);
    }

    private void setResponse(HttpServletResponse response, AuthenticationException e) throws IOException {
        ErrorResponse body = new ErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), e.getMessage());
        setReponseBase(response, body);
    }

    private void setReponseBase(HttpServletResponse response, ErrorResponse body) throws IOException {
        response.setContentType("application/json");
        response.setStatus(403);
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }

    private boolean existJetFilterException(Object jwtFilterException) {
        return jwtFilterException != null;
    }
}
