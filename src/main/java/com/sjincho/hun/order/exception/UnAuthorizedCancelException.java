package com.sjincho.hun.order.exception;

import com.sjincho.hun.exception.DeliveryApplicationException;

public class UnAuthorizedCancelException extends DeliveryApplicationException {

    public UnAuthorizedCancelException(final OrderErrorCode errorCode, final Long ordererId, final Long requesterId) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("orderId:%s but requestId:%s unauthorized", ordererId, requesterId));
    }
}
