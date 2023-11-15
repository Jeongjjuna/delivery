package com.sjincho.delivery.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DeliveryApplicationException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public DeliveryApplicationException(final ErrorCode errorCode, final String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return errorCode.getStatus();
    }
}
