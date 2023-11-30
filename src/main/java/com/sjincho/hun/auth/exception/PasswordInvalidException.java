package com.sjincho.hun.auth.exception;

import com.sjincho.hun.exception.DeliveryApplicationException;

public class PasswordInvalidException extends DeliveryApplicationException {

    public PasswordInvalidException(final AuthErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("Password invalid"));
    }
}
