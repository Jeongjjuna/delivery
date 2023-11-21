package com.sjincho.hun.delivery.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum DeliveryErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "[ERROR] Delivery Not Found");

    private final HttpStatus status;
    private final String message;

    DeliveryErrorCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
