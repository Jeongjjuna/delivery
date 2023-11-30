package com.sjincho.hun.auth.exception;

import com.sjincho.hun.exception.DeliveryApplicationException;

public class NotBearerAuthorizationException extends DeliveryApplicationException {

    public NotBearerAuthorizationException(final AuthErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("Authorization is not \"Bearer\""));
    }
}
