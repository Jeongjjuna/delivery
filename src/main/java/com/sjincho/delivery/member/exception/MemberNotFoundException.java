package com.sjincho.delivery.member.exception;

import com.sjincho.delivery.exception.DeliveryApplicationException;

public class MemberNotFoundException extends DeliveryApplicationException {

    public MemberNotFoundException(final MemberErrorCode errorCode, Long id) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("id:%d Not Found", id));
    }
}
