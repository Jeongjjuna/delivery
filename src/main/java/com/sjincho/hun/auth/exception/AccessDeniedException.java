package com.sjincho.hun.auth.exception;

import com.sjincho.hun.exception.DeliveryApplicationException;

public class AccessDeniedException extends DeliveryApplicationException {

    public AccessDeniedException(final AuthErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("don't have permission"));
    }
}
