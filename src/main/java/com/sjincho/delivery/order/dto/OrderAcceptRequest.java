package com.sjincho.delivery.order.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class OrderAcceptRequest {
    private final Long memberId;
    private final String cellPhone;
    private final String postalCode;
    private final String detailAddress;
    private final List<OrderLineDto> orderLineDtos;

    public OrderAcceptRequest(final Long memberId, final String cellPhone,
                              final String postalCode, final String detailAddress,
                              final List<OrderLineDto> orderLineDtos) {
        this.memberId = memberId;
        this.cellPhone = cellPhone;
        this.postalCode = postalCode;
        this.detailAddress = detailAddress;
        this.orderLineDtos = orderLineDtos;
    }

}
