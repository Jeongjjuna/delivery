package com.sjincho.delivery.order.exception;

import com.sjincho.delivery.exception.DeliveryApplicationException;

public class OrderNotAcceptingException extends DeliveryApplicationException {

    public OrderNotAcceptingException(final OrderErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("not accepting"));
    }
}
