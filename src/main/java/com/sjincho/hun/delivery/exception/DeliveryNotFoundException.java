package com.sjincho.hun.delivery.exception;

import com.sjincho.hun.exception.DeliveryApplicationException;

public class DeliveryNotFoundException extends DeliveryApplicationException {

    public DeliveryNotFoundException(final DeliveryErrorCode errorCode, final Long id) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("id:%d not found", id));
    }
}
