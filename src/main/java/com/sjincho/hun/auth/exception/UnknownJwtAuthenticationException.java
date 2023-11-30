package com.sjincho.hun.auth.exception;

import com.sjincho.hun.exception.DeliveryApplicationException;

public class UnknownJwtAuthenticationException extends DeliveryApplicationException {

    public UnknownJwtAuthenticationException(final AuthErrorCode errorCode, final String message) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format(message));
    }
}
