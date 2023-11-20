package com.sjincho.delivery.member.exception;

import com.sjincho.delivery.exception.DeliveryApplicationException;

public class MemberEmailDuplicatedException extends DeliveryApplicationException {

    public MemberEmailDuplicatedException(final MemberErrorCode errorCode, String email) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("id:%s Not Found", email));
    }
}
