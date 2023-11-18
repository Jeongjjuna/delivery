package com.sjincho.delivery.order.dto;

import lombok.Getter;

@Getter
public class OrderLineDto {
    private final Long foodId;
    private final Long price;
    private final Long quantity;
    private final String foodName;

    public OrderLineDto(final Long foodId, final Long price,
                        final Long quantity, final String foodName) {
        this.foodId = foodId;
        this.price = price;
        this.quantity = quantity;
        this.foodName = foodName;
    }

    public static OrderLineDto from(final Long foodId, final Long price,
                                    final Long quantity, final String foodName) {
        return new OrderLineDto(foodId, price, quantity, foodName);
    }
}
