package com.sjincho.hun.delivery.exception;

import com.sjincho.hun.exception.DeliveryApplicationException;

public class DeliveryNotDeliveringException extends DeliveryApplicationException {

    public DeliveryNotDeliveringException(final DeliveryErrorCode errorCode, Long deliveryId) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("id:%s is not delivering", deliveryId));
    }
}
