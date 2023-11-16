package com.sjincho.delivery.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    FOOD_NOT_FOUND(HttpStatus.NOT_FOUND, "[ERROR] Food Not Found"),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "[ERROR] Member Not Found");

    private final HttpStatus status;
    private final String message;

    ErrorCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}