package com.sjincho.hun.delivery.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DeliveryRequest {

    @NotNull(message = "주문 id를 확인해주세요. null 일 수 없습니다.")
    @PositiveOrZero(message = "id 는 음수일 수 없습니다.")
    private Long orderId;

    @Builder
    public DeliveryRequest(final Long orderId) {
        this.orderId = orderId;
    }

    public DeliveryRequest() {
    }
}
