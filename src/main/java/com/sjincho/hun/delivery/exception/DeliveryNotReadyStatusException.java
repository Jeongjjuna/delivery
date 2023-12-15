package com.sjincho.hun.delivery.exception;

import com.sjincho.hun.exception.DeliveryApplicationException;

public class DeliveryNotReadyStatusException extends DeliveryApplicationException {

    public DeliveryNotReadyStatusException(final DeliveryErrorCode errorCode, Long deliveryId) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("id:%s is not ready status", deliveryId));
    }
}
