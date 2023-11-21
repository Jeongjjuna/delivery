package com.sjincho.hun.order.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum OrderErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "[ERROR] Order Not Found" ),
    NOT_ACCEPTING(HttpStatus.CONFLICT, "[ERROR] Order Not Accepting");

    private final HttpStatus status;
    private final String message;

    OrderErrorCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
