package com.sjincho.hun.delivery.exception;

import com.sjincho.hun.exception.DeliveryApplicationException;

public class DeliveryAlreadyRegisterException extends DeliveryApplicationException {

    public DeliveryAlreadyRegisterException(final DeliveryErrorCode errorCode, Long deliveryId) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("id:%s is already resister", deliveryId));
    }
}
