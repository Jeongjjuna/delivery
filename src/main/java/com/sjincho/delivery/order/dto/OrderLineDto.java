package com.sjincho.delivery.order.dto;

import lombok.Getter;

@Getter
public class OrderLineDto {
    private final String foodName;
    private final Long price;
    private final Long quantity;

    private OrderLineDto(final String foodName, final Long price, final Long quantity) {
        this.foodName = foodName;
        this.price = price;
        this.quantity = quantity;
    }

    public static OrderLineDto from(final String foodName, final Long price, final Long quantity) {
        return new OrderLineDto(foodName, price, quantity);
    }
}
