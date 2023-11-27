package com.sjincho.hun.auth.exception;

import com.sjincho.hun.exception.DeliveryApplicationException;

public class TokenExpiredException extends DeliveryApplicationException {

    public TokenExpiredException(final AuthErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("Token is expired"));
    }
}
