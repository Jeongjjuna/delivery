package com.sjincho.delivery.member.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "[ERROR] Member Not Found"),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "[ERROR] Member email is duplicated");

    private final HttpStatus status;
    private final String message;

    MemberErrorCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
