package com.sjincho.hun.member.exception;

import com.sjincho.hun.exception.DeliveryApplicationException;

public class MemberNotFoundException extends DeliveryApplicationException {

    public MemberNotFoundException(final MemberErrorCode errorCode, Long id) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("id:%d not found", id));
    }

    public MemberNotFoundException(final MemberErrorCode errorCode, String email) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("email:%s not found", email));
    }
}
