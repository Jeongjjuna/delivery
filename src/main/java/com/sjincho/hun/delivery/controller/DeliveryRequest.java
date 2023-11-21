package com.sjincho.hun.delivery.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class DeliveryRequest {

    @NotNull(message = "주문 id를 확인해주세요. null 일 수 없습니다.")
    @PositiveOrZero(message = "id 는 음수일 수 없습니다.")
    private final Long orderId;

    @NotNull(message = "주문자 id를 확인해주세요. null 일 수 없습니다.")
    @PositiveOrZero(message = "id 는 음수일 수 없습니다.")
    private final Long receiverId;

    public DeliveryRequest(final Long orderId, final Long receiverId) {
        this.orderId = orderId;
        this.receiverId = receiverId;
    }
}
