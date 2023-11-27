package com.sjincho.hun.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthErrorCode {
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "[ERROR] Password Invalid"),
    MISSING_AUTHORIZATION_HEADER(HttpStatus.UNAUTHORIZED, "[ERROR] Authorization Header Is Null"),
    INVALID_AUTHENTICATION_TOKEN(HttpStatus.UNAUTHORIZED, "[ERROR] Not Bearer"),
    EXPIRED_AUTHENTICATION_TOKEN(HttpStatus.UNAUTHORIZED, "[ERROR] Token Is Expired");

    private final HttpStatus status;
    private final String message;

    AuthErrorCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
