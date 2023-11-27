package com.sjincho.hun.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthErrorCode {
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "[ERROR] Password Invalid"),
    AUTHORIZATION_HEADER_NULL(HttpStatus.UNAUTHORIZED, "[ERROR] Header Authorization Is Null"),
    INVALID_BEARER(HttpStatus.UNAUTHORIZED, "[ERROR] Not Bearer"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "[ERROR] Token Is Expired");

    private final HttpStatus status;
    private final String message;

    AuthErrorCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
