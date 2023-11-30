package com.sjincho.hun.auth.exception;

import com.sjincho.hun.exception.DeliveryApplicationException;

public class CustomAccessDeniedException extends DeliveryApplicationException {

    public CustomAccessDeniedException(final AuthErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("don't have permission"));
    }
}
