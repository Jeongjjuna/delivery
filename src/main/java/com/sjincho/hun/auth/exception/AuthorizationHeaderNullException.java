package com.sjincho.hun.auth.exception;

import com.sjincho.hun.exception.DeliveryApplicationException;

public class AuthorizationHeaderNullException extends DeliveryApplicationException {

    public AuthorizationHeaderNullException(final AuthErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("Authorization header is null"));
    }
}
