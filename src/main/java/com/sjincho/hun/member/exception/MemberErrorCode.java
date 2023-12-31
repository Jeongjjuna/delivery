package com.sjincho.hun.member.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "[ERROR] Member Not Found"),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "[ERROR] Member Email Duplicated"),
    UNAUTHORIZED_UPDATE(HttpStatus.FORBIDDEN, "[ERROR] Member Update Unauthorized");

    private final HttpStatus status;
    private final String message;

    MemberErrorCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
