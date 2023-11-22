package com.sjincho.hun.order.exception;

import com.sjincho.hun.exception.DeliveryApplicationException;

public class OrderNotFoundException extends DeliveryApplicationException {

    public OrderNotFoundException(final OrderErrorCode errorCode, final Long id) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("id:%d Not Found", id));
    }
}
