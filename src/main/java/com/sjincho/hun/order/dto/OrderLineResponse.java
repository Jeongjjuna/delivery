package com.sjincho.hun.order.dto;

import com.sjincho.hun.order.domain.OrderLine;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderLineResponse {
    private final Long foodId;
    private final Long price;
    private final Long quantity;
    private final String foodName;

    @Builder
    private OrderLineResponse(final Long foodId, final Long price,
                              final Long quantity, final String foodName) {
        this.foodId = foodId;
        this.price = price;
        this.quantity = quantity;
        this.foodName = foodName;
    }

    public static OrderLineResponse from(OrderLine orderLine) {
        return OrderLineResponse.builder()
                .foodId(orderLine.getFoodId())
                .price(orderLine.getFoodId())
                .quantity(orderLine.getQuantity())
                .foodName(orderLine.getFoodName())
                .build();
    }
}
