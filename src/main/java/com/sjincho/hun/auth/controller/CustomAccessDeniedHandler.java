package com.sjincho.hun.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjincho.hun.exception.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response, final AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("접근권한 에러가 발생 : CustomAccessDeniedEntryPoint.class의 handle() 실행");
        ErrorResponse body = new ErrorResponse(LocalDateTime.now(), HttpStatus.FORBIDDEN.getReasonPhrase(), accessDeniedException.getMessage());
        response.setContentType("application/json");
        response.setStatus(403);
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
