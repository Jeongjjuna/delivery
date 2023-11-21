package com.sjincho.hun.order.exception;

import com.sjincho.hun.exception.DeliveryApplicationException;

public class OrderNotAcceptingException extends DeliveryApplicationException {

    public OrderNotAcceptingException(final OrderErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("not accepting"));
    }
}
