package com.sjincho.hun.delivery.exception;

import com.sjincho.hun.exception.DeliveryApplicationException;

public class DeliveryNotReadyStatusException extends DeliveryApplicationException {

    public DeliveryNotReadyStatusException(final DeliveryErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("not ready status"));
    }
}
