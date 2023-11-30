package com.sjincho.hun.auth.controller;

import com.sjincho.hun.auth.exception.AuthErrorCode;
import com.sjincho.hun.auth.exception.CustomAccessDeniedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import java.io.IOException;

@Component
@Slf4j
public class CustomAccessDeniedEntryPoint implements AccessDeniedHandler {
    private final HandlerExceptionResolver resolver;

    public CustomAccessDeniedEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response, final AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("접근권한 확인중 에러가 발생해서 CustomAccessDeniedEntryPoint.class의 handle() 실행");
        resolver.resolveException(request, response, null, new CustomAccessDeniedException(AuthErrorCode.ACCESS_DENIED));
    }
}
