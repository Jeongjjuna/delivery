package com.sjincho.delivery.food.dto;

import lombok.Getter;

@Getter
public class FoodUpdateRequest {
    private final String name;
    private final String foodType;
    private final Long price;

    public FoodUpdateRequest(final String name, final String foodType, final Long price) {
        this.name = name;
        this.foodType = foodType;
        this.price = price;
    }
}
