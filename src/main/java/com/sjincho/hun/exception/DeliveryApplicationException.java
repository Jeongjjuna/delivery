package com.sjincho.hun.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DeliveryApplicationException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String httpStatusMessage;
    private final String detailMessage;

    public DeliveryApplicationException(final HttpStatus httpStatus, final String httpStatusMessage, final String detailMessage) {
        this.httpStatus = httpStatus;
        this.httpStatusMessage = httpStatusMessage;
        this.detailMessage = detailMessage;
    }
}
