package com.sjincho.hun.order.dto.response;

import com.sjincho.hun.order.domain.OrderLine;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderLineResponse {
    private final Long foodId;
    private final Long price;
    private final String foodName;
    private final Long quantity;

    @Builder
    public OrderLineResponse(final Long foodId, final Long price, final String foodName, final Long quantity) {
        this.foodId = foodId;
        this.price = price;
        this.foodName = foodName;
        this.quantity = quantity;
    }

    public static OrderLineResponse from(OrderLine orderLine) {
        return OrderLineResponse.builder()
                .foodId(orderLine.getFood().getId())
                .price(orderLine.getFood().getPrice())
                .foodName(orderLine.getFood().getName())
                .quantity(orderLine.getQuantity())
                .build();
    }
}
