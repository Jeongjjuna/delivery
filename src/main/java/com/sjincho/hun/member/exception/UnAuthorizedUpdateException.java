package com.sjincho.hun.member.exception;

import com.sjincho.hun.exception.DeliveryApplicationException;

public class UnAuthorizedUpdateException extends DeliveryApplicationException {

    public UnAuthorizedUpdateException(final MemberErrorCode errorCode, final Long memberId, final Long requesterId) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("targetMemberId:%s but requesterId:%s unauthorized", memberId, requesterId));
    }
}
