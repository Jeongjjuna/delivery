package com.sjincho.hun.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderLineRequest {
    @NotNull(message = "주문음식 id를 확인해주세요. null 일 수 없습니다.")
    @PositiveOrZero(message = "id 는 음수일 수 없습니다.")
    private final Long foodId;

    @NotBlank(message = "주문상품 수량을 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
    @Positive(message = "주문상품 수량은 1보다 같거나 커야합니다.")
    private final Long quantity;

    @Builder
    public OrderLineRequest(final Long foodId, final Long quantity) {
        this.foodId = foodId;
        this.quantity = quantity;
    }
}
